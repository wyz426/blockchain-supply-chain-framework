<template>
  <el-form
    ref="form"
    :model="form"
    label-width="140px"
    label-position="left"
    style="width: 90%"
  >
    <el-form-item label="字段名称：">
      <el-input v-model="form.fieldName" />
    </el-form-item>
    <el-form-item label="字段编码：">
      <el-input v-model="form.fieldCode" />
    </el-form-item>
    <el-form-item label="字段类型：">
      <!-- <el-input v-model="form.type" /> -->
      <el-select v-model="form.fieldType" placeholder="请选择类型">
        <el-option value="text" label="文本">
          <template>
            <i class="el-icon-document"></i> 文本
          </template>
        </el-option>
        <el-option value="num" label="整数">
          <template>
            <i class="el-icon-plus"></i> 整数
          </template>
        </el-option>
        <el-option value="float" label="浮点数">
          <template>
            <i class="el-icon-minus"></i> 浮点数
          </template>
        </el-option>
        <el-option value="date" label="日期">
          <template>
            <i class="el-icon-date"></i> 日期
          </template>
        </el-option>
        <el-option value="gps" label="GPS">
          <template>
            <i class="el-icon-position"></i> GPS
          </template>
        </el-option>
        <el-option value="pic" label="图片">
          <template>
            <i class="el-icon-tickets"></i> 图片
          </template>
        </el-option>
        <el-option value="file" label="文件">
          <template>
            <i class="el-icon-folder"></i> 文件
          </template>
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item class="wrap" :label="'所在的实体/值对象'">
      <el-select
        v-model="form.objectCode"
        style="width: 200px"
        placeholder="请选择实体/值对象"
        @change="handleSelectionChange"
      >
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

    <el-form-item class="wrap" :label="'关联对象：\n(该字段数据来源)'">
      <el-select
        v-model="form.source"
        style="width: 200px"
        placeholder="请选择关联对象"
        @change="handleSelectionChange"
      >
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

    <el-form-item label="字段注释：">
      <el-input v-model="form.fieldRemark" type="textarea" :rows="3" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即添加</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { objectCodeOptions } from "@/assets/data/objectCode";
import { createCustomField } from "@/api/customField";

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['modulecode', 'moduleid'],
  data() {
    return {
      chaincode: "",
      moduleId: this.moduleid,
      moduleCode: this.modulecode,
      options: objectCodeOptions[this.modulecode],
      form: {
        objectCode: "",
        fieldName: "",
        fieldCode: "",
        fieldType: "",
        fieldRemark: "",
      },
      orgs: [
        {
          value: "",
          label: "企业A",
        },
        {
          value: "",
          label: "企业B",
        },
      ],
      chosenValue: [],
    };
  },
  mounted() {
    console.log(this.modulecode);
    console.log(this.moduleId);
  },
  methods: {
    onSubmit() {
      console.log(this.form);
      createCustomField(this.form, this.moduleId)
      this.$message("新建字段成功")
    }
  }
};
</script>

<style>
.wrap .el-form-item__label {
  white-space: pre-line;
  line-height: 30px;
}
</style>
