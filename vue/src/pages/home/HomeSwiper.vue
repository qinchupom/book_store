<template>
  <div style="background-color: aliceblue; height: 300px; width: 100%; position: relative;">
    <div style="background-color: #ffffff;
      width: 72%;
      min-width: 1000px;

      height: 280px;
      position: absolute;
      margin: auto;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;">
      <el-carousel :interval="4000" type="card" height="200px" style="padding: 30px 10px" :options="swiperOption">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img style="object-fit: scale-down; width: 100%; height: 100%; border: antiquewhite" :src="item.idView" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeSwiper",
  data() {
    return {
      imgList: [
        { id: 0, idView: require("@/assets/img/1.jpg") },
        { id: 1, idView: require("@/assets/img/2.jpg") },
        { id: 2, idView: require("@/assets/img/3.jpg") },
        { id: 3, idView: require("@/assets/img/4.jpg") },
        { id: 4, idView: require("@/assets/img/5.jpg") }
      ]
    }
  }
}
</script>

<style scoped></style>