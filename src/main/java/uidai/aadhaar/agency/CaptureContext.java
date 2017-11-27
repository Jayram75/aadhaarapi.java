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
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.resident.ResidentInfo;
import uidai.aadhaar.serialization.ResidentInfoXmlEncoder;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static uidai.aadhaar.generic.AadhaarHelper.TIMESTAMP_FORMAT;

public class CaptureContext extends ApiContext<CaptureRequest, CaptureResponse> {
    public CaptureContext() {
        setRequest(new CaptureRequest());
        setResponse(new CaptureResponse());
    }

    public void encrypt() throws ApiException {
        ExceptionHelper.validateNull(getEncrypter(), "encrypter");
        ExceptionHelper.validateNull(getRequest(), "request");
        ExceptionHelper.validateNull(getRequest().getResidentInfo(), "request.residentInfo");

        final ResidentInfo info = getRequest().getResidentInfo();
        final ResidentInfoXmlEncoder encoder = new ResidentInfoXmlEncoder(info);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(16000);
        encoder.validate();
        encoder.write(out);

        if (getResponse() == null)
            setResponse(new CaptureResponse());

        final byte[] timestamp = info.getCapturedOn().format(TIMESTAMP_FORMAT).getBytes(StandardCharsets.UTF_8);
        getResponse()
            .setEncryptedData(getEncrypter().encrypt(out.toByteArray(), timestamp)
                .setDataFormat(DataFormat.XML))
            .setSessionKey(getEncrypter().getSessionKey());
    }
}