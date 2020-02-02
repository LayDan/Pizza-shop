<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/katalogStyle.css"/>


<#if PromotionProducts??>
ТОП НЕДЕЛИ ПО СКИДКАМ<br>
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
            <#list PromotionProducts as product>
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
    </#if>
</@C.page>