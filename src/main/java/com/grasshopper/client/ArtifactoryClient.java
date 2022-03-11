package com.grasshopper.client;

import com.grasshopper.core.search.SearchApiRequest;
import com.grasshopper.core.search.SearchApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ArtifactoryClient {
    SearchApiResponse search(SearchApiRequest request) throws URISyntaxException, IOException;
    void searchAndDownload(SearchApiRequest searchApiRequest, String downloadPathPrefix) throws URISyntaxException, IOException;
}
