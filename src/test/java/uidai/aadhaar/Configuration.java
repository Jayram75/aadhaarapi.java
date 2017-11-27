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

package uidai.aadhaar;

import uidai.aadhaar.agency.AgencyInfo;
import uidai.aadhaar.device.DeviceInfo;
import uidai.aadhaar.security.Encrypter;
import uidai.aadhaar.security.XmlSignature;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

public final class Configuration {
    private static final int INITIALIZED = 0;
    private static int state = ~INITIALIZED;

    public static AgencyInfo agencyInfo;
    public static DeviceInfo publicDeviceInfo;
    public static Encrypter encrypter;
    public static XmlSignature xmlSigner;
    public static XmlSignature xmlVerifier;

    private Configuration() {
    }

    public static void init() {
        if (state == INITIALIZED)
            return;

        agencyInfo = new AgencyInfo()
            .setAuaCode("public")
            .setAuaLicenseKey("MEaMX8fkRa6PqsqK6wGMrEXcXFl_oXHA-YuknI2uf0gKgZ80HaZgG3A")
            .setAsaLicenseKey("MG41KIrkk5moCkcO8w-2fc01-P7I5S-6X2-X7luVcDgZyOa2LXs3ELI")
            .setHosts(Map.of(
                "Auth", "http://developer.uidai.gov.in/auth",
                "Bfd", "http://developer.uidai.gov.in/bfd",
                "Kyc", "http://developer.uidai.gov.in/kyc",
                "Otp", "http://developer.uidai.gov.in/otp"
                )
            );

        publicDeviceInfo = new DeviceInfo()
            .setHostDeviceCode("STAGING");

        encrypter = Encrypter.getInstance();
        xmlSigner = XmlSignature.getInstance();
        xmlVerifier = XmlSignature.getInstance();

        try (final InputStream ukis = ClassLoader.getSystemResourceAsStream("UIDAI.cer");
             final InputStream akis = ClassLoader.getSystemResourceAsStream("Public AUA.p12")) {

            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            final Certificate uidai = cf.generateCertificate(ukis);

            final KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(akis, "public".toCharArray());
            final Entry aua = ks.getEntry("public", new PasswordProtection("public".toCharArray()));

            encrypter.init((X509Certificate) uidai);
            xmlSigner.initSign((PrivateKeyEntry) aua);
            xmlVerifier.initVerify(uidai.getPublicKey());

            state = INITIALIZED;
        } catch (final IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}