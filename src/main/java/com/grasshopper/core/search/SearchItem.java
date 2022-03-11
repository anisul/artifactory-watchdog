package com.grasshopper.core.search;

import com.grasshopper.core.Asset;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchItem {
    String id;
    String repository;
    String format;
    String group;
    String name;
    String version;
    List<Asset> assets = new ArrayList<>();
}
