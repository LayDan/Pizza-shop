<#import "parts/common.ftl" as C>
<#import "parts/login.ftl" as L>


<@C.page>
<link rel="stylesheet" href="/static/registration.css"/>

<div class="container-fluid">

    <div class="container">
        <div class="registration-data">
            <form method="post">
                <div><label class="label"> <input class="input" type="text" name="username" placeholder=${login} /> </label></div>
                <div><label class="label"> <input class="input" type="password" name="password" placeholder=${password} /> </label></div>
                <div><label class="label"> <input class="input" type="text" name="firstName" placeholder= ${firstName} /> </label></div>
                <div><label class="label"> <input class="input" type="text" name="lastName" placeholder=${lastName} /> </label></div>
                <div><label class="label"> <input class="input" type="email" name="mail" placeholder=${email} /> </label></div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <div align="left">
                    <input type="submit" class="btn btn-success login-button" value=${registration} />
                </div>
            </form>
        </div>
    </div>
</div>
</@C.page>