package com.grasshopper;


import com.grasshopper.client.NexusArtifactoryClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        NexusArtifactoryClient client = new NexusArtifactoryClient();
        System.out.println(client.search("maven-local", "com", "RELEASE-1.0.0"));
    }
}
