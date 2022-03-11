package com.grasshopper;

import com.grasshopper.config.ApplicationContext;
import com.grasshopper.service.DefaultArtifactoryService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext();
        DefaultArtifactoryService service =
                new DefaultArtifactoryService(applicationContext.getApplicationProperties());

        //following call will search
        service.searchAsset("com", "RELEASE-1.0.0");
        //following call will search and download (destination is configurable in properties file)
        service.searchAndDownloadAsset("grasshopper", "RELEASE-1.0.0", ".java");
    }
}
