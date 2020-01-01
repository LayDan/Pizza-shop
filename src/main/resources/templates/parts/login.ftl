<#macro login path>
<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div>
        <input type="submit" value="Sign In"/>
    </div>
</form>
</#macro>

<#macro registration>

<form method="post">
    <div><label> User Name : <input type="text" name="username" placeholder="Введите ваше login"/> </label></div>
    <div><label> Password: <input type="password" name="password" placeholder="Введите password"/> </label></div>
    <div><label> FirstName: <input type="text" name="firstName" placeholder="Введите ваше имя"/> </label></div>
    <div><label> LastName: <input type="text" name="lastName" placeholder="Введите Фамилию"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    <div><input type="submit" value="Sign In"/></div>

</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" value="Sign Out"/>
</form>
</#macro>