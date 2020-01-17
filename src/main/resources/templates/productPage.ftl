<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/productPageStyle.css"/>


<div class="container">
    <div class="left-column">
        <img src="/images/${product.imagePath}">
    </div>
    <div class="right-column">
        <div class="name">${product.name}</div>
        <div class="textDescription">${product.description}</div>
        <#list priceFromSize?keys as key>
        <div class="btn btn-primary selected">
            ${key} = ${priceFromSize[key]}
        </div>
    </#list>
    </div>


</@C.page>
