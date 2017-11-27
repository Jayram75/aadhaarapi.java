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

package uidai.aadhaar.security.implementation;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uidai.aadhaar.internal.ErrorMessages;
import uidai.aadhaar.internal.ExceptionHelper;
import uidai.aadhaar.security.XmlSignature;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

public final class DomXmlSignature extends XmlSignature {
    private static final XMLSignatureFactory XSF = XMLSignatureFactory.getInstance();
    private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
    private static final KeyInfoFactory KIF = KeyInfoFactory.getInstance();
    private static final TransformerFactory TF = TransformerFactory.newInstance();

    static {
        DBF.setNamespaceAware(true);
    }

    private PrivateKeyEntry entry;
    private PublicKey publicKey;

    @Override
    public void initSign(PrivateKeyEntry entry) {
        ExceptionHelper.validateNull(entry, "entry");

        this.entry = entry;
        state = SIGN;
    }

    @Override
    public void initVerify(PublicKey publicKey) {
        ExceptionHelper.validateNull(publicKey, "publicKey");

        this.publicKey = publicKey;
        state = VERIFY;
    }

    @Override
    public void sign(InputStream in, OutputStream out) throws IOException, SAXException {
        ExceptionHelper.validateNull(in, "in");
        ExceptionHelper.validateNull(out, "out");
        if (state != SIGN)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);

        final X509Certificate xc = (X509Certificate) entry.getCertificate();

        try {
            final XMLSignature signature = XSF
                .newXMLSignature(XSF
                    .newSignedInfo(XSF
                        .newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null), XSF
                        .newSignatureMethod(SignatureMethod.RSA_SHA1, null), List.of(XSF
                        .newReference("", XSF
                            .newDigestMethod(DigestMethod.SHA1, null), List.of(XSF
                            .newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null))), KIF
                    .newKeyInfo(List.of(KIF
                        .newX509Data(List.of(xc.getSubjectX500Principal().getName(), xc)))));

            final Document document = DBF.newDocumentBuilder().parse(in);
            final XMLSignContext signContext = new DOMSignContext(entry.getPrivateKey(), document.getDocumentElement());
            final Transformer transformer = TF.newTransformer();

            signature.sign(signContext);
            transformer.transform(new DOMSource(document), new StreamResult(out));
        } catch (final InvalidAlgorithmParameterException | ParserConfigurationException | NoSuchAlgorithmException | MarshalException | XMLSignatureException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verify(InputStream in) throws IOException, SAXException {
        ExceptionHelper.validateNull(in, "in");
        if (state != VERIFY)
            throw new IllegalStateException(ErrorMessages.INVALID_STATE);

        boolean isValid = false;
        try {
            final Document document = DBF.newDocumentBuilder().parse(in);
            final NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            if (nodeList.getLength() == 0)
                throw new IllegalArgumentException(ErrorMessages.REQUIRED_SIGNATURE);

            final XMLValidateContext validateContext = new DOMValidateContext(publicKey, nodeList.item(0));
            final XMLSignature signature = XSF.unmarshalXMLSignature(validateContext);
            isValid = signature.validate(validateContext);
        } catch (final ParserConfigurationException | MarshalException | XMLSignatureException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}