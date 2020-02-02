<#import "login.ftl" as l>
<#include "security.ftl">
<nav class="navbar navbar-expand-lg my-navbar">
    <a class="navbar-brand" href="/">Pizza-Shop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if isAdmin>
            <li class="nav-item dropdown">
                <a href="/catalog" class="nav-link dropdown-toggle" id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${catalog}</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/catalog">${catalog}</a>
                    <a class="dropdown-item" href="/addProduct?size=1">Добавить товар</a>
                </div>
            </li>
            <#else>
            <li class="nav-item">
                <a class="nav-link" href="/catalog" id="navbarDropdown2" role="button"
                   aria-haspopup="true" aria-expanded="false">${catalog}</a>
            </li>
        </#if>
        <#if isAdmin>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="/account" id="navbarDropdown3" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${account}</a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="/account">${account}</a>
                <a class="dropdown-item" href="/administrationPanel">Админ-панель</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/basket">${basket}</a>
            </div>
        </li>
        <#else>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="/account" id="navbarDropdown1" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${account}</a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="/account">${account}</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/basket">${basket}</a>
            </div>
        </li>
        </#if>
    </div>
    <#if name??>
    <div class="navbar-text mr-3 name">${name}</div>
    <@l.logout/>
    <#else>
    <a href="/login" class="btn btn-info mr-3">${login}</a>
    <a href="/registration" class="btn btn-info mr-3">${registration}</a>
</#if>
</nav>

