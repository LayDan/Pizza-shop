<#import "parts/common.ftl" as C>
<@C.page>
<form method="post" enctype="multipart/form-data">
    <div><label> Code : <input type="number" name="code" placeholder="Code"/> </label></div>
    <div><label> Name: <input type="text" name="name" placeholder="Name"/> </label></div>
    <div><label> Description: <input type="text" name="description" placeholder="Description"/> </label></div>
    <div><label> Image: <input type="file" name="file" placeholder="Image"/></label></div>
    <div>
        <label> Size and Price ('-' -связка, ';' - следующий размер и цена)<input type="text" name="priceSize"/></label>
    </div>
    <div><label> Type:
        <select name="type">
            <#list types as type>
            <option name="type">${type}</option>
            <#else>
            none
        </#list>
    </label>
    </div>


    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input class="btn btn-success" type="submit" value="Sign In"/></div>

</form>
</@C.page>