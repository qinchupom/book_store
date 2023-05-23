<template>
  <div style="background-color: aliceblue;width: 100%;height:800px;position: relative">
    <div style="width: 72%;height: 50px;margin: auto;padding: 15px 0">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>现代言情</el-breadcrumb-item>
        <el-breadcrumb-item>偷吻月光</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="width: 72%;height: 290px;margin: auto;background-color: #ffffff">
      <div class="booksimg">
        <img src="https://bookcover.yuewen.com/qdbimg/349573/c_24924935109289504/180.webp" alt=""
        style="padding: 22px">
      </div>
      <div class="bookscontent" style="width: 800px;height: 290px;float: right;display: inline-block;position:relative;border: 1px solid #eaeaea;">
        <div>
          <span>偷吻月光</span>
          <span>匪匪有意 著</span>
          <span>上架时间 2023-03-20 22:40:26</span>
        </div>
        <div>
          <span class="bookslabel">连载中</span>
          <span class="bookslabel">签约</span>
          <span class="bookslabel">VIP</span>
          <span class="bookslabel">现代言情</span>
          <span class="bookslabel">都市生活</span>
        </div>
       <div>
         <span>47.82</span>
         <span>万字 | </span>
         <span>1104</span>
         <span>总收藏</span>
       </div>
        <div>
          <p>
            【医学生VS神经外科医生】
            云糯在二十岁这年喜欢上了周崇月。两人年龄、辈分和阅历的差距，让她一次次望而却步，以至于在一起后，迫于各方压力，她强烈要求地下恋。
            面对女孩的坚持，男人嘴上答应，实则明里暗里，无时无刻不在宣示自己的主权。
            某次团建，科室新来的实习生云糯抽到真心话。
            同事问：“在场所有男性中，有没有你喜欢的类型？”
            云糯说：“没有。”
            同事点头正准备继续，坐于角落的周医生却淡声打断：“刚刚那个问题，让她重新答。”
            众人：？？
            团建结束后，云糯路过洗手间时，被同科室的一名规培生师兄拦住表白。她不知所措愣在原地，还没开口，旁边男厕就走出来一人。
            周崇月一边洗着手一边警告：“最好死了这条心，她家长不许。”
            “她家长？”
            “我。”
            云糯：……
            众人眼中的周崇月：医术高超，为人正派且自律。
            云糯眼中的周崇月：年纪大，会疼人，就是心眼小。
            但无论哪一面，云糯觉得，有些人从一出生起，就注定要成为她的英雄。
            *大叔和少女，年龄差12岁。
            *双C，无虐，暗搓搓的甜。
          </p>
        </div>
        <div>
          <button>免费试读</button>
          <button>加入书架</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ArrowRight } from '@element-plus/icons'
export default {
  name: "WriterHome",
  components:{
    ArrowRight
  }
}
</script>

<style scoped>

</style>