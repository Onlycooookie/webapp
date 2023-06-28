package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {
    public String searchFiles(String folderPath, String keyword) {
        List<File> indexedFiles = indexFiles(folderPath);
        List<SearchResult> searchResults = searchFiles(indexedFiles, keyword);
        return formatResults(searchResults);
    }

    private List<File> indexFiles(String searchPath) {
        List<File> indexedFiles = new ArrayList<>();

        File folder = new File(searchPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return indexedFiles;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && (file.getName().endsWith(".pdf") || file.getName().endsWith(".doc")
                        || file.getName().endsWith(".docx"))) {
                    indexedFiles.add(file);
                } else if (file.isDirectory()) {
                    indexedFiles.addAll(indexFiles(file.getAbsolutePath()));
                }
            }
        }

        return indexedFiles;
    }

    private List<SearchResult> searchFiles(List<File> indexedFiles, String keyword) {
        List<SearchResult> results = new ArrayList<>();

        for (File file : indexedFiles) {
            List<String> matchedLines = new ArrayList<>();
            int lineNumber = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (line.contains(keyword)) {
                        matchedLines.add(lineNumber + "\t" + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!matchedLines.isEmpty()) {
                SearchResult result = new SearchResult(file.getAbsolutePath(), matchedLines);
                results.add(result);
            }
        }

        return results;
    }

    private String formatResults(List<SearchResult> results) {
        StringBuilder sb = new StringBuilder();

        if (results.isEmpty()) {
            sb.append("未找到匹配的文件。");
        } else {
            for (SearchResult result : results) {
                sb.append("路径：").append(result.getFilePath()).append("\n");

                for (String line : result.getMatchedLines()) {
                    sb.append("-----------------------------------\n");
                    sb.append(line).append("\n");
                }
            }
        }

        return sb.toString();
    }

    private static class SearchResult {
        private final String filePath;
        private final List<String> matchedLines;

        public SearchResult(String filePath, List<String> matchedLines) {
            this.filePath = filePath;
            this.matchedLines = matchedLines;
        }

        public String getFilePath() {
            return filePath;
        }

        public List<String> getMatchedLines() {
            return matchedLines;
        }
    }
}
