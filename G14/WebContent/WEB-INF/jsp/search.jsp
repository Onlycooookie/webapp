<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <title>搜索页面</title>
    <style>
        body {
            background: #f2f2f2;
        }
        .info-item{
            width: 100%;
            display: flex;
            align-items: flex-start;
            justify-content: flex-start;
        }
        .info-item>input{
            margin: 10px;
            margin-left: 0;
        }
        .content-wrap{
            width: 100%;
        }
        .content-item{
            width: 100%;
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            margin: 5px 0;
        }
        .content-item.bold{
            font-weight: bold;
        }
        .content-item>span:first-child{
            white-space: nowrap;
            margin-right: 10px;
        }
        .content-item>span:last-child{
            /*text-align: right;*/
            width: 85%;
            text-align: left;
            box-sizing: border-box;
        }
    </style>
</head>

<body>
<div class="container mt-5">
    <div class="card">
        <div class="card-body">
            <h5 class="mb-3">搜索页面</h5>
            <form class="form-inline" action="" method="post">
                <div class="form-group">
                    <label for="keywordInput">关键词：</label>
                    <input type="text" class="form-control" id="keywordInput" name="keyword"
                           value="${requestScope.keyword}" placeholder="输入关键词">
                </div>
                <button type="submit" class="btn btn-primary ml-2">搜索</button>
            </form>
            <div class="info-wrap">
                <c:forEach items="${requestScope.resultList}" var="item">
                    <div class="info-item">
                        <input type="checkbox" class="checkbox">
                        <div class="content-wrap">
                            <div class="content-item bold">
                                <span>路径</span>
                                <span class="path">${item.path}</span>
                            </div>
                            <c:forEach items="${item.list}" var="el">
                                <div class="content-item">
                                    <span>行号<a class="line-num">${el.lineNum}</a></span>
                                    <span class="content">${el.content}</span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <hr>
                </c:forEach>
            </div>
           <c:if test="${requestScope.resultList.size() > 0}" >
               <button type="button" class="btn btn-primary" onclick="saveResult()">保存</button>
           </c:if>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">

    let keyword = document.querySelector('#keywordInput').value

    document.querySelectorAll('.content-item .content').forEach(item =>{
        item.innerHTML = item.innerText.replaceAll(new RegExp(keyword, 'g'), `<span style="color: red">\${keyword}</span>`)
    })

    function saveResult() {
        let contentArr = []
        $('.checkbox:checked').each((index, item) =>{
            let infoItem = item.parentElement
            contentArr.push(['路径', infoItem.querySelector('.path').innerHTML])
            infoItem.querySelectorAll('.content-item:not(.bold)').forEach(el =>{
                contentArr.push([
                   '行号' +  el.querySelector('.line-num').innerHTML,
                    el.querySelector('.content').innerHTML
                ])
            })
            contentArr.push(['--------------'])
        })
        let resultText = contentArr.map(item => item.join('\t')).join('\n')
        downloadTextFile(resultText, 'searchResult.txt')
    }

    function downloadTextFile(text, fileName) {
        const blob = new Blob([text], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;

        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        URL.revokeObjectURL(url);
    }

</script>
</body>
</html>
