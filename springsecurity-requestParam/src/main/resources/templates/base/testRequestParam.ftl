testRequestParam
username : ${username!'空值 '}


<form action="/base/testModelAttribute" method="post">
    <label>名称</label><input name="name" type="text"><br/>
    <label>密码</label><input name="password" type="password"><br/>
    <label>权限</label><input name="permission" type="text"><br/>
    <button type="submit">提交</button>
</form>