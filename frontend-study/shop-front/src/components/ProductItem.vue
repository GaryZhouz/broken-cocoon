<template>
  <div class='product-item-box'>
    <div class='product-banner'>
      <img class='banner' src='https://gw.alicdn.com/img/i4/133751438/O1CN01KO1RxC1MUeE5meeC4_!!0-saturn_solar.jpg_310x310xzq90.jpg'
           alt='商品图片' />
    </div>

    <div class='product-desc'>
      <h4 class='title'>[38预售提前加购]Murad慕拉</h4>
      <span class='price'>
        <span class='price-unit'>¥</span>
        <span class='price-value'>1250</span>
      </span>
    </div>
  </div>
</template>

<script setup lang='ts'>
defineProps<{
  productBannerUrl: string
  title: string
  price: number
}>()
</script>

<style scoped lang='scss'>
.product-item-box {
  display: flex;
  flex-direction: column;
  width: 100%;
}


.product-banner {
  height: 80%;
  width: 100%;
  background-color: white;

  .banner {
    width: 100%;
    height: 100%;
    border-radius: 0.4rem 0.4rem 0 0;
  }
}

.product-desc {
  width: 100%;
  height: 20%;
  background-color: white;
  border-radius: 0 0 0.4rem 0.4rem;

  .title {
    color: #333;
    font-weight: normal;
    font-size: 14px;
    margin: 0;
    padding: 0 0.3rem 0 0.3rem;
  }

  .price {
    color: #FF2828;
    padding-left: 0.3rem;

    .price-unit {
      font-size: 12px;
    }

    .price-value {
      font-weight: bold;
      margin-left: 1px;
    }
  }
}
</style>
