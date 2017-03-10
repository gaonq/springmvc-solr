<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: gaonq
  Date: 2017/3/4
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
</head>
<body>
<div id="app">
    当前登录用户 ${username}
    <form action="doLogin" v-bind:id="'form-'+ id" name="{{ id }}" method="post">
        用户名：<input type="text" name="username" v-model="message"><br/>
        密码：<input type="password" name="password" v-model="password"><br/>
        reverseMessage：<input type="text" name="reverseMessage" v-model="reverseMessage"><br/>
        <div v-if="true">输入正确</div>
        <span style="color:red"  v-once> {{ message }}</span>
        <div v-html="">{{ html }}</div>
        <span v-bind:id="'nan' | sex ">
            {{ 'nv' | sex }}
        </span>
        <%--<span v-on:click="{{ fullname }}">--%>
            <%--测试的--%>
        <%--</span>--%>
        <tolist></tolist>
        <testtemplate message-Notify="这个是component"></testtemplate>
        <button type="submit"  v-on:click="login">登录</button>
    </form>
</div>
<script src="js/login.js"></script>
</body>
</html>
