
<#import "parts/common.ftl" as C>
<#import "parts/login.ftl" as L>

<@C.page>
<form action="/login" method="post">
    <div><label> ${login} : <input type="text" name="username"/> </label></div>
    <div><label> ${password} : <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div>
        <input type="submit" class="btn btn-success" value="Login"/>
    </div>
</form>
</@C.page>