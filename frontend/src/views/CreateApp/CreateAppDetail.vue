<script>
import ImageUploader from "../../components/ImgUpLoader/ImageUploader.vue";
import { getAllBlockchains } from "@/api/blockchain";

// 创建应用时的对话框内容

export default {
  components: { ImageUploader },
  data() {
    return {
      props: ["dialogVisible"],
      form: {
        name: "",
        icon: "",
        description: "",
        blockchain: "",
        selectOptions: [], // 添加一个变量来存储复选框的选择项
      },
      orgs: [
        {
          value: "1",
          label: "pharmChain",
        },
      ],
      blockchains: [],
    };
  },
  async mounted() {
    this.blockchains = await getAllBlockchains();
    console.log(this.blockchains);
  },
  methods: {
    onSubmit() {
      console.log("submit!");
      console.log(this.form.icon);
      this.$emit("toAppDetail", this.form);
    },
    onCancel() {
      console.log("cancel.");
      this.$emit("closeDialog");
    },
  },
};
</script>

<template>
  <el-form ref="form" :model="form" label-width="100px">
    <el-form-item label="名称">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item label="图标">
      <!-- <ImageUploader /> -->
      <!-- <el-button icon="el-icon-s-grid" />-->
      <!-- <el-button icon="el-icon-edit" size="medium" type="text" />-->
      <el-select v-model="form.icon" placeholder="请选择系统的图标" style="width: 280px">
        <el-option value="s-grid">
          <span>
            <i class="el-icon-s-grid"/>
          </span>
        </el-option>
        <el-option value="s-tools">
          <span>
            <i class="el-icon-s-tools"/>
          </span>
        </el-option>
        <el-option value="edit">
          <span>
            <i class="el-icon-edit"/>
          </span>
        </el-option>
        <el-option value="share">
          <span>
            <i class="el-icon-share"/>
          </span>
        </el-option>
        <el-option value="user">
          <span>
            <i class="el-icon-user"/>
          </span>
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="描述">
      <el-input
        v-model="form.description"
        type="textarea"
        :rows="3"
        placeholder="请输入系统的描述"
      />
    </el-form-item>
    <el-form-item label="区块链网络">
      <el-select
        v-model="form.blockchain"
        style="width: 280px"
        placeholder="请选择系统所依赖的区块链网络"
      >
        <el-option
          v-for="item in blockchains"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button @click="onCancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped></style>
