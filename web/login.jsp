<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: wangzhaojun
  Date: 2017/2/21
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>登录界面</title>
    <link href="css/login.css" type="text/css" rel="stylesheet">
    <%--<script  type="text/javascript">--%>
      <%--function reg_click() {--%>
          <%--document.getElementsByClassName("theform").action="registAction";--%>
      <%--}--%>
    <%--</script>--%>
        </head>

  <body>



  <div class="login">
    <div class="message">在线聊天室登录</div>
    <div id="darkbannerwrap"></div>
    <%--注意struts获取Html表单就目前看来如果直接使用html的表单是不行的，要用s:from标签 才能正常拦截Action--%>
    <s:form method="post" id="theform" action="loginAction">
      <%--<input name="action" value="login" type="hidden">--%>
      <%--特别注意name大小写 要userName 如果是username就会出错！--%>
      <input name="userName" placeholder="用户名" required="" type="text">
      <hr class="hr15">
      <input name="passWord" placeholder="密码" required="" type="password">
      <hr class="hr15">
      <input value="登录" style="width:100%;" type="submit">
      <!-- <input value="注册" style="width:100%;" type="submit"> -->
      <hr class="hr20">
    </s:form>
    <a href="regist.jsp">
      <input value="注册"  style="width:100%;" type="submit">
    </a>
  </div>

  <%--<s:form action="loginAction" >--%>
  <%--&lt;%&ndash;<s:textfield  name="userName"/>&ndash;%&gt;--%>
  <%--&lt;%&ndash;<s:password name="passWord"/>&ndash;%&gt;--%>
  <%--<input type="text" name="userName">--%>
  <%--<input type="password" name="passWord">--%>
  <%--<s:submit key="login" value="登录"/>--%>
  <%--</s:form>--%>


  <%--<br/>--%>
  <%--<br/>--%>
  <%--<h4>如果没有账户，请注册！</h4>--%>

  <%--<a href="regist.jsp"><input type="button" value="前往注册页面"></a>--%>


  </body>
</html>
