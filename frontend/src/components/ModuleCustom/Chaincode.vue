<template>
  <div style="display: flex; height: max-content;">
    <div style="width: 45%">
      <h4> 链码部署 </h4>
      <el-form
        ref="form"
        :model="form"
        label-width="90px"
        label-position="left"
        style="width: 100%"
      >
        <el-form-item label="链码名称：">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="链码描述：">
          <el-input v-model="form.desc"/>
        </el-form-item>
        <el-form-item label="所属通道：">
          <el-input v-model="form.channel" disabled/>
        </el-form-item>
        <el-form-item label="链码来源：">
          <el-radio-group v-model="form.resource" size="medium">
            <el-radio border label="standard">平台提供的标准链码</el-radio>
            <el-radio border label="custom">自定义链码</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.resource === 'custom'" label="上传链码压缩包">
          <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
          >
            <i class="el-icon-upload"/>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <!-- <div slot="tip" class="el-upload__tip">
              只能上传zip文件，且不超过500kb
            </div> -->
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            style="width: 445px"
            @click="onSubmit"
          >立即部署链码
          </el-button>
          <!-- <el-button>取消</el-button> -->
        </el-form-item>
      </el-form>
    </div>
    <div style="width: 55%; height: 100%; margin-left: 30px;">
      <h4>链码预览</h4>
      <codemirror
        v-model="code"
        :options="cmOptions"
        style="height: 100%"
      />
    </div>
  </div>
</template>

<script>
import { createChainCode } from "@/api/chainCode";
import { getChannelByModuleId } from "@/api/channel";

export default {
  props: {
    moduleId: String,
  },
  data() {
    return {
      channel: {},
      code: "12345",
      cmOptions: {
        theme: "rubyblue",
        mode: "text/x-java",
        lineNumbers: true,
        styleActiveLine: true,
        smartIndent: true,
        readOnly: true,
      },
      form: {
        name: "Chain Code",
        desc: "采购管理模块的链码",
        channel: "未创建Channel",
        resource: "standard",
        path: "standard_chaincode_path"
      },
      orgs: [
        {
          value: "FactoryOrg",
          label: "企业",
        },
        {
          value: "Shanghai",
          label: "上海",
        },
      ],
      chosenValue: [],
    };
  },
  async mounted() {
    await this.loadCode();
    this.channel = await getChannelByModuleId(this.moduleId)
    if (this.channel) { this.form.channel = this.channel.name }
  },
  watch: {
    async moduleId() {
      await this.loadCode();
      this.channel = await getChannelByModuleId(this.moduleId)
      if (this.channel) {
        this.form.channel = this.channel.name
      } else {
        this.form.channel = "未创建Channel"
      }
    }
  },
  methods: {
    async onSubmit() {
      console.log("form", this.form);
      const channelId = sessionStorage.getItem("channelId");
      const chainCode = {
        name: this.form.name,
        description: this.form.desc,
        version: "1.0",
        status: 1,
        path: this.form.path,
      }
      await createChainCode(chainCode, channelId)
      this.$message({
        message: "链码部署成功",
        type: "success",
      })
      this.$emit("refresh")
    },
    async loadCode() {
      console.log("load code");
      try {
        const response = await require("@/assets/exampleCode.java");
        this.code = response.default;
        this.$forceUpdate();
      } catch (error) {
        console.error('Failed to load example file', error);
        this.code = "";
        this.$message.error("加载标准链码预览失败，请检查网络连接或联系管理员");
      }
    }
  }
};
</script>

<style></style>
