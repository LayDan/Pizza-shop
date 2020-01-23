<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" class="btn btn-danger" value="Logout"/>
</form>
</#macro>