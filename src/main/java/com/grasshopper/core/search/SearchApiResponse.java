package com.grasshopper.core.search;

import java.util.ArrayList;
import java.util.List;

public class SearchApiResponse {
    List<SearchItem> items = new ArrayList<>();
    String continuationToken;

    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }
}
