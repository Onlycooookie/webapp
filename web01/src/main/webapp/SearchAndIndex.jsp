<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>选择文件夹</title>
</head>
<body>
    <h1>选择文件夹</h1>
    <%
    java.util.HashMap<String, java.util.List<String>> fileIndex = (java.util.HashMap<String, java.util.List<String>>) request.getAttribute("fileIndex");
    if (fileIndex != null) {
        for (java.util.Map.Entry<String, java.util.List<String>> entry : fileIndex.entrySet()) {
            %>
            <div>
                文件名： <%= entry.getKey() %> <br>
                内容：<br>
                <%
                java.util.List<String> content = entry.getValue();
                for (int i = 0; i < content.size(); i++) {
                    %>
                    <%= i+1 %>: <%= content.get(i) %><br>
                    <%
                }
                %>
            </div>
            <br>
            <%
        }
    }
    %>

    <div id="searchDiv">
        <h2>关键字查询</h2>
        <input type="text" id="keywordInput">
        <button onclick="searchKeyword()">查询</button>
    </div>

    <div id="searchResultDiv"></div>

    <script>
    function searchKeyword() {
        var keyword = document.getElementById("keywordInput").value;

        // 使用 AJAX 向后端发送查询请求
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var searchResult = JSON.parse(this.responseText);
                displaySearchResult(searchResult);
            }
        };
        xhttp.open("GET", "SearchServlet?keyword=" + keyword, true);
        xhttp.send();
    }

    function displaySearchResult(result) {
        var resultDiv = document.getElementById("searchResultDiv");
        resultDiv.innerHTML = "";

        for (var i = 0; i < result.length; i++) {
            var filename = result[i].filename;
            var lineNumber = result[i].lineNumber;
            var content = result[i].content;

            var fileInfo = document.createElement("div");
            fileInfo.innerHTML = "文件名     " + filename + "<br>" +
                                 "行号: " + lineNumber + "<br>" +
                                 "       " + content + "<br>";

            resultDiv.appendChild(fileInfo);
        }
    }
    </script>
</body>
</html>
