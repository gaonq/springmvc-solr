<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: gaonq
  Date: 2017/3/4
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>搜索</title>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<div class="container">
<div class="form-group">
       <select name="searchtype" ID="searchtype" class="form-control">
          <option value="database">数据库</option>
          <option value="file">文档</option>
        </select>
    <input type="text" class="form-control" placeholder="搜索关键词" name="keyword" id="keyword"/>

    <span class="input-group-btn">
    <button type="button" class="btn" value="搜索" id="btnSearch" >搜索</button>
        <button type="button" class="btn btn-default" id="btnAddDocument" >新建索引</button>
    </span>
</div>
<div>
    搜索<span id="keywordTxt"></span>,为您找到相关结果约<span id="resultCount"></span>个，耗时<span id="qtime"></span>毫秒。
</div>
<div>
    <ui class="list-group" id="list">
        <c:forEach var="list" items="${list}">
            <li class="list-group-item">
                ${list.exception}
            </li>
        </c:forEach>
    </ui>
</div>
</div>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="/js/search.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
