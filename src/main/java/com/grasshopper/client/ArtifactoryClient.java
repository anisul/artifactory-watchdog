package com.grasshopper.client;

import com.grasshopper.core.search.SearchApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ArtifactoryClient {
    SearchApiResponse search(String repository, String group, String version) throws URISyntaxException, IOException;
}
