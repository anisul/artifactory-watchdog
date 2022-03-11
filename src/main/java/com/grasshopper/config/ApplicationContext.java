package com.grasshopper.config;

import com.grasshopper.core.ApplicationProperties;

import java.io.InputStream;
import java.util.Properties;


public class ApplicationContext {
    ApplicationProperties applicationProperties;

    public ApplicationContext() {
        applicationProperties = new ApplicationProperties();
        loadProperties();
    }

    private void loadProperties() {
        try {
            var input = ApplicationContext.class.getClassLoader().getResourceAsStream("app.properties");

            var prop = new Properties();
            prop.load(input);

            applicationProperties.setNexusRepository(prop.getProperty("nexus.repository"));
            applicationProperties.setNexusServerPort(Integer.parseInt(prop.getProperty("nexus.server.port")));
            applicationProperties.setNexusServerHostname(prop.getProperty("nexus.server.hostname"));
            applicationProperties.setAssetDownloadEndpoint(prop.getProperty("nexus.server.api.asset.download.endpoint"));
            applicationProperties.setSearchEndpoint(prop.getProperty("nexus.server.api.search.endpoint"));
            applicationProperties.setDownloadPathPrefix(prop.getProperty("client.download.path.prefix"));
        } catch (Exception e) {
            //log.error("Error while loading properties: " + e.getMessage());
        }
    }

    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }
}
