<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Supcon</title>
    <link href="${springMacroRequestContext.contextPath}/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="${springMacroRequestContext.contextPath}/oauth/loginCheck">
        <h2 class="form-signin-heading">单点登录页面</h2>
        <p>
            <label for="username" class="sr-only">用户名</label> <input type="text"
                                                                     id="username" name="username" class="form-control"
                                                                     placeholder="用户名" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">密码</label> <input
                    type="password" id="password" name="password" class="form-control"
                    placeholder="密码" required>
        </p>
        <input name="_csrf" type="hidden"
               value="fdac8be9-412f-4963-ad24-08902a2a3c64"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>
</div>
</body>
</html>