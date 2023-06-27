
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.FileSaver" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>下载查询结果</title>
</head>
<body>
<h1>下载查询结果</h1>
<%
    String fileName = "searchResults.txt";
    List<String> contents = new ArrayList<>();
    String keyword = request.getParameter("keyword");
    List<FileSearcher.SearchResult> results = FileSearcher.searchFiles(keyword);
    for (FileSearcher.SearchResult result : results) {
        contents.add("文件名：" + result.getFileName());
        contents.add("路径：" + result.getFilePath());
        contents.add("行号：");
        for (Integer lineNumber : result.getLineNumbers()) {
            contents.add(lineNumber.toString());
        }
        contents.add("");
    }

    FileSaver.saveToFile(contents, fileName);
%>
<p>查询结果已保存到文件：<%= fileName %></p>
<a href="<%= request.getContextPath() %>/Download/<%= fileName %>">下载</a>
</body>
</html>




