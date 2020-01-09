<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/product.css"/>
<div class="row">
    <div class="col-md-2">
        <form method="post">
            <div class="list-group">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div><a href="/katalog" name="type" type="submit" class="btn list-group-item">All</a></div>
                <#list catalog as type>
                <div><a href="/katalog?type=${type}" name="type" type="submit" class="btn list-group-item">${type}</a>
                </div>
            </#list>
        </form>
    </div>
</div>

<div class="col-md-8 products">
    <div class="row">
        <#list AllProduct as product>
        <div class="col-sm-3">
                <div class="product">
                    <div class="product-img">
                        <a href="#"><img src="/images/${product.imagePath}"></a>
                    </div>
                    <p class="product-title">
                        <a href="#">${product.name}</a>
                    </p>
                </div>
        </div>
    </#list>
</@C.page>