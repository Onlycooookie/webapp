<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/layer/layer.js"></script>
  <style>
    body {
      background: #f2f2f2;
    }
  </style>
  <title>建立索引</title>
</head>

<body>
<div class="container p-5">
  <div class="card">
    <div class="card-body">
      <h5 class="mb-2">建立索引</h5>
      <form action="" method="post" onsubmit="layer.load()">
        <div class="form-group">
          <p>${sessionScope.path}</p>
          <label for="driveSelect">选择一个目录：</label>
          <select name="path" class="form-control" id="driveSelect" >
            <c:forEach items="${sessionScope.driveList}" var="item">
              <option value="${item}">${item}</option>
            </c:forEach>
          </select>
        </div>
        <button type="submit" name="type" value="enter" class="btn btn-primary">进入</button>
        <button type="submit" name="type" value="back" class="btn btn-warning">返回</button>
        <button type="submit" name="type" value="confirm" class="btn btn-primary">提交</button>
      </form>
    </div>
  </div>
</div>


</body>

</html>
