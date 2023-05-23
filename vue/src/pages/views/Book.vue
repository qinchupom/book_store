<template>
  <div class="home" style="padding: 10px">
    <!-- 搜索-->
    <div style="margin: 10px 0;">
      <el-form inline="true" size="small">
        <el-form-item v-if="user.role == 0" label="图书编号">
          <el-input v-model="search1" placeholder="请输入图书编号" clearable>
            <template #prefix><el-icon class="el-input__icon">
                <search />
              </el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="search2" placeholder="请输入图书名称" clearable>
            <template #prefix><el-icon class="el-input__icon">
                <search />
              </el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="user.role == 0" label="作者">
          <el-input v-model="search3" placeholder="请输入作者" clearable>
            <template #prefix><el-icon class="el-input__icon">
                <search />
              </el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 1%" @click="load" size="mini">
            <svg-icon iconClass="search" />查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="danger" @click="clear">重置</el-button>
        </el-form-item>
        <el-form-item style="float: right" v-if="numOfOutDataBook != 0">
          <el-popconfirm confirm-button-text="查看" cancel-button-text="取消" :icon="InfoFilled" icon-color="red"
            title="您有图书已逾期，请尽快归还" @confirm="toLook">
            <template #reference>
              <el-button type="warning">逾期通知</el-button>
            </template>
          </el-popconfirm>
        </el-form-item>
      </el-form>
    </div>
    <!-- 按钮-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="add" v-if="user.role == 0">上架</el-button>
      <el-button type="primary" @click="add(user.id)" v-if="user.role == 1">上传书籍</el-button>
      <el-popconfirm title="确认下架?" @confirm="deleteBatch" v-if="user.role == 0">
        <template #reference>
          <el-button type="danger" size="mini">批量下架</el-button>
        </template>
      </el-popconfirm>
    </div>
    <!-- 数据字段-->
    <el-table :data="tableData" height="calc(100vh - 270px)" stripe border="true"
      @selection-change="handleSelectionChange">
      <el-table-column v-if="user.role == 0" type="selection" width="55">
      </el-table-column>
      <el-table-column prop="isbn" label="图书编号" sortable />
      <el-table-column prop="name" label="图书名称" />
      <el-table-column prop="price" label="价格">
        <template v-slot="scope">
          {{ rounding(scope.row.price) }}
        </template>
      </el-table-column>
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" />
      <el-table-column prop="createTime" label="出版时间" sortable />
      <el-table-column prop="borrownum" label="总借阅次数" sortable />
      <el-table-column prop="status" label="状态">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status == 0" type="warning">已借阅</el-tag>
          <el-tag v-else type="success">未借阅</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template v-slot="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" v-if="user.role == 0">修改</el-button>
          <el-popconfirm title="确认下架?" @confirm="handleDelete(scope.row.id)" v-if="user.role == 0">
            <template #reference>
              <el-button type="danger" size="mini">下架</el-button>
            </template>
          </el-popconfirm>
          <el-button size="mini" @click="handlelend(scope.row.id, scope.row.isbn, scope.row.name, scope.row.borrownum)"
            v-if="user.role != 0" :disabled="scope.row.status == 0">借阅</el-button>
          <el-popconfirm title="确认还书?" @confirm="handlereturn(scope.row.id, scope.row.isbn, scope.row.borrownum)"
            v-if="user.role != 0" :disabled="scope.row.status == 1">
            <template #reference>
              <el-button type="danger" size="mini"
                :disabled="(this.isbnArray.indexOf(scope.row.isbn)) == -1 || scope.row.status == 1">还书</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--测试,通知对话框-->
    <el-dialog v-model="dialogVisible3" v-if="numOfOutDataBook != 0" title="逾期详情" width="50%" :before-close="handleClose">
      <el-table :data="outDateBook" style="width: 100%">
        <el-table-column prop="isbn" label="图书编号" />
        <el-table-column prop="bookName" label="书名" />
        <el-table-column prop="lendtime" label="借阅日期" />
        <el-table-column prop="deadtime" label="截至日期" />
      </el-table>

      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="dialogVisible3 = false">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!--    分页-->
    <div style="margin: 10px 0">
      <el-pagination v-model:currentPage="currentPage" :page-sizes="[5, 10, 12]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
      </el-pagination>

      <el-dialog v-model="dialogVisible" title="上架书籍" width="30%">
        <el-form :model="form" label-width="120px">

          <el-form-item label="图书编号">
            <el-input style="width: 80%" v-model="form.isbn"></el-input>
          </el-form-item>
          <el-form-item label="图书名称">
            <el-input style="width: 80%" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input-number style="width: 80%" v-model="form.price" :precision="2" :step="0.1" :max="10000" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input style="width: 80%" v-model="form.author"></el-input>
          </el-form-item>
          <el-form-item label="出版社">
            <el-input style="width: 80%" v-model="form.publisher"></el-input>
          </el-form-item>
          <el-form-item label="出版时间">
            <div>
              <el-date-picker value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable
                v-model="form.createTime"></el-date-picker>
            </div>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog v-model="dialogVisible4" title="上传书籍" width="50%">
        <el-upload class="avatar-uploader" action :show-file-list="false" :on-success="handleAvatarSuccess"
          :http-request="uploadFileImg" :before-upload="beforeUploadImg">
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
        <el-upload v-model:file-list="fileList" class="upload-demo"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :http-request="uploadFile"
          :limit="fileLimit" :on-remove="handleRemove" :file-list="fileList" :on-exceed="handleExceed"
          :before-upload="beforeUpload" :show-file-list="false" :headers="headers">
          <el-button type="primary">上传书籍 </el-button>
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
        <el-form :model="form" label-width="120px">
          <el-form-item label="图书编号">
            <el-input style="width: 60%" v-model="form.isbn"></el-input>
          </el-form-item>
          <el-form-item label="图书名称">
            <el-input style="width: 60%" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input-number style="width: 60%" v-model="form.price" :precision="2" :step="0.1" :max="10000" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input style="width: 60%" v-model="form.author"></el-input>
          </el-form-item>
          <el-form-item label="出版社">
            <el-input style="width: 60%" v-model="form.publisher"></el-input>
          </el-form-item>
          <el-form-item label="出版时间">
            <div>
              <el-date-picker value-format="YYYY-MM-DD" type="date" style="width: 60%" clearable
                v-model="form.createTime"></el-date-picker>
            </div>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog v-model="dialogVisible2" title="修改书籍信息" width="30%">
        <el-form :model="form" label-width="120px">

          <el-form-item label="图书编号">
            <el-input style="width: 80%" v-model="form.isbn"></el-input>
          </el-form-item>
          <el-form-item label="图书名称">
            <el-input style="width: 80%" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input style="width: 80%" v-model="form.price"></el-input>
          </el-form-item>
          <el-form-item label="作者">
            <el-input style="width: 80%" v-model="form.author"></el-input>
          </el-form-item>
          <el-form-item label="出版社">
            <el-input style="width: 80%" v-model="form.publisher"></el-input>
          </el-form-item>
          <el-form-item label="出版时间">
            <div>
              <el-date-picker value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable
                v-model="form.createTime"></el-date-picker>
            </div>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import request from "../../utils/request";
import axios from "axios"
import { ElMessage } from "element-plus";
import moment from "moment";
import router from "@/router";
export default {
  created() {
    let userJson = sessionStorage.getItem("user")
    if (!userJson) {
      router.push("/login")
    }
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    let user = JSON.parse(sessionStorage.getItem("user"))
    this.phone = user.phone
    this.load()
  },
  name: 'Book',
  methods: {
    //取两位小数（价格 格式化）
    rounding(value) {
      return Number(value).toFixed(2)
    },
    //上传文件之前
    beforeUpload(file) {
      if (file.type != "" || file.type != null || file.type != undefined) {
        //截取文件的后缀，判断文件类型
        var FileExt = file.name.replace(/.+\./, "").toLowerCase();
        //计算文件的大小
        var isLt5M = file.size / 1024 / 1024 < this.fileSize; //这里做文件大小限制
        //如果大于文件限制
        if (!isLt5M) {
          ElMessage.warning('上传文件大小不能超过' + this.fileSize + 'MB!');
          return false;
        }
        //如果文件类型不在允许上传的范围内
        if (this.fileType.includes(FileExt)) {
          return true;
        }
        else {
          ElMessage.error("上传文件格式不正确!");
          return false;
        }
      }
    },
    beforeUploadImg(file) {
      if (file.type != "" || file.type != null || file.type != undefined) {
        //截取文件的后缀，判断文件类型
        var FileExt = file.name.replace(/.+\./, "").toLowerCase();
        //计算文件的大小
        var isLt5M = file.size / 1024 / 1024 < 5; //这里做文件大小限制
        var fileType = ['jpg', 'jpeg', 'png']
        //如果大于文件限制
        if (!isLt5M) {
          ElMessage.warning('上传文件大小不能超过' + 5 + 'MB!');
          return false;
        }
        //如果文件类型不在允许上传的范围内
        if (fileType.includes(FileExt)) {
          return true;
        }
        else {
          ElMessage.error("上传文件格式不正确!");
          return false;
        }
      }
    },
    //上传了的文件给移除的事件，由于我没有用到默认的展示，所以没有用到
    handleRemove() {
    },
    //这是我自定义的移除事件
    handleClose(i) {
      this.fileList.splice(i, 1);//删除上传的文件
      if (this.fileList.length == 0) {//如果删完了
        this.fileflag = true;//显示url必填的标识
        this.$set(this.rules.url, 0, { required: true, validator: this.validatorUrl, trigger: 'blur' })//然后动态的添加本地方法的校验规则
      }
    },
    //超出文件个数的回调
    handleExceed() {
      this.$message({
        type: 'warning',
        message: '超出最大上传文件数量的限制！'
      }); return
    },
    //上传文件的事件
    uploadFile() { },
    async uploadFileImg(file) {
      ElMessage.success('文件上传中........')
      //上传文件的需要formdata类型;所以要转
      let FormDatas = new FormData()
      FormDatas.append('file', item.file);
      FormDatas.append('imgType', item.file.type);
      customConfig = this.headers;
      axios.post('http://127.0.0.1:9090/bookstoretest/book/uploadImg', FormDatas, { customConfig })
        .then(res => {
          if (res.data.id != '' || res.data.id != null) {
            this.fileList.push(item.file);//成功过后手动将文件添加到展示列表里
            let i = this.fileList.indexOf(item.file)
            this.fileList[i].id = res.data.id;//id也添加进去，最后整个大表单提交的时候需要的
            if (this.fileList.length > 0) {//如果上传了附件就把校验规则给干掉
              this.fileflag = false;
              this.$set(this.rules.url, 0, '')
            }
            //this.handleSuccess();
          }
        })
    },
    //上传成功后的回调
    handleSuccess() { },
    handleAvatarSuccess() { },
    // (this.isbnArray.indexOf(scope.row.isbn)) == -1
    handleSelectionChange(val) {
      this.ids = val.map(v => v.id)
    },
    deleteBatch() {
      if (!this.ids.length) {
        ElMessage.warning("请选择数据！")
        return
      }
      //  一个小优化，直接发送这个数组，而不是一个一个的提交下架
      request.post("/book/deleteBatch", this.ids).then(res => {
        if (res.code == 200 || res.code == 0) {
          ElMessage.success("批量下架成功")
          this.load()
        }
        else {
          ElMessage.error(res.msg)
        }
      })
    },
    load() {
      this.numOfOutDataBook = 0;
      this.outDateBook = [];
      if (this.user.role == 1) {
        this.search3 = 'author_' + this.user.id
      }
      request.get("/book", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search1: this.search1,
          search2: this.search2,
          search3: this.search3,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      //
      if (this.user.role == 2) {
        request.get("/bookwithuser", {
          params: {
            pageNum: "1",
            pageSize: this.total,
            search1: "",
            search2: "",
            search3: this.user.id,
          }
        }).then(res => {
          this.bookData = res.data.records
          this.number = this.bookData.length;
          var nowDate = new Date();
          for (let i = 0; i < this.number; i++) {
            this.isbnArray[i] = this.bookData[i].isbn;
            let dDate = new Date(this.bookData[i].deadtime);
            if (dDate < nowDate) {
              this.outDateBook[this.numOfOutDataBook] = {
                isbn: this.bookData[i].isbn,
                bookName: this.bookData[i].bookName,
                deadtime: this.bookData[i].deadtime,
                lendtime: this.bookData[i].lendtime,
              };
              this.numOfOutDataBook = this.numOfOutDataBook + 1;
            }
          }
          console.log("in load():" + this.numOfOutDataBook);
        })
      }
      request.get("/user/alow/" + this.user.id).then(res => {
        if (res.code == 200 || res.code == 0) {
          this.flag = true
        }
        else {
          this.flag = false
        }
      })
      //判断是否具有借阅权力
    },
    clear() {
      this.search1 = ""
      this.search2 = ""
      this.search3 = ""
      this.load()
    },

    handleDelete(id) {
      request.delete("book/" + id).then(res => {
        console.log(res)
        if (res.code == 200 || res.code == 0) {
          ElMessage.success("下架成功")
        }
        else
          ElMessage.error(res.msg)
        this.load()
      })
    },
    handlereturn(id, isbn, bn) {
      this.form.status = "1"
      this.form.id = id
      request.put("/book", this.form).then(res => {
        if (res.code == 200 || res.code == 0) {
          ElMessage({
            message: '还书成功',
            type: 'success',
          })
        }
        else {
          ElMessage.error(res.msg)
        }
        //
        this.form3.isbn = isbn
        this.form3.readerId = this.user.id
        let endDate = moment(new Date()).format("yyyy-MM-DD HH:mm:ss")
        this.form3.returnTime = endDate
        this.form3.status = "1"
        console.log(bn)
        this.form3.borrownum = bn
        request.put("/LendRecord1/", this.form3).then(res => {
          console.log(res)
          let form3 = {};
          form3.isbn = isbn;
          form3.bookName = name;
          form3.nickName = this.user.username;
          form3.id = this.user.id;
          form3.lendtime = endDate;
          form3.deadtime = endDate;
          form3.prolong = 1;
          request.post("/bookwithuser/deleteRecord", form3).then(res => {
            console.log(res)
            this.load()
          })

        })
        //
      })
    },
    handlelend(id, isbn, name, bn) {

      if (this.phone == null) {
        ElMessage.error("借阅失败! 请先将个人信息补充完整")
        this.$router.push("/person")//跳转个人信息界面
        return;
      }

      if (this.number == 5) {
        ElMessage.warning("您不能再借阅更多的书籍了")
        return;
      }
      if (this.numOfOutDataBook != 0) {
        ElMessage.warning("在您归还逾期书籍前不能再借阅书籍")
        return;
      }

      if (this.flag == false) {
        ElMessage({
          message: '您没有借阅权限,管理员审核通过后授权',
          type: 'error',
        })
        return;
      }

      this.form.status = 0
      this.form.id = id
      this.form.borrownum = bn + 1
      console.log(bn)
      request.put("/book", this.form).then(res => {
        console.log(res)
        if (res.code == 200 || res.code == 0) {
          ElMessage({
            message: '借阅成功',
            type: 'success',
          })
        }
        else {
          ElMessage.error(res.msg)
        }
      })

      this.form2.status = "0"
      this.form2.isbn = isbn
      this.form2.bookname = name
      this.form2.readerId = this.user.id
      this.form2.borrownum = bn + 1
      console.log(this.form2.borrownum)
      console.log(this.user)
      let startDate = moment(new Date()).format("yyyy-MM-DD HH:mm:ss");
      this.form2.lendTime = startDate
      console.log(this.user)
      request.post("/LendRecord", this.form2).then(res => {
        console.log(res)
        this.load();

      })
      let form3 = {};
      form3.isbn = isbn;
      form3.bookName = name;
      form3.nickName = this.user.username;
      form3.id = this.user.id;
      form3.lendtime = startDate;
      let nowDate = new Date(startDate);
      nowDate.setDate(nowDate.getDate() + 30);
      form3.deadtime = moment(nowDate).format("yyyy-MM-DD HH:mm:ss");
      form3.prolong = 1;
      request.post("/bookwithuser/insertNew", form3).then(res => {
        console.log(res)
        this.load()
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {
        'price': 0.00
      }
    },
    add(id) {
      this.dialogVisible4 = true
      this.form = {
        'isbn': 0,
        'name': 0,
        'price': 0.00,
        'author': 'author_' + id,
        'publisher': 'None',
        'createTime': 0
      }
    },
    save() {
      //ES6语法
      //地址,但是？IP与端口？+请求参数
      // this.form?这是自动保存在form中的，虽然显示时没有使用，但是这个对象中是有它的
      if (this.form.id) {
        request.put("/book", this.form).then(res => {
          console.log(res)
          if (res.code == 200 || res.code == 0) {
            ElMessage({
              message: '修改书籍信息成功',
              type: 'success',
            })
          }
          else {
            ElMessage.error(res.msg)
          }

          this.load()
          this.dialogVisible2 = false
        })
      }
      else {
        this.form.borrownum = 0
        this.form.status = 1
        request.post("/book", this.form).then(res => {
          console.log(res)
          if (res.code == 200 || res.code == 0) {
            ElMessage.success('上架书籍成功')
          }
          else {
            ElMessage.error(res.msg)
          }
          this.load()
          this.dialogVisible = false
        })
      }

    },


    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible2 = true
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    toLook() {
      this.dialogVisible3 = true;
    },
  },
  data() {
    return {
      phone: '',
      flag: '',
      form: {
      },
      form2: {},
      form3: {},
      dialogVisible: false,
      dialogVisible2: false,
      dialogVisible3: true,
      dialogVisible4: false,
      search1: '',
      search2: '',
      search3: '',
      total: 10,
      currentPage: 1,
      pageSize: 10,
      tableData: [],
      user: {},
      number: 0,
      bookData: [],
      isbnArray: [],
      outDateBook: [],
      numOfOutDataBook: 0,
      //上传后的文件列表
      fileList: [],
      // 允许的文件类型
      fileType: ["pdf", "doc", "docx", "xls", "xlsx", "txt", "png", "jpg", "bmp", "jpeg"],
      // 运行上传文件大小，单位 M
      fileSize: 50,
      // 附件数量限制
      fileLimit: 3,
      //请求头
      headers: { "Content-Type": "multipart/form-data" },

    }
  },
}
</script>
