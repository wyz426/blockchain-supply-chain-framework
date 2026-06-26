<template>
  <div style="padding: 5px 20px">
    <br>
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item>
        <p class="blockchain-name">{{ $t('BlockchainName') }}: {{ blockchain }}</p>
      </el-form-item>
      <!--      <el-form-item>-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          icon="el-icon-search"-->
      <!--          @click="onSubmitSelect"-->
      <!--        >查询组织</el-button>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button
          type="success"
          icon="el-icon-edit"
          @click="TJCollectionForm"
        >{{ $t('Add new members') }}</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格-->
    <el-table
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" :label="$t('Index')" width="60" type="index" />
      <el-table-column :label="$t('Corporate user')" prop="unit_enterprise" />
      <el-table-column :label="$t('Unique Identifier Name')" prop="unit_name" />
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
          <el-button
            size="mini"
            type="text"
            @click="deleteVisible(scope)"
          >{{ $t('Delete') }}</el-button>
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

    <el-dialog :title="$t('Add new members')" :visible.sync="dialogFormVisible">
      <el-form
        ref="collectionform"
        :rules="rules"
        :model="ruleForm"
        label-width="180px"
        label-position="left"
      >
        <el-form-item :label="$t('Select corporate user')">
          <el-select v-model="members" :placeholder="$t('Select corporate user')">
            <el-option v-for="(usr,index) in listMember" :key="index" :value="usr.name" />
          </el-select>
        </el-form-item>
        <el-col span="13">
          <el-form-item
            v-if="dialogStatus == 'create'"
            :label="$t('Unique Identifier Name')"
            prop="unit_name"
          >
            <el-input v-model="ruleForm.unit_name" :placeholder="$t('Unique Identifier Name')" />
          </el-form-item></el-col>
      </el-form><div slot="footer" class="dialog-footer"><el-button @click="dialogFormVisible = false">{{ $t('Cancel') }}</el-button>
      <el-button
        v-if="dialogStatus == 'create'"
        type="primary"
        @click="submitCollectionForm('collectionform')"
      >{{ $t('Add') }}</el-button>
      <el-button
        v-else
        type="warning"
        @click="updateCollectionForm('collectionform')"
      >修 改</el-button>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "Department",
  data() {
    return {
      /* typecode:this.$route.query.typecode,
       typetitle:this.$route.query.typetitle */
      listLoading: true,
      listMember: [
        { name: '',
        },
      ],
      listBlockchain: [
        {
          name: '',
        }
      ],
      list: [
        {
          unit_name: 123,
          unit_enterprise: 89,
          unit_consortium: 1,
          createTime: "2000-12-02",
          unit_role: "原材料提供商",
          unit_power: 0,
          status: 1
        },
      ],
      total: 0,
      members: '',
      blockchain: '',
      blockchainId: '',
      listQuery: {
        page: 1,
        limit: 10,
        unit_name: "",
      },
      dialogFormVisible: false,
      dialogStatus: "create",
      textMap: {
        update: "修改成员信息",
        create: "邀请组织成员",
      },

      ruleForm: {
        id: "",
        unit_name: "",
        unit_enterprise: "",
        unit_consortium: "",
        unit_role: "",
        unit_power: "",
        create_time: "",
        create_author: "",
      },

      rules: {
        unit_name: [
          {
            required: true,
            message: "请输入成员名称",
            trigger: "blur",
          },
        ],
        unit_description: [
          {
            required: true,
            message: "请输入组织描述",
            trigger: "blur",
          },
        ],
        unit_consortium: [
          {
            required: true,
            message: "请输入所属联盟ID",
            trigger: "blur",
          },
        ],
        unit_role: [
          {
            required: true,
            message: "请输入角色",
            trigger: "blur",
          },
        ],
        unit_power: [
          {
            required: true,
            message: "请输入权限",
            trigger: "blur",
          },
        ],
      },
    };
  } /* 以下为数据添加过程*/,
  created() {
    this.reload();
    this.getUser();
    this.getBlockchain();
  },
  methods: {
    statusName(value) {
      const keys = {
        0: 'Joined successfully',
        1: 'Join failed',
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
    mapToList(data) {
      return {
        unit_name: data.name,
        unit_enterprise: data.userName,
        unit_description: data.description, // 待定 具体根据后端再来修改
        status: data.status,
        createTime: data.createdTime,
        id: data.id,
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
    getBlockchain() {
      axios.get('http://localhost:8080/blockchain/')
        .then(response => {
          // console.log('Response data:', response.data);
          // 更新联盟列表
          // this.list = response.data.data
          this.listBlockchain = response.data.data;
          console.log("here", this.listBlockchain)
          this.listLoading = false; // 隐藏加载状态
        })
        .catch(error => {
          console.error('Error fetching data:', error);
          this.listLoading = false; // 隐藏加载状态
        });
    },
    reload() { // 需要根据此时的区块链来返回
      if (this.blockchain !== "") {
        const blockchainName = this.blockchain;
        axios.get(`http://localhost:8080/blockchain/member/select/${blockchainName}`)
          .then(response => {
            console.log('Response data2222:', response.data);
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
      } else {
        this.load();
      }
    },
    load() { // 显示所有表单信息 todo:url也需要改动
      const temp = this.$route.params.unit_name;
      this.blockchain = temp
      console.log("block" + this.blockchain);
      axios.get(`http://localhost:8080/blockchain/member/select/${temp}`)
        .then(response => {
          console.log('Response data2222:', response.data);
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
    /* 添加按钮*/
    submitCollectionForm() {
      if (this.blockchain !== "") {
        console.log("21431432", this.blockchain, this.members)
        const member = {
          name: this.ruleForm.unit_name,
          description: this.ruleForm.unit_description,
          blockchainName: this.blockchain,
          userName: this.members,
        }
        console.log(member);// todo:url需要改动
        axios.post("http://localhost:8080/blockchain/member/", member)
          .then(response => {
            console.log("here2222", response);
            this.dialogFormVisible = false; // 关闭对话框
            this.$refs.collectionform.resetFields(); // 清空表单字段
            this.reload(); // 重新获取联盟列表以显示新创建的联盟
            this.$message.success("提交成功");
            setTimeout(function() {
              window.location.reload();
            }, 120000)
          }).catch(error => {
          console.error("Error during Axios POST request:", error);
          this.$message.error("提交失败，请检查网络或稍后再试。");
        });
      } else {
        this.$message.error("提交失败，请选择区块链。");
      }
    },
    /* 删除按钮*/
    deleteVisible(scope) {
      this.$confirm("确定要删除成员【" + scope.row.unit_name + "】吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 用户点击了确定，执行删除操作
        this.deleteConsortium(scope.row.id); // 假设联盟的ID是scope.row.id
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
    deleteConsortium(id) {
      // 使用 axios 发送 DELETE 请求到后端API todo:url需要改动
      console.log("delete" + id)
      axios.delete(`http://localhost:8080/blockchain/member/${id}`)
        .then(() => { // 注意这里没有 response 参数
          // 请求成功，执行后续操作
          this.reload(); // 重新获取联盟列表以反映删除操作
          // 可以显示成功消息或通过 $notify 发送通知
          this.$message.success('删除成功');
        })
        .catch(error => {
          // 请求失败后的处理
          console.error('删除失败:', error);
          // 可以显示错误消息或通过 $notify 发送通知
          this.$message.error('删除失败，请重试');
        });
    }
  },
};
</script>

<style>
.blockchain-name{
  font-size: 20px;
  color: #333;
  font-weight: bold;
  /* 添加更多样式，如文本对齐、文本修饰等 */
  text-align: center;
  margin-top: -3px; /* 负值将元素向上移动 */
}
</style>
