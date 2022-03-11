package com.grasshopper.service;

public interface ArtifactoryService {
    void searchAsset(String group, String version);
    void searchAndDownloadAsset(String name, String version, String mavenExtension);
}
