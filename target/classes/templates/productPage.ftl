<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/productPageStyle.css"/>
<link rel="stylesheet" href="/static/forNotDoneFile.css"/>


<div class="container">
    <div class="left-column">
        <img src="/images/${product.imagePath}">
    </div>
    <div class="right-column">
        <div class="name-product">${product.name}</div>
        <div class="textDescription">${product.description}</div>
        <form method="post">
            <#list priceFromSize?keys as key>
            <div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input class="btn btn-primary selected" type="submit" value="${key}" name="size"><Br>
            </div>

        </#list>
        </form>
        <div class="price">
            <#if price??>
            <#if newPrice??>
            Price = <strike>${price}</strike><br>
            New price = ${newPrice}
            <#else>
            Price = ${price}
        </#if>
        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-success selected" type="submit" value="${price}" name="priceToBasket">Добавить в корзину</button>
        </form>
        <#else>
        Please select
    </#if>
</div>
<a href="/editProduct?productId=${product.id}">Edit</a>
<a href="/delete?productId=${product.id}">Delete</a>

</@C.page>
