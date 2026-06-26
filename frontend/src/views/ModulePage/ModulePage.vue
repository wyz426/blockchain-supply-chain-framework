<template>
  <div>
    <!-- 第一行的页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="page-title">{{ pageData.pageTitle }}</h1>
        <!-- <div class="title-divider" /> -->
      </el-col>
    </el-row>

    <!-- 第二行的一些文字 -->
    <el-row class="page-text-row">
      <el-col :span="24">
        <div class="text-container">
          <span
            v-for="(text, index) in pageData.pageText"
            :key="index"
            style="color: #606266"
            class="text-item"
          >{{ text }}</span>
        </div>
      </el-col>
    </el-row>

    <!-- <el-row class="page-text-row">
      <el-col :span="24">
        <div class="text-container">
          <span
            v-for="(text, index) in pageData.pageText2"
            :key="index"
            style="color: #606266"
            class="text-item"
            >{{ text }}</span
          >
        </div>
      </el-col>
    </el-row> -->

    <el-tabs :value="currentTab" @tab-click="handleTabClick">
      <el-tab-pane label="定制字段" name="option1" />
      <el-tab-pane label="配置通道" name="option2" />
      <el-tab-pane label="配置链码" name="option3" />
    </el-tabs>

    <div v-if="currentTab === 'option1'">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form
            ref="form"
            :model="form"
            label-width="140px"
            label-position="left"
            style="width: 90%"
          >
            <el-form-item label="请选择实体/值对象">
              <el-popover
                placement="top-start"
                title="什么是实体/值对象？"
                width="500"
                trigger="hover"
                style="margin-right: 10px"
              >
                <div class="custom-content">
                  实体：具有唯一标识的对象，具有延续性<br>
                  例如：身份信息，因为具有唯一标识（身份证号）即使职业、年龄变化也还是同个人。<br>
                  值对象：没有唯一标识，代表的是真实世界中哪些一成不变、本质性的事物。可以理解为属性的集合。<br>
                  例如：产品信息，产品名称、规格、单位等属性都可以作为值对象，本身不具备类似Id的唯一标识。
                </div>
                <el-button slot="reference" type="text"><i class="el-icon-question" /></el-button>
              </el-popover>
              <el-select v-model="objectCode" placeholder="请选择">
                <el-option-group
                  v-for="group in options"
                  :key="group.label"
                  :label="group.label"
                >
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.label + ' (' + item.value + ')'"
                    :value="item.value"
                  />
                </el-option-group>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6" :offset="6">
          <el-button
            type="primary"
            style="margin-left: 16px"
            @click="drawer = true"
          >
            添加自定义字段
          </el-button>
        </el-col>
      </el-row>

      <el-drawer
        title="添加自定义字段"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
      >
        <div class="add-field">
          <AddField :modulecode="pageData.code" :moduleid="pageData.moduleId" />
        </div>
      </el-drawer>
    </div>

    <div v-if="currentTab === 'option2'">
      <Channel :module-id="pageData.moduleId" />
    </div>
    <div v-if="currentTab === 'option3'">
      <Chaincode :module-id="pageData.moduleId" />
    </div>

    <!-- 第三行的表格 -->
    <!-- :data="pageData.tableData" -->

    <el-table
      v-if="currentTab === 'option1'"
      :data="fieldList"
      style="width: 100%"
    >
      <el-table-column label="字段名称" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.fieldName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字段编码" width="160">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <span>{{ scope.row.fieldCode }}</span>
              <!-- <el-tag size="medium">{{ scope.row.code }}</el-tag> -->
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="字段类型" width="120">
        <template slot-scope="scope">
          <span>{{ parseFieldType(scope.row.fieldType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联对象（数据来源）">
        <template slot-scope="scope">
          <span>{{ scope.row.source }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字段注释">
        <template slot-scope="scope">
          <span>{{ scope.row.fieldRemark }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <span v-if="!scope.row.fieldCode.endsWith('Code')"> </span>
          <el-button
            v-if="!scope.row.fieldCode.endsWith('Code')"
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="!scope.row.fieldCode.endsWith('Code')"
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--编辑弹窗-->
    <el-dialog
      title="编辑字段"
      :visible.sync="showEditDialog"
    >
      <el-form
        ref="editForm"
        :model="editField"
        label-width="140px"
        label-position="left"
        style="width: 50%"
      >
        <el-form-item label="字段名称">
          <el-input v-model="editField.fieldName" />
        </el-form-item>
        <el-form-item label="字段编码">
          <el-input v-model="editField.fieldCode" />
        </el-form-item>
        <el-form-item label="字段类型">
          <el-select v-model="editField.fieldType">
            <el-option label="文本" value="text" />
            <el-option label="整数" value="integer" />
            <el-option label="浮点数" value="float" />
            <el-option label="日期" value="date" />
            <el-option label="GPS" value="gps" />
            <el-option label="图片" value="pic" />
            <el-option label="文件" value="file" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联对象（数据来源）">
          <el-input v-model="editField.source" />
        </el-form-item>
        <el-form-item label="字段注释">
          <el-input v-model="editField.fieldRemark" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitEditForm">
            保存
          </el-button>
          <el-button @click="showEditDialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Channel from "../../components/ModuleCustom/Channel";
import Chaincode from "../../components/ModuleCustom/Chaincode";
import AddField from "../../components/ModuleCustom/AddField";
import { deleteField, editField, getCustomFields, parseFieldType } from "@/api/customField";
import { objectCodeOptions } from "@/assets/data/objectCode";

export default {
  components: { Channel, Chaincode, AddField },
  props: {
    pageData: {
      code: String,
      pageTitle: String,
      pageText: Array,
      pageText2: Array,
      pageData: Array,
    },
  },
  data() {
    return {
      showEditDialog: false,
      editField: {},
      fieldList: [],
      jsonData: null,
      objectCode: "",
      currentTab: "option1",
      drawer: false,
      direction: "rtl",
      options1: [
        {
          label: "实体",
          options: [
            {
              value: "productInfo",
              label: "产品信息",
            },
          ],
        },
        {
          label: "值对象",
          options: [
            {
              value: "productType",
              label: "产品类型",
            },
          ],
        },
      ],
      options: [
        {
          label: "实体",
          options: [
            {
              value: "supplier",
              label: "供应商信息",
            },
            {
              value: "purchaseOrder",
              label: "采购订单",
            },
          ],
        },
        {
          label: "值对象",
          options: [
            {
              value: "orderItem",
              label: "订单项",
            },
            {
              value: "orderStatus",
              label: "订单状态",
            }
          ],
        },
      ],
      supplier: [
        // 根据资料管理页面需要的表格数据进行设计
        {
          name: "产品信息编号",
          code: "productCode",
          type: "文本",
          address: "必要字段，产品信息的唯一标识",
          associate: "-",
          isNecessary: true,
        },
        {
          name: "产品名称",
          code: "name",
          type: "文本",
          address: "-",
          associate: "-",

          isNecessary: false,
        },
        {
          name: "产品规格",
          code: "specification",
          type: "文本",
          address: "-",
          associate: "-",

          isNecessary: false,
        },
        {
          name: "产品类型",
          code: "type",
          type: "文本",
          address: "-",
          associate: "产品类型 (productType)",
          isNecessary: false,
        },
        {
          name: "产品单位",
          code: "unit",
          type: "文本",
          address: "-",
          associate: "-",
          isNecessary: false,
        },
        // 可根据需要添加其他表格数据
      ],
      purchaseOrder: [
        // 根据资料管理页面需要的表格数据进行设计
        {
          name: "采购订单编号",
          code: "purchaseOrderCode",
          type: "文本",
          address: "必要字段，采购订单的唯一标识",
          associate: "-",
          isNecessary: true,
        },
        {
          name: "供应商",
          code: "supplier",
          type: "文本",
          address: "-",
          associate: "供应商 (supplier)",
          isNecessary: true,
        },
        {
          name: "采购负责人",
          code: "responsiblePerson",
          type: "文本",
          address: "-",
          associate: "-",

          isNecessary: false,
        },
        {
          name: "采购明细",
          code: "orderItem",
          type: "列表",
          address: "-",
          associate: "订单项 (orderItem)",
          isNecessary: false,
        },
        {
          name: "采购总金额",
          code: "totalAmout",
          type: "浮点数",
          address: "-",
          associate: "-",
          isNecessary: false,
        },
        {
          name: "采购日期",
          code: "purchaseDate",
          type: "时间",
          address: "-",
          associate: "-",
          isNecessary: false,
        },
        {
          name: "需到货日期",
          code: "deliveryDate",
          type: "时间",
          address: "-",
          associate: "-",
          isNecessary: false,
        },
        // 可根据需要添加其他表格数据
      ],
    };
  },
  watch: {
    objectCode() {
      getCustomFields(this.pageData.moduleId, this.objectCode).then(res => {
        this.fieldList = res;
      })
    },
    pageData() {
      this.objectCode = ""; // 清空选择的对象类型
      getCustomFields(this.pageData.moduleId, this.objectCode).then(res => {
        this.fieldList = res;
      })
      this.options = objectCodeOptions[this.pageData.code]; // 根据页面code获取对象类型选项
    }
  },

  mounted() {
    console.log("pageData:", this.pageData);
    this.options = objectCodeOptions[this.pageData.code]; // 根据页面code获取对象类型选项
    this.currentTab = "option1"
  },
  methods: {
    parseFieldType,
    handleTabClick(tab) {
      this.currentTab = tab.name; // 更新当前选中的选项
      this.objectCode = ""; // 清空选择的对象类型
      getCustomFields(this.pageData.moduleId, this.objectCode).then(res => {
        this.fieldList = res;
      })
    },
    handleClose(done) {
      getCustomFields(this.pageData.moduleId, this.objectCode).then(res => {
        this.fieldList = res;
      })
      done();
      // this.$confirm("确认关闭？")
      //   .then((_) => {
      //     done();
      //   })
      //   .catch((_) => {});
    },
    handleEdit(index, row) {
      console.log("handleEdit:", index, row);
      this.editField = row;
      this.showEditDialog = true;
    },
    async submitEditForm() {
      console.log("submitEditForm:", this.editField);
      await editField(this.editField);
      this.showEditDialog = false;
      this.fieldList = await getCustomFields(this.pageData.moduleId, this.objectCode);
      this.editField = {};
      this.$message.success("保存成功");
    },
    handleDelete(index, row) {
      console.log("handleDelete:", index, row);
      this.$confirm("确认删除该字段？")
       .then(async(_) => {
         await deleteField(row.id)
          this.fieldList = await getCustomFields(this.pageData.moduleId, this.objectCode);
          this.$message.success("删除成功");
       })
       .catch((_) => {});
    }
  },
};
</script>

<style>
.add-field {
  padding: 20px 20px 0;
}

/* 样式可以根据需要进行自定义 */
.page-title {
  font-size: 24px;
  margin-bottom: 40px;
  margin-top: 10px;
}

.page-text-row {
  margin-bottom: 20px;
}

.text-container {
  display: flex;
}

.title-divider {
  border-bottom: 1px solid #ccc; /* 添加底部边框作为分隔线 */
  margin-bottom: 20px; /* 设置分隔线与下方元素的间距 */
}

.text-item {
  margin-right: 100px;
}
</style>
