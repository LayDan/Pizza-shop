<#import "parts/common.ftl" as C>
<@C.page>

<#list AllProduct as product>
<span>${product.id}</span> <br>
<span>${product.name}</span><br>
<span>${product.type}</span><br>
<#list product.priceFromSize?keys as key>
    ${key} = ${product.priceFromSize[key]}<br>
</#list>
<#else>
no bills
</#list>
<a href="/addProduct"> Add product</a>
</@C.page>