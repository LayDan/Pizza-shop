<#import "parts/common.ftl" as C>
<@C.page>

<#list AllProduct as product>
<span>${product.id}</span>
<span>${product.name}</span>
<span>${product.price}</span>
<span>${product.type}</span>
<#else>
no bills
</#list>
<a href="/addProduct"> Add product</a>
</@C.page>