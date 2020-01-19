<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/basketStyle.css"/>

<div class="container">
    <div class="left-column">
        <div class="col-md-8 products">
            <d class="row">
                <#list basket as b>
                <div class="col-sm-3 product-wrapper" id="myDIV">
                    <div class="product">
                        <div class="product-img">
                            <input type="hidden" name="checked" value=${b.id}/>
                            <img src="/images/${b.imagePath}">
                        </div>
                        <div class="product-title">
                            ${b.name}
                        </div>
                        <div class="product-price">
                            ${b.price}
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <div class="right-column">
        <div>${money}</div>
        <form action="/basket" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Оформить заказ</button>
        </form>
    </div>
</@C.page>