<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/basketStyle.css"/>

<div class="container">
    <div class="left-column">
        <div class="col-md-8 products">
            <div class="row">
                <#list productFromBasket?keys as key>

                <div class="col-sm-3 product-wrapper" id="myDIV">
                    <div class="product">
                        <div class="product-img">
                            <input type="hidden" name="checked" value=${productFromBasket[key].id}/>
                            <img src="/images/${productFromBasket[key].imagePath}">
                        </div>
                        <div class="product-title">
                            ${productFromBasket[key].name}
                        </div>
                        <div class="product-price">
                            ${key}
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <div class="right-column">
        <#if success??>
        <div>${success}</div>
        <#else>
        <div>${money}</div>
    </#if>
    <form action="/basket" method="post">
        <label><input type="checkbox" name="delivery" value=0>Забрать в магазине</label>
        <label><input type="checkbox" name="delivery" value=40>Курьер</label>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" name="status" value=""/>
        <button type="submit">Оформить заказ</button>
    </form>
</@C.page>