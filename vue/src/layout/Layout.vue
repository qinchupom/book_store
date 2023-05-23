<template>
  <div class="common-layout">
   <Header style="height: 70px"></Header>
    <el-container>
      <Aside></Aside>
      <router-view style="flex: 1;margin-top: 16px;"></router-view>
    </el-container>
  </div>
</template>

<script>

import Header from "../pages/components/Header";
import Aside from "../pages/components/Aside";
export default {
  name: "Layout",
  components:{
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>