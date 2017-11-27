/***************************************************************************************************
 * Aadhaar API for Java
 * Copyright © 2017 Souvik Dey Chowdhury
 *
 * This file is part of Aadhaar API for Java.
 *
 * Aadhaar API for Java is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Aadhaar API for Java is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Aadhaar API
 * for Java. If not, see http://www.gnu.org/licenses.
 **************************************************************************************************/

package uidai.aadhaar.agency;

import uidai.aadhaar.generic.ApiException;
import uidai.aadhaar.generic.DataFormat;
import uidai.aadhaar.generic.KycInfo;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.XmlSignature;
import uidai.aadhaar.serialization.ResidentInfoXmlEncoder;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import static uidai.aadhaar.generic.AadhaarHelper.*;

public class KycContext extends ApiContext<KycRequest, KycResponse> {
    public KycContext() {
    }

    public KycContext(Encrypter encrypter, XmlSignature xmlSigner, XmlSignature xmlVerifier) {
        super(encrypter, xmlSigner, xmlVerifier);
    }

    public void encrypt(ResidentInfo info) throws ApiException {
        ExceptionHelper.validateNull(info, "info");
        ExceptionHelper.validateNull(getEncrypter(), "encrypter");

        if (getRequest() == null)
            setRequest(new KycRequest()
                .setAuthRequest(new AuthRequest()));

        info.setApiInfo(new KycInfo()
            .setResidentConsent(getRequest().getAuthRequest().hasResidentConsent())
            .setAccessLocalInfo(getRequest().shouldAccessLocalInfo())
            .setAccessEAadhaar(getRequest().shouldAccessEAadhaar())
            .setDelegateDecryptionToKsa(getRequest().shouldDelegateDecryptionToKsa())
            .setAuthType(getAuthType(info.getUses()))
            .infoDigest());

        final ResidentInfoXmlEncoder encoder = new ResidentInfoXmlEncoder(info);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(16000);
        encoder.validate();
        encoder.write(out);

        final byte[] timestamp = info.getCapturedOn().format(TIMESTAMP_FORMAT).getBytes(StandardCharsets.UTF_8);
        getRequest()
            .getAuthRequest()
            .setAadhaarNumber(info.getAadhaarNumber())
            .setEncryptedData(getEncrypter().encrypt(out.toByteArray(), timestamp)
                .setDataFormat(DataFormat.XML))
            .setSessionKey(getEncrypter().getSessionKey())
            .setUses(info.getUses());
    }

    @Override
    public URI getEndpoint() throws ApiException {
        /*
         * URI Format: <protocol://host/version>/<auaCode>/<aadhaarNumber[0]>/<aadhaarNumber[1]>/<asaLicenseKey>
         *
         * Required properties will be validated by validateRequest.
         * If validateRequest is not called before getEndpoint,
         * we wrap all NullPointerException of required properties into ApiException.
         */
        final URI endpoint;
        try {
            final StringJoiner joiner = new StringJoiner("/");
            final AuthRequest request = getRequest().getAuthRequest();
            final AgencyInfo info = request.getAgencyInfo();

            final String host = info.getHosts() != null ? info.getHosts().getOrDefault("Kyc", null) : null;
            if (isNullOrEmpty(host))
                throw new IllegalArgumentException(ErrorMessages.NOT_FOUND_HOST);

            joiner.add(host);
            joiner.add(info.getAuaCode());
            joiner.add(request.getAadhaarNumber().subSequence(0, 1));
            joiner.add(request.getAadhaarNumber().subSequence(1, 2));

            if (!isNullOrEmpty(info.getAsaLicenseKey()))
                joiner.add(info.getAsaLicenseKey());

            endpoint = URI.create(joiner.toString());
        } catch (final Exception e) {
            throw new ApiException(e);
        }
        return endpoint;
    }
}