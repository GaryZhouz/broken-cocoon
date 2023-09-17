<template>
  <div class='main-box'>
    <ul class='product-list-left'>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
    </ul>
    <ul class='product-list-right'>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
      <li class='product-item'>
        <ProductItem title='' product-banner-url='' price='' />
      </li>
    </ul>
  </div>
</template>

<script setup lang='ts'>
import ProductItem from '@/components/ProductItem.vue'
</script>

<style scoped lang='scss'>
@import "../assets/commmon.css";

.main-box {
  width: 100%;
  height: 100%;
  background-color: #f4f4f4;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.product-list-left, .product-list-right {
  width: 50%;
}

.product-item {
  display: block;
  list-style: none;
  padding: 0.5rem;
}

</style>
