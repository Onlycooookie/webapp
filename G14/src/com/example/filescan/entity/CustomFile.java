package com.example.filescan.entity;

import java.util.ArrayList;
import java.util.List;

public class CustomFile {

    private String path;
    private List<String> paragraph = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getParagraph() {
        return paragraph;
    }

    public void setParagraph(List<String> paragraph) {
        this.paragraph = paragraph;
    }
}
