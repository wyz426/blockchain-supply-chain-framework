<template>
  <div class="dashboard-container">
    <el-container v-if="infoCompleted">
      <el-header style="font-size: 24px; height: 30px">
        {{ info.name }}
        <!-- <el-descriptions :title="info.name" column="3">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-document" />
              经营范围
            </template>
            {{ info.des }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user" />
              负责人
            </template>
            {{ info.contactPerson }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone" />
              联系电话
            </template>
            {{ info.contactNumber }}
          </el-descriptions-item>
        </el-descriptions> -->
      </el-header>
      <el-main>
        <template>
          <el-tabs tab-position="top">
            <el-tab-pane :label="$t('Basic Information')">
              <el-descriptions
                class="margin-top"
                :column="3"
                :size="size"
                border
              >
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Corporation Name') }}</template>
                  {{ info.name }}
                </el-descriptions-item>
                <el-descriptions-item :content-style="{ width: '250px' }">
                  <template slot="label"> {{ $t('Business Scope') }}</template>
                  {{ info.des }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Unified Social Credit Code') }}</template>
                  {{ info.creditCode }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Principal') }}</template>
                  {{ info.contactPerson }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('ContactNumber') }}</template>
                  {{ info.contactNumber }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Email') }}</template>
                  {{ info.email }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Address') }}</template>
                  {{ info.address }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> {{ $t('Postcode') }}</template>
                  {{ info.postCode }}
                </el-descriptions-item>

                <el-descriptions-item>
                  <template slot="label"> {{ $t('status') }}</template>
                  <el-tag size="small">{{ info.status | getStatus }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-tab-pane>
            <el-tab-pane :label="$t('Certification')">
              <template>
                <div style="margin-bottom: 15px">
                  <el-input
                    v-model="input2"
                    :placeholder="$t('Please input')"
                    prefix-icon="el-icon-search"
                    style="width: 280px; margin-right: 20px"
                  />
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    @click="onSubmitSelect"
                  >{{ $t('Search') }}</el-button>
                  <el-button
                    type="success"
                    icon="el-icon-edit"
                    @click="dialogVisible = true"
                  >{{ $t('Add') }}</el-button>
                  <el-dialog
                    v-model="dialogVisible"
                    :visible.sync="dialogVisible"
                    :title="$t('Add Certification')"
                    width="500"
                    :before-close="handleClose"
                  >
                    <el-form>
                      <el-form-item :label="$t('CertificationName')" label-width="150px">
                        <el-input
                          v-model="certificateData.name"
                          :placeholder="$t('Please input')"
                        />
                      </el-form-item>

                      <el-form-item :label="$t('CertificationFile')" label-width="150px">
                        <el-upload
                          class="upload-demo"
                          drag
                          action="https://jsonplaceholder.typicode.com/posts/"
                          multiple
                        >
                          <i class="el-icon-upload" />
                          <div class="el-upload__text">
                            Drag the file here, or<em>Click to upload</em>
                          </div>
                          <div slot="tip" class="el-upload__tip">
                            Only pdf, jpg, or png files with a maximum size of 500kb can be uploaded
                          </div>
                        </el-upload>
                      </el-form-item>
                    </el-form>
                    <template #footer>
                      <div class="dialog-footer">
                        <el-button
                          @click="dialogVisible = false"
                        >{{ $t('Cancel') }}</el-button>
                        <el-button
                          type="primary"
                          @click="dialogVisible = false"
                        >{{ $t('Add') }}</el-button>
                      </div>
                    </template>
                  </el-dialog>
                </div>
                <div>
                  <el-table
                    :data="tableData"
                    element-loading-text="Loading"
                    border
                    fit
                    highlight-current-row
                  >
                    <el-table-column type="index" :label="$t('Index')" width="80" />
                    <el-table-column prop="name" :label="$t('CertificationName')" width="280" />
                    <el-table-column prop="picUrl" :label="$t('File')">
                      <template slot-scope="scope">
                        <img
                          :src="scope.row.picUrl"
                          min-width="70"
                          height="70"
                          alt=""
                        >
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      :label="$t('status')"
                      width="140"
                      :filter-method="filterTag"
                      filter-placement="bottom-end"
                    >
                      <template slot-scope="scope">
                        <el-tag
                          :type="scope.row.status === '未审核' ? 'danger' : ''"
                          disable-transitions
                        >
                          {{ scope.row.status }}
                        </el-tag>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </template>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-main>

      <!-- <el-main>
        <el-table :data="items" style="width: 100%" stripe>
          <el-table-column prop="name" label="属性名" width="180" />
          <el-table-column prop="value" label="属性值" width="500">
            <template slot-scope="scope">
              <el-link @click="openBox(scope.row)">
                {{ scope.row.value }}
                <i class="el-icon-edit el-icon--right" />
              </el-link>
            </template>
          </el-table-column>
        </el-table>
      </el-main> -->
    </el-container>
    <div v-else>
      <h2>{{ $('Corporate information is not yet complete') }}</h2>
      <EnterpriseInfoDeclare />
    </div>
  </div>
  <!-- <div style="margin-top: 10px">
  <span>企业信息尚未完善？</span>
  <el-button type="primary" @click="JumpToInfoDeclare">立即填写</el-button>
  </div> -->
  <!-- <div style="margin-top: 10px">
  <h1>登录信息展板</h1>
  <div class="dashboard-text">用户名: {{ name }}</div>
  <div class="dashboard-text">
    用户权限（身份）:
    <span v-for="role in roles" :key="role">{{ role }}</span>
  </div>
  </div> -->
</template>

<script>
import { mapGetters } from "vuex";
import EnterpriseInfoDeclare from "@/views/EnterpriseInfoDeclare/index";

export default {
  name: "Dashboard",
  components: { EnterpriseInfoDeclare },
  data() {
    return {
      listLoading: true,
      infoCompleted: true,
      userInfo: null,
      dialogVisible: false,
      info: {
        name: "上海医药集团",
        des: "原料药和各种剂型的医药产品、保健品、医疗器械及相关产品的研发、制造和销售，以及医药装备制造、销售和工程安装、维修，仓储物流、海上、陆路、航空货运代理业务",
        creditCode: "9131000013358488X7",
        address: "上海市太仓路200号上海医药大厦",
        email: "pharm@sphchina.com",
        postCode: "200020",
        contactPerson: "周俊",
        contactNumber: "+8621-63730908",
        status: 1,
        pic: "https://cn.o-buster.com/wp-content/uploads/2021/03/%E7%87%9F%E6%A5%AD%E5%9F%B7%E7%85%A7.jpg#/",
      },
      tableData: [
        {
          name: "营业执照",
          picUrl:
            "https://cn.o-buster.com/wp-content/uploads/2021/03/%E7%87%9F%E6%A5%AD%E5%9F%B7%E7%85%A7.jpg#/",
          status: "审核通过",
        },
        {
          name: "药品生产许可证",
          picUrl:
            "http://www.leadpharm.cn/wcs/Upload/202105/60a6119fc5e5d.jpg#/",
          status: "审核通过",
        },
        {
          name: "药品经营许可证",
          picUrl:
            "https://lh5.googleusercontent.com/proxy/P2CCmQSKs4UcLOrUQmFY-oP4FzvhvPDU4LZ25N8MpHIvmMInicfQJmRgJ0axHtcRTlsnwrZShiQrrfgq3T5kRzbQ7Z20Z7a-4m2fdYbhg_31QV917Bg#/",
          status: "审核通过",
        },
      ],
      certificateData: {
        user_uuid: sessionStorage.getItem("userId"),
        name: "",
        file: "",
        status: "未审核",
      },
    };
  },
  computed: {
    ...mapGetters(["name", "roles"]),
  },
  mounted() {
    // const userId = sessionStorage.getItem("userId");
    // axios.get(`http://localhost:8080/corporation/uid/${userId}`)
    //   .then(response => {
    //     // 如果获取到了用户企业信息，直接返回
    //     console.log(response.data)
    //     this.infoCompleted = response.data.success
    //     this.info = response.data.data
    //     console.log(response.data.data)
    //     this.info.email = sessionStorage.getItem('email')
    //   })
    //   .catch(error => {
    //     console.log(error)
    //     this.infoCompleted = false
    //   })
    return null;
  },
  updated() {
    // if (!this.infoCompleted) {
    //   this.$alert('请先填写企业信息申报表', '企业信息未完善', {
    //     confirmButtonText: '确定',
    //     callback: action => {
    //     }
    //   })
    // }
  },
  methods: {
    handleClose(done) {
      this.$confirm("Confirm to Close？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    updateItem(value, indexNum) {
      console.log("updateItem");
      // this.items = this.items.map((row) => row !== item ? row : { ...item, value: item.value });
      this.items[indexNum].value = value;
      console.log(this.items);
    },
    JumpToInfoDeclare() {
      console.log("跳转");
      this.$router.push("/EnterpriseInfoDeclare");
    },
    openEditBox(row) {
      this.$prompt("若想要整体编辑，请点击下方“立即填写”", "编辑此项", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /.+/,
        inputErrorMessage: "修改内容为空",
        inputValue: row.value,
        role: "info",
        center: true,
      })
        .then((value) => {
          console.log(value.value);
          console.log(row.id);
          this.updateItem(value.value, row.id);
          this.$message({
            role: "success",
            message: "修改成功!",
          });
        })
        .catch(() => {
          this.$message({
            role: "info",
            message: "已取消修改",
          });
        });
    },
    TJCollectionForm() {},
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 18px;
    line-height: 46px;
  }
}
</style>
