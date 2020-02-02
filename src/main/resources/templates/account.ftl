<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/forNotDoneFile.css"/>

<div class="container">
    <div class="main-window-info">
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