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

package uidai.aadhaar.device;

import java.net.URL;
import java.util.Collection;

public class RDServiceVersionInfo {
    private String version;
    private String md5;
    private String os;
    private String osVersion;
    private RDServiceStatus status;
    private URL downloadPath;
    private URL docPath;
    private Collection<RDModelInfo> models;

    public String getVersion() {
        return version;
    }

    public RDServiceVersionInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public RDServiceVersionInfo setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public String getOS() {
        return os;
    }

    public RDServiceVersionInfo setOS(String os) {
        this.os = os;
        return this;
    }

    public String getOSVersion() {
        return osVersion;
    }

    public RDServiceVersionInfo setOSVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public RDServiceStatus getStatus() {
        return status;
    }

    public RDServiceVersionInfo setStatus(RDServiceStatus status) {
        this.status = status;
        return this;
    }

    public URL getDownloadPath() {
        return downloadPath;
    }

    public RDServiceVersionInfo setDownloadPath(URL downloadPath) {
        this.downloadPath = downloadPath;
        return this;
    }

    public URL getDocPath() {
        return docPath;
    }

    public RDServiceVersionInfo setDocPath(URL docPath) {
        this.docPath = docPath;
        return this;
    }

    public Collection<RDModelInfo> getModels() {
        return models;
    }

    public RDServiceVersionInfo setModels(Collection<RDModelInfo> models) {
        this.models = models;
        return this;
    }
}