package com.grasshopper.core;

import lombok.Data;

@Data
public class Checksum {
    String sha1;
    String md5;
    String sha256;
    String sha512;
}
