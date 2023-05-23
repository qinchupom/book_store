<template>
  <div class="box" :style="{ height: getheight() }">
    <div class="sbox">
      <div class="books">
        <el-row :gutter="20">
          <el-col v-for="book in Books" :key="book.id" :span="8">
            <img :src="book.book_img" :alt="book.name" class="books-img" />
            <div class="right-content">
              <div class="titles">
                <span>{{ book.name }}</span>
              </div>
              <div class="books-desc">
                <span>{{ book.desc }}</span>
              </div>
              <div class="books-writer">
                <div class="writer">
                  <img class="img-one" :src="book.writer_img" :alt="book.writer">
                  <span class="writer-name">{{ book.writer }}</span>
                </div>
                <span class="kind">{{ book.kind }}</span>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeBookshelves",
  data() {
    return {
      Books: [
        {
          "id": "349578",
          "name": "重生之高门主母",
          "book_img": "https://bookcover.yuewen.com/qdbimg/349573/c_24560358101610204/90.webp",
          "desc": "镇国公府世子李陵，英隽异勇，是个铮铮好男儿。他的娇妻沈氏却觉得跟他过得憋闷。成婚五年，她对他百般柔顺，他却对她没有丁点热乎劲。若单是因他性子冷，她也认了。",
          "writer": "鹊南枝",
          "writer_img": "https://yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/user.f22d3.png",
          "kind": "古代言情"
        },
        {
          "id": "34234",
          "name": "恃婚生骄",
          "book_img": "https://bookcover.yuewen.com/qdbimg/349573/c_24920432001880504/90.webp",
          "desc": "【甜文+娱乐圈+隐婚+1V1】【持美行凶精致孔雀型女明星X薄情寡言克制闷骚型总裁】",
          "writer": "槿郗",
          "writer_img": "https://yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/user.f22d3.png",
          "kind": "古代言情"
        },
        {
          "id": "343244",
          "name": "权臣的在逃白月光",
          "book_img": "https://bookcover.yuewen.com/qdbimg/349573/c_24078177401403004/90.webp",
          "desc": "上辈子，温凝被囚在裴宥身边，做了他的笼中鸟，掌中雀，每天不是在计划逃跑就是正在逃跑的路上，最终被他折断双翼，郁郁而终。",
          "writer": "西西东东",
          "writer_img": "https://yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/user.f22d3.png",
          "kind": "古代言情"
        },
        {
          "id": "34234",
          "name": "恃婚生骄",
          "book_img": "https://bookcover.yuewen.com/qdbimg/349573/c_24920432001880504/90.webp",
          "desc": "【甜文+娱乐圈+隐婚+1V1】【持美行凶精致孔雀型女明星X薄情寡言克制闷骚型总裁】",
          "writer": "槿郗",
          "writer_img": "https://yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/user.f22d3.png",
          "kind": "古代言情"
        }
      ]
    }
  },
  methods: {
    getheight(){
      var h = Math.ceil(this.Books.length / 3)
      return h * 144+ 32 + "px"
    }
  }

}
</script>

<style scoped>
.box {
  background-color: aliceblue;
  width: 100%;
  position: relative;

}

.sbox {
  background-color: #ffffff;
  width: 72%;
  min-width: 1000px;
  display: inline-block;
  position: absolute;
  margin: auto;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

.books {
  margin: 30px 60px;
}

.left-img {
  width: 86px;
  height: 115px;
  padding: 20px 10px;
}

.books-img {
  width: 80px;
  height: 120px;
}

.titles {
  font-size: 20px;
}

.right-content {
  height: 120px;
  float: right;
  display: inline;
  margin-top: -125px;
  margin-left: 100px;
}

.books-desc {
  width: 90%;
  text-indent: 2em;
  height: 62px;
  font-size: 12px;
  overflow: hidden;
  padding: 15px 0px;
}

.books-writer {
  position: relative;
}

.writer {
  padding: 10px;
}

.img-one {
  height: 20px;
  width: 20px;
}

.writer-name {
  font-size: 12px;
  color: #a6a6a6;
  padding-left: 5px;
}

.kind {
  float: right;
  margin-top: -30px;
  margin-right: 10px;
  font-size: 12px;
  color: #a6a6a6;
  border: 1px solid #e7e7e7;
  border-radius: 1px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
  margin-bottom: 20px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  background-color: beige;
}
</style>