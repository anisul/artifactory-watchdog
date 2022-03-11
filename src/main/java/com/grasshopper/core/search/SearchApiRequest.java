package com.grasshopper.core.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchApiRequest {
    private String repository;
    private String group;
    private String name;
    private String version;
    private String mavenExtension;
}
