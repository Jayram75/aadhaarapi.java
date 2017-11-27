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
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.Decrypter;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.XmlSignature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.Map;

public class ApiContext<TRequest extends ApiRequest, TResponse extends ApiResponse> {
    private static final Map<String, String> REQUEST_PROPERTIES = Map.of(
        "Content-Type", "application/xml"
    );

    private TRequest request;
    private TResponse response;
    private Encrypter encrypter;
    private Decrypter decrypter;
    private XmlSignature xmlSigner;
    private XmlSignature xmlVerifier;

    protected ApiContext() {
    }

    protected ApiContext(Encrypter encrypter, XmlSignature xmlSigner, XmlSignature xmlVerifier) {
        this.encrypter = encrypter;
        this.xmlSigner = xmlSigner;
        this.xmlVerifier = xmlVerifier;
    }

    public TRequest getRequest() {
        return request;
    }

    public ApiContext<TRequest, TResponse> setRequest(TRequest request) {
        this.request = request;
        return this;
    }

    public TResponse getResponse() {
        return response;
    }

    public ApiContext<TRequest, TResponse> setResponse(TResponse response) {
        this.response = response;
        return this;
    }

    public Encrypter getEncrypter() {
        return encrypter;
    }

    public ApiContext<TRequest, TResponse> setEncrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
        return this;
    }

    public Decrypter getDecrypter() {
        return decrypter;
    }

    public ApiContext<TRequest, TResponse> setDecrypter(Decrypter decrypter) {
        this.decrypter = decrypter;
        return this;
    }

    public XmlSignature getXmlSigner() {
        return xmlSigner;
    }

    public ApiContext<TRequest, TResponse> setXmlSigner(XmlSignature xmlSigner) {
        this.xmlSigner = xmlSigner;
        return this;
    }

    public XmlSignature getXmlVerifier() {
        return xmlVerifier;
    }

    public ApiContext<TRequest, TResponse> setXmlVerifier(XmlSignature xmlVerifier) {
        this.xmlVerifier = xmlVerifier;
        return this;
    }

    /**
     * Encodes request to the specified output stream.
     *
     * @param out the output stream.
     */
    public void encodeRequest(OutputStream out) throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Decodes and initializes request from the specified input stream.
     *
     * @param in the input stream.
     */
    public void decodeRequest(InputStream in) throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Encodes response to the specified output stream.
     *
     * @param out the output stream.
     */
    public void encodeResponse(OutputStream out) throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Decodes and initializes response from the specified input stream.
     *
     * @param in the input stream.
     */
    public void decodeResponse(InputStream in) throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Validates request API.
     */
    public void validateRequest() throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Validates response API.
     */
    public void validateResponse() throws ApiException {
        ExceptionHelper.NotSupported();
    }

    /**
     * Gets the endpoint of the service.
     */
    public URI getEndpoint() throws ApiException {
        ExceptionHelper.NotSupported();
        return null;
    }

    /**
     * Sends request to the host.
     */
    public void send() throws ApiException, IOException {
        validateRequest();

        final URLConnection connection = getEndpoint().toURL().openConnection();
        REQUEST_PROPERTIES.forEach(connection::setRequestProperty);
        connection.setDoOutput(true);

        try (final OutputStream out = connection.getOutputStream()) {
            encodeRequest(out);
        }
        try (final InputStream in = connection.getInputStream()) {
            decodeResponse(in);
        }
    }
}