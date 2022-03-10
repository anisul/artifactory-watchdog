package com.grasshopper.client;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ArtifactoryClient {
    String search(String repository, String group, String version) throws URISyntaxException, IOException;
}
