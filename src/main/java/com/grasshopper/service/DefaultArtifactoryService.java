package com.grasshopper.service;

import com.grasshopper.client.NexusArtifactoryClient;
import com.grasshopper.core.ApplicationProperties;
import com.grasshopper.core.search.SearchApiRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;

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
            // TODO:show error
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
            var inputStream = nexusArtifactoryClient.searchAndDownload(request, downloadPathPrefix);

            var targetFile = new File(getDownloadPath(
                    downloadPathPrefix,
                    request.getName(),
                    request.getVersion(),
                    request.getMavenExtension()
            ));

            FileUtils.copyInputStreamToFile(inputStream, targetFile);
        } catch (Exception e) {
            // TODO:show error
        }
    }

    private String getDownloadPath(String pathPrefix, String name, String version, String extension) {
        return pathPrefix + name + "." + version + "." + extension;
    }
}
