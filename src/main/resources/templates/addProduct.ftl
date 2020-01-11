<#import "parts/common.ftl" as C>
<@C.page>
<form method="post" enctype="multipart/form-data">
    <div><label> Code : <input type="number" name="code" placeholder="Code"/> </label></div>
    <div><label> Name: <input type="text" name="name" placeholder="Name"/> </label></div>
    <div>Description:<textarea name="description"> </textarea></div>
    <div><label> Image: <input type="file" name="file" placeholder="Image"/></label></div>
    <table>
        <thead>
        <tr>
            <th>Size</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <#list array as i>
        <tr>
            <td>${i}<input type="text" name="sizeProduct"></td>
            <td><input type="number" min="0" step="any" name="priceForProduct"></td>

        </tr>
        <#assign
        size=i+1
        num="/addProduct?size="+size
        >
        </#list>
    </table>
    <a name="size" href=${num}>Добавить</a>


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