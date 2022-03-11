package com.grasshopper.core.search;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchApiResponse {
    List<SearchItem> items = new ArrayList<>();
    String continuationToken;
}
