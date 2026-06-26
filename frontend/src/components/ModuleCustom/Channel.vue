<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="160px"
      label-position="left"
      style="width: 50%"
    >
      <el-form-item label="通道名称：">
        <el-input v-model="form.name" :disabled="!canCreate" />
      </el-form-item>
      <el-form-item label="通道描述：">
        <el-input v-model="form.desc" :disabled="!canCreate" />
      </el-form-item>
      <el-form-item label="所属区块链网络：">
        <el-input v-model="form.blockchain" disabled />
      </el-form-item>
      <el-form-item label="加入通道的组织成员：">
        <el-select
          v-model="form.orgs"
          multiple
          :disabled="!canCreate"
          placeholder="请选择加入通道的组织成员"
          style="width: 300px"
        >
          <el-option
            v-for="(item, index) in options"
            :key="index"
            :label="item.label+ '  ' + item.value"
            :value="item.value"
          >
            <span style="float: left">{{ item.label }}  {{ item.value }}</span>
            <!-- <span style="float: right; color: #8492a6; font-size: 13px">{{
              item.value
            }}</span> -->
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item v-if="canCreate">
        <el-button
          type="primary"
          style="width: 445px"
          @click="onSubmit"
        >立即创建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getCorporationBySystemId, getCorporationsByModuleId, getSystemById } from "@/api/Sys_Module";
import { createChannel, getChannelByModuleId } from "@/api/channel";
import { getBlockchainById } from "@/api/blockchain";

export default {
  props: {
    moduleId: String
  },
  data() {
    return {
      canCreate: true,
      form: {
        name: "Channel",
        desc: "模块对应的通道",
        blockchain: "pharmChain",
        orgs: [],
      },
      corporations: [],
      corporationMap: {},
      options: {},

      orgs: [
        {
          value: "supplierOrg",
          label: "上海医药集团",
        },
        {
          value: "manufactureOrg",
          label: "上海新华联制药有限公司",
        },
        {
          label: "药品包装厂",
          value: "processOrg",
        },
        {
          label: "药品区域分销中心",
          value: "distributorOrg",
        },
        {
          label: "药品监督管理局",
          value: "regulatoryOrg",
        },
      ],
      chosenValue: [],
    };
  },
  async mounted() {
    const SystemId = sessionStorage.getItem("systemId")
    const curSys = await getSystemById(SystemId);
    const blockchain_id = curSys.blockchainid;
    const curBlockchain = await getBlockchainById(blockchain_id);
    this.form.blockchain = curBlockchain.name;
    // this.corporations = await getCorporationsByModuleId(this.moduleId)
    this.corporationMap = await getCorporationBySystemId(SystemId);
    const corporations = this.corporationMap.corporations
    const identifiers = this.corporationMap.identifiers
    this.options = corporations.map((corp, index) => ({
          label: corp.name,
          value: identifiers[index]
        }));

    const channel = await getChannelByModuleId(this.moduleId)
    console.log(channel)
    if (channel) {
      this.form.name = channel.name
      this.form.desc = channel.desc
      this.form.orgs = JSON.parse(channel.orgs)
      this.canCreate = false
      console.log(this.form)
    }
    console.log(this.form)
  },
  watch: {
    async moduleId() {
      this.form = {
        name: "Channel",
        desc: "模块对应的通道",
        blockchain: "pharmChain",
        orgs: [],
      }
      this.canCreate = true
      const channel = await getChannelByModuleId(this.moduleId)
      console.log(channel)
      if (channel) {
        this.form.name = channel.name
        this.form.desc = channel.description
        this.form.orgs = JSON.parse(channel.orgs)
        this.canCreate = false
        console.log(this.form)
      }
      console.log(this.form)
    },
  },
  methods: {
    async onSubmit() {
      const SystemId = sessionStorage.getItem("systemId")
      const curSys = await getSystemById(SystemId);
      const blockchain_id = curSys.blockchain_id;
      const channel = {
        name: this.form.name,
        description: this.form.desc,
        orgs: JSON.stringify(this.form.orgs),
        status: 0,
        blockchain_id: blockchain_id,
        user_uuid: sessionStorage.getItem("userId"),
      }
      const newChannel = await createChannel(channel, this.moduleId)
      sessionStorage.setItem("channelId", newChannel.id)
      console.log("submit!");
      this.$emit("refresh");
      this.$message.success("创建通道成功！");
    },
  },
};
</script>

<style></style>
