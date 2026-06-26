<script>
import ModulePage from "@/views/ModulePage/ModulePage.vue";
import { getModuleList } from "@/api/Sys_Module";
export default {
  name: "AppDetailPage",
  components: { ModulePage },
  data() {
    return {
      systemId: null, // 系统ID
      moduleList: [], // 系统模块列表
      openeds: ["1", "2"],
      selectedModuleOptions: null, // 添加一个属性用于存储上个页面传递过来的参数
      selectedMenuItem: null, // 存储当前选中的 menu-item
      pageData0: {
        pageTitle: "用户管理",
        code: "user",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          {
            name: "用户",
            code: "user",
            type: "字符串",
            address: "/",
            isNecessary: true,
          },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData1: {
        pageTitle: "产品管理",
        code: "product",
        pageText: [
          "模块名称：产品管理模块",
          "模块编码：product",
          "模块描述：提供产品信息填报、修改、删除等功能",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
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
      },
      pageData2: {
        pageTitle: "采购管理",
        code: "purchase",
        pageText: [
          "模块名称：采购管理模块",
          "模块编码：purchase",
          "模块描述：提供供应商信息管理、采购单管理功能",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "采购订单", code: "order", type: "列表", address: "/" },
          { name: "供应商", code: "suppliers", type: "字符串", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData3: {
        pageTitle: "仓储管理",
        code: "warehouse",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "仓库", code: "repository", type: "字符串", address: "/" },
          { name: "出库单", code: "outbound", type: "列表", address: "/" },
          { name: "入库单", code: "inbound", type: "列表", address: "/" },
          { name: "库存项", code: "inventory", type: "列表", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData4: {
        pageTitle: "销售管理",
        code: "sales",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "客户", code: "consumer", type: "字符串", address: "/" },
          {
            name: "销售订单",
            code: "consume-order",
            type: "列表",
            address: "/",
          },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData5: {
        pageTitle: "物流管理",
        code: "logistics",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "物流公司", code: "logistics", type: "字符串", address: "/" },
          { name: "运输单", code: "transport", type: "列表", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData6: {
        pageTitle: "生产管理",
        code: "production",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "生产工单", code: "produce", type: "列表", address: "/" },
          { name: "车间", code: "workshop", type: "字符串", address: "/" },
          { name: "工序", code: "process", type: "列表", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData7: {
        pageTitle: "加工管理",
        code: "processing",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "质检报告", code: "report", type: "列表", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData8: {
        pageTitle: "质检管理",
        code: "qualityInspection",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "溯源码", code: "code", type: "字符串", address: "/" },
          { name: "溯源信息", code: "info", type: "字符串", address: "/" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData9: {
        pageTitle: "溯源管理",
        code: "trace",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "数据1", code: "001", type: "类型1", address: "地址1" },
          { name: "数据2", code: "002", type: "类型2", address: "地址2" },
          // 可根据需要添加其他表格数据
        ],
      },
      pageData10: {
        pageTitle: "产品召回",
        pageText: [
          "模块名称：",
          "模块编码：",
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        pageText2: [
          "模块描述：",
          // 可根据需要添加其他文字内容
        ],
        tableData: [
          // 根据资料管理页面需要的表格数据进行设计
          { name: "数据1", code: "001", type: "类型1", address: "地址1" },
          { name: "数据2", code: "002", type: "类型2", address: "地址2" },
          // 可根据需要添加其他表格数据
        ],
      },
    };
  },
  async mounted() {
    this.systemId = sessionStorage.getItem("systemId");
    console.log("systemId:", this.systemId);
    this.moduleList = await getModuleList(this.systemId);
    this.selectedMenuItem = "1-0";
    this.selectedModuleOptions = JSON.parse(
      this.$route.query.selectedModuleOptions
    );
    // 从moduleList中获取所有pageData
    console.log(this.moduleList.length);
    for (let i = 0; i < this.moduleList.length; i++) {
      const module = this.moduleList[i];
      console.log(i);
      const pageData = {
        moduleId: module.id,
        pageTitle: module.name,
        code: module.code,
        pageText: [
          "模块名称：" + module.name,
          "模块编码：" + module.code,
          "模块描述：" + module.description,
        ],
      }
      switch (module.type) {
        case 1:
          this.pageData0 = pageData;
          break;
        case 2:
          this.pageData1 = pageData;
          break;
        case 3:
          this.pageData2 = pageData;
          break;
        case 4:
          this.pageData3 = pageData;
          break;
        case 5:
          this.pageData4 = pageData;
          break;
        case 6:
          this.pageData5 = pageData;
          break;
        case 7:
          this.pageData6 = pageData;
          break;
        case 8:
          this.pageData7 = pageData;
          break;
        case 9:
          this.pageData8 = pageData;
          break;
        case 10:
          this.pageData9 = pageData;
          break;
        case 11:
          this.pageData10 = pageData;
          break;
      }
    }
  },
  methods: {
    handleMenuItemClick(index) {
      // 更新当前选中的 menu-item
      this.selectedMenuItem = index;
      console.log("selectedMenuItem:", this.selectedMenuItem);
    },
  },
};
</script>

<template>
  <el-container>
    <el-header class="custom-header">定制系统业务模块</el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="1"
          class="menu-wrapper"
          :default-openeds="openeds"
        >
          <el-submenu index="1">
            <template slot="title">
              <span>核心模块</span>
            </template>
            <el-menu-item
              index="1-0"
              @click="handleMenuItemClick('1-0')"
            >用户管理</el-menu-item>
            <el-menu-item
              index="1-1"
              @click="handleMenuItemClick('1-1')"
            >产品管理</el-menu-item>
            <el-menu-item
              index="1-2"
              @click="handleMenuItemClick('1-2')"
            >采购管理</el-menu-item>
            <el-menu-item
              index="1-3"
              @click="handleMenuItemClick('1-3')"
            >仓储管理</el-menu-item>
            <el-menu-item
              index="1-4"
              @click="handleMenuItemClick('1-4')"
            >销售管理</el-menu-item>
            <el-menu-item
              index="1-5"
              @click="handleMenuItemClick('1-5')"
            >物流管理</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <span>插件模块</span>
            </template>
            <el-menu-item
              v-if="selectedModuleOptions.checked7"
              index="2-1"
              @click="handleMenuItemClick('2-1')"
            >生产管理</el-menu-item>
            <el-menu-item
              v-if="selectedModuleOptions.checked8"
              index="2-2"
              @click="handleMenuItemClick('2-2')"
            >加工管理</el-menu-item>
            <el-menu-item
              v-if="selectedModuleOptions.checked9"
              index="2-3"
              @click="handleMenuItemClick('2-3')"
            >质检管理</el-menu-item>
            <el-menu-item
              v-if="selectedModuleOptions.checked10"
              index="2-4"
              @click="handleMenuItemClick('2-4')"
            >溯源管理</el-menu-item>
            <!-- <el-menu-item
            v-if="selectedModuleOptions.checked10"
            index="2-5"
            @click="handleMenuItemClick('2-5')"
            >产品召回</el-menu-item
          > -->
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <module-page v-if="selectedMenuItem === '1-0'" :page-data="pageData0" />
        <module-page v-if="selectedMenuItem === '1-1'" :page-data="pageData1" />
        <module-page v-if="selectedMenuItem === '1-2'" :page-data="pageData2" />
        <module-page v-if="selectedMenuItem === '1-3'" :page-data="pageData3" />
        <module-page v-if="selectedMenuItem === '1-4'" :page-data="pageData4" />
        <module-page v-if="selectedMenuItem === '1-5'" :page-data="pageData5" />
        <module-page v-if="selectedMenuItem === '2-1'" :page-data="pageData6" />
        <module-page v-if="selectedMenuItem === '2-2'" :page-data="pageData7" />
        <module-page v-if="selectedMenuItem === '2-3'" :page-data="pageData8" />
        <module-page v-if="selectedMenuItem === '2-4'" :page-data="pageData9" />
        <module-page v-if="selectedMenuItem === '2-5'" :page-data="pageData10" />
      </el-main>
    </el-container>
  </el-container>
</template>

<style>
.custom-header {
  background-color: #304156;
  color: #f6f4f4;
  text-align: left;
  line-height: 60px;
  font-size: 20px;
}

.menu-wrapper {
  overflow: hidden;
}
</style>
