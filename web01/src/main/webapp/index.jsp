
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查询结果</title>
</head>
<body>
    <h1>查询结果</h1>

    <!-- 设置多个复选框 -->
    <form action="SaveResultsServlet" method="post">
        <input type="checkbox" name="result" value="结果1"> 结果1<br>
        <input type="checkbox" name="result" value="结果2"> 结果2<br>
        <input type="checkbox" name="result" value="结果3"> 结果3<br>
        <input type="submit" value="保存">
    </form>
</body>
</html>
