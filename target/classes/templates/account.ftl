<#import "parts/common.ftl" as C>

<@C.page>


Main Page
<div class="container-fluid" id="middle">
    <div class="row">
        <div class="col-lg-3">
            <span>${infoUser.id}</span>
        </div>
        <div class="col-lg-3">
            <span>${infoUser.username}</span>
        </div>
        <div class="col-lg-3">
            <span>${infoUser.firstName}</span>
        </div>
        <div class="col-lg-3">
            <span>${infoUser.bonus}</span>
        </div>
    </div>
</div>
<tr>
    <td><#list infoUser.roles as role>${role}<#sep>,</#list></td>
</tr>


</@C.page>