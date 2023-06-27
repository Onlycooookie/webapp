
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.FileSearcher" %>
<%@ page import="com.example.FileSaver" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查询结果</title>
</head>
<body>
<h1>查询结果</h1>
<%
    String keyword = request.getParameter("keyword");
    List<FileSearcher.SearchResult> results = FileSearcher.searchFiles(keyword);
    for (FileSearcher.SearchResult result : results) {
%>
    <h2>文件名：<%= result.getFileName() %></h2>
    <h3>路径：<%= result.getFilePath() %></h3>
    <table border="1">
        <tr>
            <th>行号</th>
            <th>包括关键字的内容行</th>
        </tr>
        <% for (Integer lineNumber : result.getLineNumbers()) { %>
        <tr>
            <td><%= lineNumber %></td>
            <td>
                <% String lineContent = readLineFromFile(result.getFilePath(), lineNumber); %>
                <%= lineContent %>
            </td>
        </tr>
        <% } %>
    </table>
    <br><br>
<%
}
%>
<form action="download.jsp" method="post">



