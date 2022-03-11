package com.grasshopper.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grasshopper.core.ApplicationProperties;
import com.grasshopper.core.search.SearchApiRequest;
import com.grasshopper.core.search.SearchApiResponse;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class NexusArtifactoryClient implements ArtifactoryClient {

    private final ApplicationProperties properties;

    private static final String PROTOCOL = "http";
    private static String hostName;
    private static int port;

    private final CloseableHttpClient client;
    private final HttpHost target;
    private final ObjectMapper mapper = new ObjectMapper();

    public NexusArtifactoryClient(ApplicationProperties applicationProperties) {
        properties = applicationProperties;

        hostName = applicationProperties.getNexusServerHostname();
        port = applicationProperties.getNexusServerPort();

        target = new HttpHost(hostName, 8081, PROTOCOL);

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
    public SearchApiResponse search(SearchApiRequest request) throws URISyntaxException, IOException {
        var httpGetRequest = buildSearchRequest(request);

        var response = client.execute(target, httpGetRequest, getClientContext());

        return mapper.readValue(EntityUtils.toString(response.getEntity()), SearchApiResponse.class);
    }

    @Override
    public InputStream searchAndDownload(SearchApiRequest searchApiRequest, String downloadPathPrefix) throws URISyntaxException, IOException {
        var request = buildSearchAndDownloadRequest(searchApiRequest);

        var response = client.execute(target, request, getClientContext());

        return response.getEntity().getContent();
    }

    private CredentialsProvider getCredentialsProvider() {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "admin123")
        );
        return credentialsProvider;
    }

    private HttpGet buildSearchAndDownloadRequest(SearchApiRequest request) throws URISyntaxException {
        var uriBuilder = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(hostName)
                .setPort(port)
                .setPath(properties.getAssetDownloadEndpoint())
                .addParameter("repository", request.getRepository())
                .addParameter("name", request.getName())
                .addParameter("version", request.getVersion())
                .addParameter("maven.extension", request.getMavenExtension());

        return new HttpGet(uriBuilder.build());
    }

    private HttpGet buildSearchRequest(SearchApiRequest request) throws URISyntaxException {
        var uriBuilder = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(hostName)
                .setPort(port)
                .setPath(properties.getSearchEndpoint())
                .addParameter("repository", request.getRepository())
                .addParameter("group", request.getGroup())
                .addParameter("version", request.getVersion());

        return new HttpGet(uriBuilder.build());
    }
}
