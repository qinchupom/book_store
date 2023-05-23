<template>
  <home-header></home-header>
  <writer-header></writer-header>
</template>

<script>
import HomeHeader from "@/pages/home/HomeHeader";
import WriterHeader from "@/pages/books/BooksHeader";
export default {
  name: "Writer",
  components:{
    HomeHeader,
    WriterHeader,
  }
}
</script>

<style scoped>

</style>