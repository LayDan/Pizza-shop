<#import "login.ftl" as l>
<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Pizza-Shop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if isAdmin>
            <li class="nav-item dropdown">
                <a href="/katalog" class="nav-link dropdown-toggle"  id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Каталог</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/katalog">Каталог</a>
                    <a class="dropdown-item" href="/addProduct">Добавить товар</a>
                </div>
            </li>
            <#else>
            <li class="nav-item">
                <a class="nav-link" href="/katalog" id="navbarDropdown2" role="button"
                   aria-haspopup="true" aria-expanded="false">Каталог</a>
            </li>
        </#if>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="/account" id="navbarDropdown1" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Аккаунт</a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="/account">Личный кабинет</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Корзина</a>
            </div>
        </li>
    </div>
    <#if name??>
    <div class="navbar-text mr-3">${name}</div>
    <@l.logout/>
    <#else>
    <a href="/login" class="btn btn-info mr-3">Login</a>
    <a href="/registration" class="btn btn-info mr-3">Registration</a>
</#if>
</nav>

