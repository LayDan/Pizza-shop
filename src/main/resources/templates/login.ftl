<#import "parts/common.ftl" as C>
<#import "parts/login.ftl" as L>

<@C.page>
<link rel="stylesheet" href="/static/login.css"/>

<div class="container-fluid">
    <div class="container">
        <div class="login-data">
            <form action="/login" method="post">
                <div><label class="label"> <input class="input" type="text" name="username" placeholder=${login} /> </label></div>
                <div><label class="label"> <input class="input" type="password" name="password" placeholder=${password} /> </label></div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div>
                    <input type="submit" class="btn btn-success login-button" value="Login"/>
                    <a class="forgot" href="/forgotPassword">Забыли пароль?</a>
                </div>
            </form>
            
            <#if message??>
                <script>
                    alert("${message}")
                </script>
            </#if>
        </div>
    </div>
</@C.page>