<#import "parts/common.ftl" as C>
<@C.page>
<form method="post">
    <div><label> Code : <input type="text" name="code" placeholder="Code"/> </label></div>
    <div><label> Name: <input type="text" name="name" placeholder="Name"/> </label></div>
    <div><label> Type: <input type="text" name="type" placeholder="Type"/> </label></div>
    <div><label> Price: <input type="number" name="price" placeholder="Price"/> </label></div>
    <div><label> Description: <input type="text" name="description" placeholder="Description"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>

</form>
</@C.page>