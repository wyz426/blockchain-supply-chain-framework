<template>
  <div>
    <el-row type="flex" align="middle" class="row-bg">
      <el-col :span="10" style="margin-left: 20px">
        <span
          style="font-size: 18px"
        >您可以从这里开始创建基于区块链的供应链系统!</span>
      </el-col>
      <el-col :span="4" style="margin-left: 150px">
        <div>
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="selectModule = true"
          >创建系统
          </el-button>
        </div>
      </el-col>
    </el-row>

    <el-row type="flex" class="body-bg">
      <el-col :span="4" style="margin-left: 20px">
        <el-input
          v-model="input"
          placeholder="请输入内容"
          prefix-icon="el-icon-search"
          style="width: 400px"
        />
      </el-col>

      <el-col :span="3" offset="8">
        <el-select v-model="createValue" placeholder="请选择">
          <el-option
            v-for="item in Creators"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- <easy-select :initial-options="Creators" /> -->
      </el-col>
      <el-col :span="3" offset="1">
        <el-select v-model="dateValue" placeholder="请选择">
          <el-option
            v-for="item in Dates"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- <easy-select :initial-options="Dates" /> -->
      </el-col>
      <el-col :span="3" offset="1">
        <el-select v-model="statusValue" placeholder="请选择">
          <el-option
            v-for="item in Status"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- <easy-select :initial-options="Status" /> -->
      </el-col>
    </el-row>
    <!-- 系统列表 -->

    <el-table :data="systemData" style="width: 95%; margin-left: 20px">
      <el-table-column prop="name" label="系统名称" width="230" class="sys-name">
        <template slot-scope="scope">
          <span class="sys-name">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="系统图标" width="140">
        <template slot-scope="scope">
          <i
            v-if="scope.row.icon === null"
            class="el-icon-s-grid"
          />
          <i
            v-else
            :class="`el-icon-${scope.row.icon}`"
          />

        </template>
      </el-table-column>
      <el-table-column prop="description" label="系统描述" width="230" />
      <el-table-column prop="blockchain_id" label="依赖的区块链名称" width="200">
        <template slot-scope="scope">
          <el-link type="primary">{{ scope.row.blockchainid }}</el-link>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="系统状态" width="200">
        <template slot-scope="scope">
          <el-tag size="small" type="success">{{ parseSystemStatus(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            type="text"
            @click="jumpToSystem(scope.row)"
          >访问
          </el-button>
          <el-button
            type="text"
            @click="handleJumpClick(scope.row)"
          >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="selectModule"
      title="选择供应链业务模块"
      style="margin-top: 5px"
    >
      <select-module
        :prop-varible="selectModule"
        @nextStep="ModuleSelected"
        @cancel="selectModule = false"
      />
    </el-dialog>
    <el-dialog :visible.sync="createAppDialog" title="填写系统信息">
      <create-app-detail
        :prop-varible="createAppDialog"
        @closeDialog="createAppDialog = false"
        @toAppDetail="toAppDetail"
      />
    </el-dialog>
  </div>
</template>

<script>
import CreateAppDetail from "@/views/CreateApp/CreateAppDetail.vue";
import SelectModule from "../../components/CheckBox/CheckBox.vue";
import { createModuleList, createSystem, getModuleList, getSystemList, parseSystemStatus } from "@/api/Sys_Module";

export default {
  components: { CreateAppDetail, SelectModule },
  data() {
    return {
      systemData: [
        {
          name: "基于区块链的药品供应链系统",
          description: "用于管理药品流通",
          blc: "pharmChain",
          status: "已启用",
          icon: "",
        },
      ],
      createValue: "1",
      dateValue: "1",
      statusValue: "1",
      Creators: [
        { value: "1", label: "全部应用" },
        { value: "2", label: "我创建的" },
      ],
      Dates: [
        { value: "1", label: "按创建时间排序" },
        { value: "2", label: "按更新时间排序" },
      ],
      Status: [
        { value: "1", label: "全部状态" },
        { value: "2", label: "已启用" },
        { value: "3", label: "未启用" },
      ],
      createAppDialog: false,
      selectModule: false,
      selectedModuleOptions: null,
    };
  },
  mounted() {
    getSystemList().then((res) => {
      this.systemData = res
    })
    console.log("systemData:", this.systemData)
  },
  methods: {
    parseSystemStatus,
    async jumpToSystem(system) {
      console.log("jump to system:", system)
      const systemId = system.id
      window.location.href = `http://localhost:8081/#/?systemId=${systemId}`
    },
    async handleJumpClick(system) {
      const ModuleList = await getModuleList(system.id)
      console.log("ModuleList:", ModuleList)
      const moduleBools = {
        checked1: false,
        checked2: false,
        checked3: false,
        checked4: false,
        checked5: false,
        checked6: false,
        checked7: false,
        checked8: false,
        checked9: false,
        checked10: false,
      }
      for (let i = 0; i < ModuleList.length; i++) {
        switch (ModuleList[i].type) {
          case 1:
            moduleBools.checked1 = true
            break
          case 2:
            moduleBools.checked2 = true
            break
          case 3:
            moduleBools.checked3 = true
            break
          case 4:
            moduleBools.checked4 = true
            break
          case 5:
            moduleBools.checked5 = true
            break
          case 6:
            moduleBools.checked6 = true
            break
          case 7:
            moduleBools.checked7 = true
            break
          case 8:
            moduleBools.checked8 = true
            break
          case 9:
            moduleBools.checked9 = true
            break
          case 10:
            moduleBools.checked10 = true
            break
          default:
            break
        }
      }
      console.log("moduleBools:", moduleBools)
      // 使用路由参数跳转到/AppDetail，并将selectedModuleOptions作为参数传递
      sessionStorage.setItem("systemId", system.id)
      this.$router.push({
        path: "/AppDetail",
        query: {
          selectedModuleOptions: JSON.stringify(moduleBools),
        },
      });
    },
    ModuleSelected(selectedOptions) {
      console.log("module selected.");
      this.createAppDialog = true;
      this.selectModule = false;
      this.selectedModuleOptions = selectedOptions;
    },
    async toAppDetail(system) {
      this.createAppDialog = false;
      // 将数据传输到后端，创建系统和模块列表
      const newSystem = {
        name: system.name,
        description: system.description,
        blockchainid: system.blockchain,
        status: 1,
        // icon
        icon: system.icon
      }
      const new_system = await createSystem(newSystem)
      console.log("system:", system)
      const systemId = new_system.data.id
      console.log("systemId:", systemId)
      await createModuleList(this.selectedModuleOptions, systemId)
      // 使用路由参数跳转到/AppDetail，并将selectedModuleOptions作为参数传递
      sessionStorage.setItem("systemId", systemId)
      this.$router.push({
        path: "/AppDetail",
        query: {
          selectedModuleOptions: JSON.stringify(this.selectedModuleOptions),
        },
      });
    },
  },
};
</script>

<style>
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.body-bg {
  padding: 10px 0;
  background-color: white;
}

.sys-name {
  font-size: 15px;
  font-weight: bold
}
</style>
