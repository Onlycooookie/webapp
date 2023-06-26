
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Save</title>
</head>
<body>
    <h1>Save</h1>

    <form action="SaveResultsServlet" method="post">
        <ul>
            <% for (int i = 1; i <= 3; i++) { %>
                <li>
                    <input type="checkbox" name="result" value="<%= "路径" + i %>"> <%= "路径" + i %>
                    <ul>
                        <li>列表项 1</li>
                        <li>列表项 2</li>
                        <li>列表项 3</li>
                    </ul>
                </li>
            <% } %>
        </ul>
        <input type="submit" value="下载">
    </form>

    <!-- 显示保存成功消息 -->
    <% String saveSuccessMsg = (String) request.getAttribute("saveSuccessMsg");
       if (saveSuccessMsg != null) { %>
        <p><%= saveSuccessMsg %></p>
    <% } %>
</body>
</html>
