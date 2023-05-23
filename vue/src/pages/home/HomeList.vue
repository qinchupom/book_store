<template>
  <div :style="{ height: getheight() }" style="background-color: aliceblue; width: 100%;position: relative;">
    <div style="background-color: #ffffff;
      width: 72%;
      min-width: 1000px;
      position: absolute;
      margin: auto;
      left: 0;
      right: 0;
      top: 12px;
      bottom: 0;">
      <el-table class="ones" :data="tableData" :header-cell-style="{ background: 'skyblue', color: '#000' }">
        <el-table-column prop="list1" label="华语言情风云榜" width="320" />
        <el-table-column prop="list2" label="热销榜" width="360" />
        <el-table-column prop="list3" label="更新榜" />
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeList",
  data() {
    return {
      tableData: [
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },
        {
          list1: '穿成权臣的首富娇',
          list2: '穿成权臣的首富娇',
          list3: '归梧谣：腹黑皇帝',
        },

      ]
    }
  },
  created() {
    this.tableData.forEach((item, index) => {
      var tmp = parseInt(index)+1
      Object.entries(item).forEach(([key, value]) => {
        item[key] = 'NO.' + tmp + ' ' + value
      });
    })
  },
  methods: {
    getheight(){
      var h = this.tableData.length+1
      return h * 40+12 + "px"
    }

  }
}
</script>

<style scoped></style>