<#import "parts/common.ftl" as C>

<@C.page>
Main Page
<span>${infoUser.id}</span>
<span>${infoUser.username}</span>
<span>${infoUser.firstName}</span>
<span>${infoUser.bonus}</span>

<tr>
    <td><#list infoUser.roles as role>${role}<#sep>,</#list></td>
</tr>
<a href="/catalog">${catalog}</a>
</@C.page>