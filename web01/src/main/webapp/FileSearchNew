<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Choose File</title>
</head>
<body>
    <h1>选择文件夹</h1>
    <%
    String[] disks = (String[]) request.getAttribute("disks");
    if (disks != null) {
        for (String disk : disks) {
            %>
            <button onclick="exploreFolder('<%= disk %>')">
                <%= disk %>
            </button><br>
            <%
        }
    }
    %>

    <form id="uploadForm" action="IndexingServlet" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file" multiple>
        <input type="submit" value="确认" name="index">
    </form>

    <script>
    function exploreFolder(folderPath) {
        // 将选定的文件夹路径传递给后端
        var form = document.createElement('form');
        form.method = 'post';
        form.action = 'DiskExplorerServlet';
        form.innerHTML = '<input type="hidden" name="selectedDir" value="' + folderPath + '">';
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
    }
    </script>
</body>
</html>
