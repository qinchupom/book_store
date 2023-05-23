<template>
  <div class="box">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
      <el-menu-item index="0">
        <img src="../../assets/icon/readlogo.jpeg" style="width: 45px; height:45px" alt="">
        <p style="font-size:26px; font-weight: bold; color:dodgerblue">创客阅读</p>
      </el-menu-item>
      <el-input v-model="input2" class="w-50 m-2" placeholder="请输入要查询的书名/作者" :suffix-icon="Search"
        style="width: 500px; margin-top: 10px; margin-left: 100px" />
      <div class="demo-type">
        <div class="user" @click="userClick()">
          <p>登录/注册</p>
          <el-avatar :size="50" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        </div>
      </div>
    </el-menu>
    <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect" style="border: none">
      <el-sub-menu index="2">
        <template #title>全部分类</template>
        <el-menu-item index="2-1">现代言情</el-menu-item>
        <el-menu-item index="2-2">古代言情</el-menu-item>
        <el-menu-item index="2-3">浪漫青春</el-menu-item>
        <el-menu-item index="2-4">仙侠奇缘</el-menu-item>
        <el-menu-item index="2-5">悬疑</el-menu-item>
        <el-menu-item index="2-6">科幻空间</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="1">排行榜</el-menu-item>
      <el-menu-item index="3">免费</el-menu-item>
      <el-menu-item index="4">完本</el-menu-item>
      <a style="margin-left: auto;margin-top: 18px">作家专区</a>
    </el-menu>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Search } from '@element-plus/icons'
import router from "@/router"

const activeIndex = ref('1')
const input2 = ref('')

export default {
  name: "HomeHeader",
  components: { Search },
  created() {
    let userJson = sessionStorage.getItem("user")
    if (!userJson) {
      document.getElementsByClassName("user")
    }else{
      // document.querySelector("p.user").innerHTML = "vv";
    }
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  },
  methods: {
    userClick() {
      if (JSON.stringify(this.user) == "{}") {
        router.push("/login")
      }else{
        console.log(this.user.nickName)
        // router.push("/dashboard")
      }
    },// 用户点击用户自定义按钮
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },
  },

}
</script>

<style scoped>
.box {
  padding: 15px 100px;
  background-color: #ffffff;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.demo-type {
  display: flex;
  margin-left: auto;
}

.demo-type>div {
  flex: 1;
  text-align: center;
}

.user p {
  user-select: none;
  float: left;
  transform: translateY(100%);
  transition: 0.55s;
}

.user:hover{
  color: aquamarine;
}

.user span {
  float: right;
  margin: 5px;
}

</style>