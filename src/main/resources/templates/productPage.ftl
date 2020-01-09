<#import "parts/common.ftl" as C>

<@C.page>
<link rel="stylesheet" href="/static/style.css"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <div class="image">
                <img src="/images/${product.imagePath}">
            </div>
            <div class="name">
                <label >${product.name}</label>
            </div>
            <div class="description">

            </div>
            <div class="price">

            </div>
            <div class="size">

            </div>
        </div>
    </div>
</@C.page>