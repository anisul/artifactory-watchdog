package com.grasshopper.client;

import org.apache.http.HttpEntity;
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
    private final HttpClientContext clientContext;

    public NexusArtifactoryClient() {
        target = new HttpHost(HOST_NAME, 8081, PROTOCOL);

        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "admin123")
        );

        AuthCache authCache = new BasicAuthCache();
        authCache.put(target, new BasicScheme());

        clientContext = HttpClientContext.create();
        clientContext.setAuthCache(authCache);

        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
    }

    @Override
    public String search(String repository, String group, String version) throws URISyntaxException, IOException {
        HttpGet request = buildSearchRequest(repository, group, version);

        var response = client.execute(target, request, clientContext);

        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
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
