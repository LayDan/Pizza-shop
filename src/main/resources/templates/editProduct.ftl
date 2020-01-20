<#import "parts/common.ftl" as C>
<@C.page>
<form method="post" enctype="multipart/form-data">
    <div><label> Name: <input type="text" name="name" placeholder="Name"/> </label></div>
    <div><label> Stock : <input type="number" step="any" name="stock" placeholder="Stock"/></label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input class="btn btn-success" type="submit" value="Sign In"/></div>
</form>
</@C.page>