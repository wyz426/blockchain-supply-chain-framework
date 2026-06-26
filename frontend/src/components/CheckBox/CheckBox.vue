<template>
  <div>
    <el-divider content-position="center">核心模块</el-divider>
    <el-row>
      <el-col
        v-for="(o, index) in coreModule1"
        :key="o"
        :span="7"
        :offset="index > 0 ? 1 : 0"
      >
        <el-popover
          title="模块描述"
          placement="right"
          width="300"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <!-- <span>"功能"</span>
                <div v-for="v in 4" :key="v" class="text item">
                  {{ "列表内容 " + o }}
                </div> -->
            <span class="des">{{ o.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox
                v-model="checked3"
                :label="o.name"
                disabled
                style="color: #606266"
              />
            </div>
          </el-card>
        </el-popover>
      </el-col>
      <el-col
        v-for="(o, index) in coreModule2"
        :key="o"
        :span="7"
        :offset="index > 0 ? 1 : 0"
        style="padding-top: 10px"
      >
        <el-popover
          title="模块描述"
          placement="right"
          width="300"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <span class="des">{{ o.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox
                v-model="checked3"
                :label="o.name"
                disabled
                style="color: #606266"
              />
            </div>
          </el-card>
        </el-popover>
      </el-col>
    </el-row>

    <el-divider content-position="center">插件模块</el-divider>
    <el-row>
      <el-col :span="7" :offset="0" style="padding-top: 10px">
        <el-popover
          title="模块描述"
          placement="right"
          width="400"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <span class="des">{{ pluginModule1.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox v-model="checked7" :label="pluginModule1.name" />
            </div>
          </el-card>
        </el-popover>
      </el-col>

      <el-col :span="7" :offset="1" style="padding-top: 10px">
        <el-popover
          title="模块描述"
          placement="right"
          width="400"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <span class="des">{{ pluginModule2.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox v-model="checked8" :label="pluginModule2.name" />
            </div>
          </el-card>
        </el-popover>
      </el-col>

      <el-col :span="7" :offset="1" style="padding-top: 10px">
        <el-popover
          title="模块描述"
          placement="right"
          width="400"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <span class="des">{{ pluginModule3.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox v-model="checked9" :label="pluginModule3.name" />
            </div>
          </el-card>
        </el-popover>
      </el-col>

      <el-col :span="7" :offset="0" style="padding-top: 10px">
        <el-popover
          title="模块描述"
          placement="right"
          width="400"
          trigger="hover"
        >
          <div class="bottom clearfix">
            <span class="des">{{ pluginModule4.des }}</span>
          </div>
          <el-card
            slot="reference"
            :body-style="{ padding: '0px' }"
            shadow="never"
          >
            <div style="padding: 14px">
              <el-checkbox v-model="checked10" :label="pluginModule4.name" />
            </div>
          </el-card>
        </el-popover>
      </el-col>
    </el-row>
    <div style="margin-top: 30px">
      <el-button type="primary" @click="onNext">下一步</el-button>
      <el-button @click="onCancel">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: ["dialogVisible"],
  data() {
    return {
      coreModule1: [
        {
          id: 1,
          name: "用户管理",
          des: "提供系统用户登录、注册及权限控制功能",
        },

        { id: 2, name: "信息管理", des: "提供产品信息填报、修改、删除等功能" },

        { id: 3, name: "采购管理", des: "提供供应商信息管理、采购单管理功能" },
      ],
      coreModule2: [
        {
          id: 4,
          name: "仓储管理",
          des: "提供仓库信息管理、填报入库单、填报出库单、查看库存等功能",
        },

        { id: 5, name: "销售管理", des: "提供客户信息管理、销售订单管理功能" },

        {
          id: 6,
          name: "物流管理",
          des: "提供物流公司信息管理、发货信息管理功能",
        },
      ],

      pluginModule1: { id: 7, name: "生产管理", des: "提供有关生产工单、车间、工序、物料、产品的信息管理功能" },
      pluginModule2: { id: 8, name: "加工管理", des: "提供有关加工工单、工厂、工序、物料、产品的信息管理功能" },
      pluginModule3: { id: 9, name: "质检管理", des: "提供上传质检报告、查看检查结果和审核结果功能" },
      pluginModule4: { id: 10, name: "溯源管理", des: "提供溯源码、溯源信息、环节信息等信息管理功能" },
      checked1: true,
      checked2: true,
      checked3: true,
      checked4: true,
      checked5: true,
      checked6: true,
      checked7: false,
      checked8: false,
      checked9: false,
      checked10: false,
    };
  },
  methods: {
    onNext() {
      const selectedOptions = {
        checked1: this.checked1,
        checked2: this.checked2,
        checked3: this.checked3,
        checked4: this.checked4,
        checked5: this.checked5,
        checked6: this.checked6,
        checked7: this.checked7,
        checked8: this.checked8,
        checked9: this.checked9,
        checked10: this.checked10,
      };
      console.log("submit!");
      this.$emit("nextStep", selectedOptions);
    },
    onCancel() {
      console.log("cancel.");
      this.$emit("cancel");
    },
  },
};
</script>

<style>
.des {
  color: #909399;
  line-height: 1.5;
}

.el-dialog__body {
  padding-top: 0px;
}
.el-checkbox__input.is-disabled + span.el-checkbox__label {
  color: #303133;
  cursor: not-allowed;
}
.el-checkbox__label {
  color: #303133;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
