<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/style.css"/>

<div class="container-fluid" id="accountInfo">
    <div id="characteristicsInfo">
        <div class="info">
            <label> Name : ${infoUser.firstName}</label>
        </div>
        <div class="info">
            <label> Last Name : ${infoUser.lastName}</label>
        </div>
        <div class="info">
            <label> Login : ${infoUser.username}</label>
        </div>
        <div class="info">
            <label> Bonus : ${infoUser.bonus}</label>
        </div>
    </div>
</div>


</@C.page>