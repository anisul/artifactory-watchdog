package com.grasshopper.service;

import com.grasshopper.client.NexusArtifactoryClient;
import com.grasshopper.core.ApplicationProperties;
import com.grasshopper.core.search.SearchApiRequest;

public class DefaultArtifactoryService implements ArtifactoryService {

    private final NexusArtifactoryClient nexusArtifactoryClient;
    private final ApplicationProperties properties;

    public DefaultArtifactoryService(ApplicationProperties properties) {
        this.properties = properties;
        this.nexusArtifactoryClient = new NexusArtifactoryClient(properties);
    }

    @Override
    public void searchAsset(String group, String version) {
        var request = SearchApiRequest.builder()
                .repository(properties.getNexusRepository())
                .group(group)
                .version(version)
                .build();
        try {
            var searchApiResponse = nexusArtifactoryClient.search(request);
            System.out.println(searchApiResponse.toString());
        } catch (Exception e) {
            //log.error("Error occurred while searching asset: " + e.getMessage());
        }
    }

    @Override
    public void searchAndDownloadAsset(String name, String version, String mavenExtension) {
        var downloadPathPrefix = properties.getDownloadPathPrefix();
        var request = SearchApiRequest.builder()
                .repository(properties.getNexusRepository())
                .name(name)
                .version(version)
                .mavenExtension(mavenExtension)
                .build();
        try {
            nexusArtifactoryClient.searchAndDownload(request, downloadPathPrefix);
        } catch (Exception e) {
            //log.error("Error occurred while search and downloading asset: " + e.getMessage());
        }
    }
}
