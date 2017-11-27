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
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.XmlSignature;
import uidai.aadhaar.serialization.AuthRequestXmlEncoder;
import uidai.aadhaar.serialization.AuthResponseXmlDecoder;
import uidai.aadhaar.serialization.ResidentInfoXmlEncoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import static uidai.aadhaar.generic.AadhaarHelper.TIMESTAMP_FORMAT;
import static uidai.aadhaar.generic.AadhaarHelper.isNullOrEmpty;

public class AuthContext extends ApiContext<AuthRequest, AuthResponse> {
    public AuthContext() {
    }

    public AuthContext(Encrypter encrypter, XmlSignature xmlSigner, XmlSignature xmlVerifier) {
        super(encrypter, xmlSigner, xmlVerifier);
    }

    public void encrypt(ResidentInfo info) throws ApiException {
        ExceptionHelper.validateNull(info, "info");
        ExceptionHelper.validateNull(getEncrypter(), "encrypter");

        final ResidentInfoXmlEncoder encoder = new ResidentInfoXmlEncoder(info);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(16000);
        encoder.validate();
        encoder.write(out);

        if (getRequest() == null)
            setRequest(new AuthRequest());

        final byte[] timestamp = info.getCapturedOn().format(TIMESTAMP_FORMAT).getBytes(StandardCharsets.UTF_8);
        getRequest()
            .setAadhaarNumber(info.getAadhaarNumber())
            .setEncryptedData(getEncrypter().encrypt(out.toByteArray(), timestamp)
                .setDataFormat(DataFormat.XML))
            .setSessionKey(getEncrypter().getSessionKey())
            .setUses(info.getUses());
    }

    @Override
    public void encodeRequest(OutputStream out) {
        ExceptionHelper.validateNull(out, "out");
        ExceptionHelper.validateNull(getRequest(), "request");

        final AuthRequestXmlEncoder encoder = new AuthRequestXmlEncoder(getRequest(), getXmlSigner());
        encoder.write(out);
    }

    @Override
    public void decodeResponse(InputStream in) throws ApiException {
        ExceptionHelper.validateNull(in, "in");

        final AuthResponseXmlDecoder decoder = new AuthResponseXmlDecoder(getXmlVerifier());
        setResponse(decoder.read(in));
    }

    @Override
    public URI getEndpoint() throws ApiException {
        /*
         * URI Format: <protocol://host/version>/<auaCode>/<aadhaarNumber[0]>/<aadhaarNumber[1]>/<asaLicenseKey>
         *
         * Required properties will be validated by validateRequest. If validateRequest is not
         * called before getEndpoint, we wrap any exception thrown into an ApiException.
         */
        final URI endpoint;
        try {
            final StringJoiner joiner = new StringJoiner("/");
            final AuthRequest request = getRequest();
            final AgencyInfo info = request.getAgencyInfo();

            final String host = info.getHosts() != null ? info.getHosts().getOrDefault("Auth", null) : null;
            if (isNullOrEmpty(host))
                throw new IllegalArgumentException(ErrorMessages.NOT_FOUND_HOST);

            joiner.add(host);
            joiner.add(info.getAuaCode());
            joiner.add(request.getAadhaarNumber().subSequence(0, 1));
            joiner.add(request.getAadhaarNumber().subSequence(1, 2));

            if (!isNullOrEmpty(info.getAsaLicenseKey()))
                joiner.add(info.getAsaLicenseKey());

            endpoint = URI.create(joiner.toString());
        } catch (final NullPointerException e) {
            throw new ApiException(e);
        }
        return endpoint;
    }
}