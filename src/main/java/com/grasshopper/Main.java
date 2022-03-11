package com.grasshopper;


import com.grasshopper.client.NexusArtifactoryClient;
import com.grasshopper.core.search.SearchApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        NexusArtifactoryClient client = new NexusArtifactoryClient();
        SearchApiResponse apiResponse = client.search("maven-local", "com", "SNAPSHOT-1.0.0");
        System.out.println(apiResponse.toString());
    }
}
