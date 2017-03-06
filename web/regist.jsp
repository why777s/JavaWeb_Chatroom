<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: wangzhaojun
  Date: 2017/2/27
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
    <link href="css/login.css" type="text/css" rel="stylesheet">
</head>
<body>

<%--请输入用户名和密码，然后点击注册按钮--%>
<%--<s:form action="registAction">--%>
    <%--<s:textfield name="userName"/>--%>
    <%--<s:password name="passWord"/>--%>
    <%--<s:submit value="注册"/>--%>
<%--</s:form>--%>

<div class="login">
    <div class="message">注册账户</div>
    <div id="darkbannerwrap"></div>
    <%--注意struts获取Html表单就目前看来如果直接使用html的表单是不行的，要用s:from标签 才能正常拦截Action--%>
    <s:form method="post" id="theform" action="registAction">
        <%--<input name="action" value="login" type="hidden">--%>
        <%--特别注意name大小写 要userName 如果是username就会出错！--%>
        <input name="userName" placeholder="用户名" required="" type="text">
        <hr class="hr15">
        <input name="passWord" placeholder="密码" required="" type="password">
        <hr class="hr15">
        <input value="注册" style="width:100%;" type="submit">
        <hr class="hr20">
    </s:form>
</div>

</body>
</html>
