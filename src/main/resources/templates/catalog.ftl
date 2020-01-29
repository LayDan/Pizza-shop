<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/katalogStyle.css"/>
<!--<link rel="stylesheet" href="/static/style.css"/>-->
<form action="/catalog" method="post">
    <label> <input type="text" name="search"></label>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">Найти</button>
</form>
<div class="row">
    <div class="col-md-2">

        <div class="list-group">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div>
                <a href="/catalog" name="type" type="submit" class="btn list-group-item">All</a>
            </div>
            <#list types as type>
            <div>
                <a href="/catalog?type=${type}" name="type" type="submit" class="btn list-group-item">${type}</a>
            </div>
        </#list>

    </div>
</div>

<div class="col-md-8 products clearfix">
    <div class="row">
        <#list AllProduct as product>
        <div class="col-sm-3 product-wrapper">
            <div class="product">
                <div class="product-img">
                    <a href="/catalog/${product.id}"><img src="/images/${product.imagePath}"></a>
                </div>
                <div class="product-title">
                    <a href="/catalog/${product.id}">${product.name}</a>
                </div>
                <p class="product-description">
                    <a href="/catalog/${product.id}">${product.description}</a>
                </p>
            </div>
        </div>
    </#list>
</@C.page>