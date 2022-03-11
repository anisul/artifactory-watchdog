package com.grasshopper.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Asset {
    String downloadUrl;
    String path;
    String id;
    String repository;
    String format;
    @JsonIgnore
    Checksum checksum;
    String contentType;
    String lastModified;
    String uploader;
    String uploaderIp;
    Long fileSize;
    Maven2 maven2;
}
