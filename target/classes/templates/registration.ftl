<#import "parts/common.ftl" as C>
<#import "parts/login.ftl" as L>

<@C.page>
<form method="post">
    <div><label> ${login} : <input type="text" name="username"/> </label></div>
    <div><label> ${password} : <input type="password" name="password"/> </label></div>
    <div><label> ${firstName} : <input type="text" name="firstName"/> </label></div>
    <div><label> ${lastName} : <input type="text" name="lastName"/> </label></div>
    <div><label> ${email} : <input type="email" name="mail"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    <div><input type="submit" class="btn btn-success" value="Submit"/></div>

</form>
</@C.page>