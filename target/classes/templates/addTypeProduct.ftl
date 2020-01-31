<#import "parts/common.ftl" as C>

<@C.page>
<form method="post" enctype="multipart/form-data" xmlns="http://www.w3.org/1999/html">
    <div><label> TypeProduct : <input type="text" name="type" placeholder="TypeProduct"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input class="btn btn-success" type="submit" value="Sign In"/></div>
</form>
</@C.page>