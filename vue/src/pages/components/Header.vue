<template>
 <div style="height: 70px; line-height:70px; border-bottom: 1px solid #ccc; display: flex">
   <div style="width: 300px; padding-left:20px; font-size:26px; font-weight: bold; color:dodgerblue">
     <img :src="imgUrl" class="icon" @click="gohome()">
    创客阅读管理系统</div>
   <div style="flex: 1"></div>
   <div style="width: 100px">
     <el-dropdown>
      <span class="el-dropdown-link">
        {{user.nickName}} <el-icon class="el-icon--right">
          <arrow-down />
          </el-icon>
      </span>
       <template #dropdown>
         <el-dropdown-menu>
           <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
         </el-dropdown-menu>
       </template>
     </el-dropdown>
   </div>
 </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "Header",
  props: ['user'],
  data(){
    return{
      user:[],
      imgUrl:require("../../assets/icon/readlogo.jpeg")
    }
  },
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user = JSON.parse(userStr)
  },
  methods:{
    gohome(){
      this.$router.push("/Home")
    },
    exit(){
      sessionStorage.removeItem("user")
      this.$router.push("/Home")
      ElMessage.success("退出系统成功")
    }
  }

}
</script>

<style scoped>
.icon {
  width: 45px;
  height: 45px;
  padding-top: 5px;
  padding-right: 10px;
}
</style>