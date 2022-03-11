package com.grasshopper.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationProperties {
    String nexusServerHostname;
    int nexusServerPort;

    String assetDownloadEndpoint;
    String searchEndpoint;
    String nexusRepository;

    String downloadPathPrefix;
}
