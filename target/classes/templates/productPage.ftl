<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/productPageStyle.css"/>
<div>
    <div class="image-main">
        <img src="/images/${product.imagePath}">
    </div>
    <div class="info-main">
        <label class="name">${product.name}</label>
        <label class="textDescription">${product.description}</label>
    </div>
    <div>
        <#list priceFromSize?keys as key>
        ${key} = ${priceFromSize[key]}
    </#list>
</div>
</@C.page>
