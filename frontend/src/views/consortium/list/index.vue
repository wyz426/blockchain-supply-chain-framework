<template>
  <div style="padding: 5px 20px">
    <br>
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="listQuery.unit_name" style="width: 300px;" clearable :placeholder="$t('Please input the blockchain name')" prefix-icon="el-icon-search" @input="handleClear" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSubmitSelect">{{ $t('Search for the blockchain') }}</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" icon="el-icon-edit" @click="TJCollectionForm">{{ $t('Establish a blockchain') }}</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格-->
    <el-table :data="list" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" :label="$t('Index')" width="60" type="index" />
      <el-table-column :label="$t('BlockchainName')" prop="unit_name" />
      <el-table-column :label="$t('description')" width="300" prop="unit_description" />
      <el-table-column :label="$t('status')" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'danger' : ''" disable-transitions>{{ statusName(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('createTime')">
        <template slot-scope="scope">
          <i class="el-icon-time" />&nbsp;
          <span style="margin-left: 5px" v-text="scope.row.createTime" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('operation')">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleClick(scope.row)">{{ $t('View members') }}</el-button>
          <!--<el-button size="mini" type="text" @click="deleteVisible(scope)">删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <!--    <el-pagination-->
    <!--      :current-page="listQuery.page"-->
    <!--      :page-sizes="[10]"-->
    <!--      :page-size="listQuery.limit"-->
    <!--      layout="total, sizes, prev, pager, next, jumper"-->
    <!--      :background="true"-->
    <!--      @size-change="handleSizeChange"-->
    <!--      @current-change="handleCurrentChange"-->
    <!--    />-->

    <el-dialog :title="$t('Establish a blockchain')" :visible.sync="dialogFormVisible">
      <el-form ref="collectionform" :rules="rules" :model="ruleForm" label-width="140px" label-position="left">
        <el-form-item v-if="dialogStatus == 'create'" :label="$t('BlockchainName')" prop="unit_name">
          <el-input v-model="ruleForm.unit_name" :placeholder="$t('BlockchainName')" />
        </el-form-item>
        <el-form-item v-if="dialogStatus == 'create'" :label="$t('description')" prop="unit_description">
          <el-input v-model="ruleForm.unit_description" :placeholder="$t('description')" />
        </el-form-item>

        <el-form-item
          v-for="(domain, index) in domains"
          :key="domain.key"
          :label="$t('OrgMember') + index"
          :prop="'domains.' + index + '.value'"
          :rules="{
            required: true, message: $t('Cannot be empty'), trigger: 'blur'
          }"
        >
          <el-col :span="9">
            <el-select v-model="domain.corporation" :placeholder="$t('Select corporate user')">
              <el-option v-for="(usr,index2) in listMember" :key="index2" :value="usr.name" />
            </el-select>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-input v-model="domain.name" :placeholder="$t('Unique Identifier Name')" />
          </el-col>
          <el-col :span="6" :offset="1">
            <el-button @click.prevent="removeDomain(domain)">{{ $t('Delete') }}</el-button>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button @click="addDomain">{{ $t('Add new members') }}</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('Cancel') }}</el-button>
        <el-button v-if="dialogStatus == 'create'" type="primary" @click="submitCollectionForm('collectionform')">{{ $t('Add') }}</el-button>
        <el-button v-else type="warning" @click="updateCollectionForm('collectionform')">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import state from "autoprefixer/lib/value";

export default {
  name: "Department",
  filters: {
    // statusName: function(value) {
    //   // eslint-disable-next-line no-unused-vars
    //   // eslint-disable-next-line no-unused-vars
    //   let typeName = null;
    //   switch (value) {
    //     case 0:
    //       typeName = "正在构建";
    //       break;
    //     case 1:
    //       typeName = "构建失败";
    //       break;
    //     case 2:
    //       typeName = "运行中";
    //       break;
    //     case 3:
    //       typeName = "已释放";
    //       break;
    //   }
    //   return typeName;
    // },
  },
  data() {
    return {
      /* typecode:this.$route.query.typecode,
       typetitle:this.$route.query.typetitle */
      listLoading: true,
      domains: [{
        value: '',
        corporation: '',
        name: '',
        description: '',
      }],
      listMember: [
        { name: '',
        },
      ],
      list: [
        {
          unit_account: 1,
          unit_name: "医药联盟",
          unit_description: "描述",
          status: 1,
          unit_members: 7,
          createTime: "2024-02-10",
        },
        {
          unit_account: 2,
          unit_name: "农产品联盟",
          unit_description: "描述2",
          status: 2,
          unit_members: 3,
          unit_enterpriseID: 1,
          createTime: "2024-02-12",
        },
      ],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        unit_name: "",
      },
      dialogFormVisible: false,
      dialogStatus: "create",
      textMap: {
        update: "修改联盟信息",
        create: "创建区块链",
      },

      ruleForm: {
        id: "",
        unit_code: "",
        unit_account: "",
        unit_name: "",
        unit_description: "",
        unit_enterpriseID: "",
        create_time: "",
        create_author: "",
      },

      rules: {
        unit_account: [
          {
            required: true,
            message: "请输入联盟ID",
            trigger: "blur",
          },
        ],
        unit_name: [
          {
            required: true,
            message: "Cannot be empty",
            trigger: "blur",
          },
        ],
        unit_description: [
          {
            required: true,
            message: "Cannot be empty",
            trigger: "blur",
          },
        ],
        unit_enterpriseID: [
          {
            required: true,
            message: "请输入企业ID",
            trigger: "blur",
          },
        ],
      },
    };
  } /* 以下为数据添加过程*/,
  created() {
    this.load();
    this.getUser();
  },
  methods: {
    state,
    statusName(value) {
      // eslint-disable-next-line no-unused-vars
      // eslint-disable-next-line no-unused-vars
      // let typeName = null;
      // switch (value) {
      //   case 0:
      //     typeName = "正在构建";
      //     break;
      //   case 1:
      //     typeName = "构建失败";
      //     break;
      //   case 2:
      //     typeName = "运行中";
      //     break;
      //   case 3:
      //     typeName = "已释放";
      //     break;
      // }
      // return typeName;
      const keys = {
        0: 'Building',
        1: 'Build failed',
        2: 'Running',
        3: 'Released'
      }
      return this.$t(keys[value])
    },
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.load();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.load();
    },
    handleClear() {
      // console.log("look at here" + this.listQuery.unit_name)
      if (this.listQuery.unit_name === '') {
        this.load();
      }
    },
    mapToList(data) {
      return {
        unit_account: data.uid,
        unit_name: data.name,
        unit_description: data.description,
        unit_enterpriseID: data.corpuid,
        createTime: data.createdTime,
        status: data.status
      }
    },
    addDomain() {
      this.domains.push({
        value: '',
        key: Date.now()
      });
    },
    removeDomain(item) {
      var index = this.domains.indexOf(item)
      if (index !== -1) {
        this.domains.splice(index, 1)
      }
    },
    getUser() {
      axios.get('http://localhost:8080/corporation/')
        .then(response => {
          console.log("user", response.data);
          this.listMember = response.data.data;
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          this.listLoading = false; // 隐藏加载状态
        });
    },
    onSubmitSelect() {
      const searchQuery = this.listQuery.unit_name.trim();
      this.list = [];
      axios.get(`http://localhost:8080/blockchain/name/${searchQuery}`)
        .then(response => {
          console.log('Response data select:', response.data.data);
          // 更新联盟列表
          // this.list = response.data.data
          const singleItem = this.mapToList(response.data.data);
          this.list = [singleItem];
          this.listLoading = false; // 隐藏加载状态
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          this.listLoading = false; // 隐藏加载状态
        });
    },
    load() { // 显示所有表单信息
      axios.get('http://localhost:8080/blockchain/')
        .then(response => {
          console.log('Response data:', response.data);
          // 更新联盟列表
          // this.list = response.data.data
          const dataItems = response.data.data;
          this.list = dataItems.map(this.mapToList);
          this.listLoading = false; // 隐藏加载状态
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          this.listLoading = false; // 隐藏加载状态
        });
    },

    /* 添加表单*/
    TJCollectionForm() {
      /* 表单重置*/
      Object.assign(this.$data.ruleForm, this.$options.data().ruleForm);

      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    /* 查看成员跳转*/
    handleClick(row) {
      console.log("跳转");
      // 假设 row 对象中包含了 consortiumId 属性
      this.$router.push({ path: `/consortium/memberCopy/${row.unit_name}` });
    },

    /* 添加按钮*/
    submitCollectionForm() {
      const blockchain = {
        uid: this.ruleForm.unit_account,
        name: this.ruleForm.unit_name,
        description: this.ruleForm.unit_description,
        state: this.ruleForm.state
      }
      console.log(blockchain);
      axios.post("http://localhost:8080/blockchain/", blockchain)
        .then(response => {
          console.log(response);
          this.dialogFormVisible = false; // 关闭对话框
          // this.$refs.collectionform.resetFields(); // 清空表单字段
          this.load(); // 重新获取联盟列表以显示新创建的联盟
          this.$message.success("提交成功");
          setTimeout(function() {
            window.location.reload();
          }, 12000)
        }).catch(error => {
        console.error("Error during Axios POST request:", error);
        this.$message.error("提交失败，请检查网络或稍后再试。");
      });
      console.log("list" + this.domains.length)
      // eslint-disable-next-line no-empty
      if (this.domains.length > 0) {
        for (let i = 0; i < this.domains.length; i++) {
          const blockchainMember = {
            name: this.domains[i].name,
            userName: this.domains[i].corporation,
            blockchainName: this.ruleForm.unit_name,
          }
          console.log(blockchainMember);// todo:url需要改动
          axios.post("http://localhost:8080/blockchain/member/", blockchainMember)
            .then(response => {
              console.log("here2222", response);
              this.dialogFormVisible = false; // 关闭对话框
              this.load(); // 重新获取联盟列表以显示新创建的联盟
              setTimeout(function() {
                window.location.reload();
              }, 120000)
            }).catch(error => {
            console.error("Error during Axios POST request:", error);
            this.$message.error("提交失败，请检查网络或稍后再试。");
          });
        }
      }
    },
    /* 删除按钮*/
    deleteVisible(scope) {
      this.$confirm("确定要删除【" + scope.row.unit_name + "】吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 用户点击了确定，执行删除操作
        this.deleteConsortium(scope.row.unit_name); // 假设联盟的ID是scope.row.id
      })
        .catch(() => {
          // 用户点击了取消或关闭了对话框
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    // 执行删除联盟的操作
    deleteConsortium(name) {
      // 使用 axios 发送 DELETE 请求到后端API
      this.$axios.delete(`http://localhost:8080/consortium/uid/${name}`)
        .then(() => { // 注意这里没有 response 参数
          // 请求成功，执行后续操作
          this.load(); // 重新获取联盟列表以反映删除操作
          // 可以显示成功消息或通过 $notify 发送通知
          this.$message.success('删除联盟成功');
        })
        .catch(error => {
          // 请求失败后的处理
          console.error('删除联盟失败:', error);
          // 可以显示错误消息或通过 $notify 发送通知
          this.$message.error('删除联盟失败，请重试');
        });
    }
  },
};
</script>

<style></style>
