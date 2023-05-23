<template>
  <home-header></home-header>
  <home-swiper></home-swiper>
  <home-bookshelves></home-bookshelves>
  <home-list></home-list>
  <HomeFooter></HomeFooter>
</template>

<script>
import HomeHeader from "@/pages/home/HomeHeader";
import HomeSwiper from "@/pages/home/HomeSwiper";
import HomeBookshelves from "@/pages/home/HomeBookshelves";
import HomeList from "@/pages/home/HomeList";
import HomeFooter from "@/pages/home/HomeFooter";
export default {
  name: "Home",
  components:{
    HomeBookshelves,
    HomeHeader,
    HomeSwiper,
    HomeList,
    HomeFooter
  }
}
</script>

<style scoped>

</style>