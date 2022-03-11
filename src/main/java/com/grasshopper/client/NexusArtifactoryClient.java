package com.grasshopper.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grasshopper.core.search.SearchApiResponse;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class NexusArtifactoryClient implements ArtifactoryClient {

    private static final String PROTOCOL = "http";
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8081;

    private final CloseableHttpClient client;
    private final HttpHost target;
    private final ObjectMapper mapper = new ObjectMapper();

    public NexusArtifactoryClient() {
        target = new HttpHost(HOST_NAME, 8081, PROTOCOL);

        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(getCredentialsProvider())
                .build();
    }

    private HttpClientContext getClientContext() {
        var authCache = new BasicAuthCache();
        authCache.put(target, new BasicScheme());

        var clientContext = HttpClientContext.create();
        clientContext.setAuthCache(authCache);
        return clientContext;
    }

    @Override
    public SearchApiResponse search(String repository, String group, String version) throws URISyntaxException, IOException {
        var request = buildSearchRequest(repository, group, version);

        var response = client.execute(target, request, getClientContext());

        return mapper.readValue(EntityUtils.toString(response.getEntity()), SearchApiResponse.class);
    }

    private CredentialsProvider getCredentialsProvider() {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "admin123")
        );
        return credentialsProvider;
    }

    private HttpGet buildSearchRequest(String repository, String group, String version) throws URISyntaxException {
        var builder = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST_NAME)
                .setPort(PORT)
                .setPath("/service/rest/v1/search")
                .addParameter("repository", repository)
                .addParameter("group", group)
                .addParameter("version", version);

        return new HttpGet(builder.build());
    }
}
