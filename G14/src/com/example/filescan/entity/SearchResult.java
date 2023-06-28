package com.example.filescan.entity;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private String path;

    private List<SearchResultItem> list = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SearchResultItem> getList() {
        return list;
    }

    public void setList(List<SearchResultItem> list) {
        this.list = list;
    }
}
