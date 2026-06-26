//
// objectCode: "",
// fieldName: "",
// fieldCode: "",
// fieldType: "",
// fieldRemark: "",

//  switch (fieldType) {
//     case "text":
//       return "文本";
//     case "num":
//       return "整数";
//     case "float":
//       return "浮点数";
//     case "date":
//       return "日期";
//     case "gps":
//       return "GPS";
//     case "pic":
//       return "图片";
//     case "file":
//       return "文件";
//   }


export const defaultFields = {
  "user" : [ // 人员编码、姓名、性别、身份证号码、联系方式、职务、部门、入职时间
    {
      objectCode: "userInfo",
      fieldName: "人员编码",
      fieldCode: "userCode",
      fieldType: "text",
      fieldRemark: "人员实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "userInfo",
      fieldName: "姓名",
      fieldCode: "userName",
      fieldType: "text",
      fieldRemark: "人员的真实姓名"
    },
    {
      objectCode: "userInfo",
      fieldName: "性别",
      fieldCode: "userGender",
      fieldType: "text",
      fieldRemark: "人员的性别"
    },
    {
      objectCode: "userInfo",
      fieldName: "身份证号码",
      fieldCode: "userIdCard",
      fieldType: "text",
      fieldRemark: "人员的身份证号码"
    },
    {
      objectCode: "userInfo",
      fieldName: "联系方式",
      fieldCode: "userPhone",
      fieldType: "text",
      fieldRemark: "人员的联系方式"
    },
    {
      objectCode: "userInfo",
      fieldName: "职务",
      fieldCode: "userDuty",
      fieldType: "text",
      fieldRemark: "人员的职务"
    },
    {
      objectCode: "userInfo",
      fieldName: "部门",
      fieldCode: "userDepartment",
      fieldType: "text",
      fieldRemark: "人员所在的部门"
    },
    {
      objectCode: "userInfo",
      fieldName: "入职时间",
      fieldCode: "userHireDate",
      fieldType: "date",
      fieldRemark: "人员的入职时间"
    }
  ],
  "product" : [
    // productInfo: 产品编码、产品名称、产品型号、产品价格、产品库存
    {
      objectCode: "productInfo",
      fieldName: "产品编码",
      fieldCode: "productCode",
      fieldType: "text",
      fieldRemark: "产品实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "productInfo",
      fieldName: "产品名称",
      fieldCode: "productName",
      fieldType: "text",
      fieldRemark: "产品的名称"
    },
    {
      objectCode: "productInfo",
      fieldName: "产品型号",
      fieldCode: "productModel",
      fieldType: "text",
      fieldRemark: "产品的型号"
    },
    {
      objectCode: "productInfo",
      fieldName: "产品价格",
      fieldCode: "productPrice",
      fieldType: "float",
      fieldRemark: "产品的价格"
    },
    {
      objectCode: "productInfo",
      fieldName: "产品库存",
      fieldCode: "productStock",
      fieldType: "num",
      fieldRemark: "产品的库存"
    },
    // productCategory: 产品类别名称
    {
      objectCode: "productCategory",
      fieldName: "产品类别名称",
      fieldCode: "productCategoryName",
      fieldType: "text",
      fieldRemark: "产品的类别名称"
    }
  ],
  "purchase" : [
    // supplier: 供应商编码、供应商名称、供应商地址、供应商联系方式
    {
      objectCode: "supplier",
      fieldName: "供应商编码",
      fieldCode: "supplierCode",
      fieldType: "text",
      fieldRemark: "供应商实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "supplier",
      fieldName: "供应商名称",
      fieldCode: "supplierName",
      fieldType: "text",
      fieldRemark: "供应商的名称"
    },
    {
      objectCode: "supplier",
      fieldName: "供应商地址",
      fieldCode: "supplierAddress",
      fieldType: "text",
      fieldRemark: "供应商的地址"
    },
    {
      objectCode: "supplier",
      fieldName: "供应商联系方式",
      fieldCode: "supplierPhone",
      fieldType: "text",
      fieldRemark: "供应商的联系方式"
    },
    // purchaseOrder: 采购订单编码、采购订单日期、采购订单金额
    {
      objectCode: "purchaseOrder",
      fieldName: "采购订单编码",
      fieldCode: "purchaseOrderCode",
      fieldType: "text",
      fieldRemark: "采购订单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "purchaseOrder",
      fieldName: "采购订单日期",
      fieldCode: "purchaseOrderDate",
      fieldType: "date",
      fieldRemark: "采购订单的日期"
    },
    {
      objectCode: "purchaseOrder",
      fieldName: "采购订单金额",
    },
    //purchaseOrderItem: 采购订单项名称、采购订单项数量、采购订单项单价、采购订单项金额
    {
      objectCode: "purchaseOrderItem",
      fieldName: "采购订单项名称",
      fieldCode: "purchaseOrderItemName",
      fieldType: "text",
      fieldRemark: "采购订单项的名称"
    },
    {
      objectCode: "purchaseOrderItem",
      fieldName: "采购订单项数量",
      fieldCode: "purchaseOrderItemQuantity",
      fieldType: "num",
      fieldRemark: "采购订单项的数量"
    },
    {
      objectCode: "purchaseOrderItem",
      fieldName: "采购订单项单价",
      fieldCode: "purchaseOrderItemPrice",
      fieldType: "float",
      fieldRemark: "采购订单项的单价"
    },
    {
      objectCode: "purchaseOrderItem",
      fieldName: "采购订单项金额",
      fieldCode: "purchaseOrderItemAmount",
      fieldType: "float",
      fieldRemark: "采购订单项的金额"
    },
    {
      objectCode: "purchaseOrderStatus",
      fieldName: "采购订单状态",
      fieldCode: "purchaseOrderStatusName",
      fieldType: "text",
      fieldRemark: "采购订单的状态"
    }
  ],
  "sales":[
    // customer: 客户编码、客户名称、客户地址、客户联系方式
    {
      objectCode: "customer",
      fieldName: "客户编码",
      fieldCode: "customerCode",
      fieldType: "text",
      fieldRemark: "客户实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "customer",
      fieldName: "客户名称",
      fieldCode: "customerName",
      fieldType: "text",
      fieldRemark: "客户的名称"
    },
    {
      objectCode: "customer",
      fieldName: "客户地址",
      fieldCode: "customerAddress",
      fieldType: "text",
      fieldRemark: "客户的地址"
    },
    {
      objectCode: "customer",
      fieldName: "客户联系方式",
      fieldCode: "customerPhone",
      fieldType: "text",
      fieldRemark: "客户的联系方式"
    },
    // saleOrder: 销售订单编码、销售订单日期、销售订单金额
    {
      objectCode: "saleOrder",
      fieldName: "销售订单编码",
      fieldCode: "salesOrderCode",
      fieldType: "text",
      fieldRemark: "销售订单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "saleOrder",
      fieldName: "销售订单日期",
      fieldCode: "salesOrderDate",
      fieldType: "date",
      fieldRemark: "销售订单的日期"
    },
    {
      objectCode: "saleOrder",
      fieldName: "销售订单金额",
      fieldCode: "salesOrderAmount",
      fieldType: "float",
      fieldRemark: "销售订单的金额"
    },
    // saleOrderItem: 销售订单项名称、销售订单项数量、销售订单项单价、销售订单项金额
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项名称",
      fieldCode: "salesOrderItemName",
      fieldType: "text",
      fieldRemark: "销售订单项的名称"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项数量",
      fieldCode: "salesOrderItemQuantity",
      fieldType: "num",
      fieldRemark: "销售订单项的数量"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项单价",
      fieldCode: "salesOrderItemPrice",
      fieldType: "float",
      fieldRemark: "销售订单项的单价"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项金额",
      fieldCode: "salesOrderItemAmount",
      fieldType: "float",
      fieldRemark: "销售订单项的金额"
    },
    {
      objectCode: "saleOrderStatus",
      fieldName: "销售订单状态",
      fieldCode: "salesOrderStatusName",
      fieldType: "text",
    },
    // saleOrderItem: 销售订单项名称、销售订单项数量、销售订单项单价、销售订单项金额
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项名称",
      fieldCode: "salesOrderItemName",
      fieldType: "text",
      fieldRemark: "销售订单项的名称"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项数量",
      fieldCode: "salesOrderItemQuantity",
      fieldType: "num",
      fieldRemark: "销售订单项的数量"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项单价",
      fieldCode: "salesOrderItemPrice",
      fieldType: "float",
      fieldRemark: "销售订单项的单价"
    },
    {
      objectCode: "saleOrderItem",
      fieldName: "销售订单项金额",
      fieldCode: "salesOrderItemAmount",
      fieldType: "float",
      fieldRemark: "销售订单项的金额"
    },
  ],
  "warehouse" : [
    // repository: 仓库编码、仓库名称、仓库地址
    {
      objectCode: "repository",
      fieldName: "仓库编码",
      fieldCode: "repositoryCode",
      fieldType: "text",
      fieldRemark: "仓库实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "repository",
      fieldName: "仓库名称",
      fieldCode: "repositoryName",
      fieldType: "text",
      fieldRemark: "仓库的名称"
    },
    {
      objectCode: "repository",
      fieldName: "仓库地址",
      fieldCode: "repositoryAddress",
      fieldType: "text",
      fieldRemark: "仓库的地址"
    },
    // outbound: 出库单编码、出库单日期、出库单金额
    {
      objectCode: "outbound",
      fieldName: "出库单编码",
      fieldCode: "outboundCode",
      fieldType: "text",
      fieldRemark: "出库单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "outbound",
      fieldName: "出库单日期",
      fieldCode: "outboundDate",
      fieldType: "date",
      fieldRemark: "出库单的日期"
    },
    {
      objectCode: "outbound",
      fieldName: "出库单金额",
      fieldCode: "outboundAmount",
      fieldType: "float",
      fieldRemark: "出库单的金额"
    },
    // inbound: 入库单编码、入库单日期、入库单金额
    {
      objectCode: "inbound",
      fieldName: "入库单编码",
      fieldCode: "inboundCode",
      fieldType: "text",
      fieldRemark: "入库单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "inbound",
      fieldName: "入库单日期",
      fieldCode: "inboundDate",
      fieldType: "date",
      fieldRemark: "入库单的日期"
    },
    {
      objectCode: "inbound",
      fieldName: "入库单金额",
      fieldCode: "inboundAmount",
      fieldType: "float",
      fieldRemark: "入库单的金额"
    },
    // inventory: 库存项编码、库存数量、库存单价、库存金额
    {
      objectCode: "inventory",
      fieldName: "库存项编码",
      fieldCode: "inventoryCode",
      fieldType: "text",
      fieldRemark: "库存项实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "inventory",
      fieldName: "库存数量",
      fieldCode: "inventoryQuantity",
      fieldType: "num",
      fieldRemark: "库存的数量"
    },
    {
      objectCode: "inventory",
      fieldName: "库存单价",
      fieldCode: "inventoryPrice",
      fieldType: "float",
      fieldRemark: "库存的单价"
    },
    {
      objectCode: "inventory",
      fieldName: "库存金额",
      fieldCode: "inventoryAmount",
      fieldType: "float",
      fieldRemark: "库存的金额"
    },
    // goodsInfo: 商品名称
    {
      objectCode: "goodsInfo",
      fieldName: "商品名称",
      fieldCode: "goodsName",
      fieldType: "text",
      fieldRemark: "商品的名称"
    }
  ],
  "logistics" : [
    // transportation: 运单编码、运单日期、运单金额
    {
      objectCode: "transportation",
      fieldName: "运单编码",
      fieldCode: "transportationCode",
      fieldType: "text",
      fieldRemark: "运单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "transportation",
      fieldName: "运单日期",
      fieldCode: "transportationDate",
      fieldType: "date",
      fieldRemark: "运单的日期"
    },
    {
      objectCode: "transportation",
      fieldName: "运单金额",
      fieldCode: "transportationAmount",
      fieldType: "float",
      fieldRemark: "运单的金额"
    },
    // logisticsInfo: 物流公司编码、物流公司名称
    {
      objectCode: "logisticsInfo",
      fieldName: "物流公司编码",
      fieldCode: "logisticsInfoCode",
      fieldType: "text",
      fieldRemark: "物流公司实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "logisticsInfo",
      fieldName: "物流公司名称",
      fieldCode: "logisticsName",
      fieldType: "text",
      fieldRemark: "物流公司的名称"
    },
    // transportationAddress: 运输地址
    {
      objectCode: "transportationAddress",
      fieldName: "运输地址",
      fieldCode: "transportationAddress",
      fieldType: "text",
      fieldRemark: "运输地址"
    },
    // goodsDetail: 货物明细
    {
      objectCode: "goodsDetail",
      fieldName: "货物明细",
      fieldCode: "goodsDetail",
      fieldType: "text",
      fieldRemark: "货物明细"
    }
  ],
  "production": [
    // produce: 生产工单编码、生产工单日期
    {
      objectCode: "produce",
      fieldName: "生产工单编码",
      fieldCode: "produceCode",
      fieldType: "text",
      fieldRemark: "生产工单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "produce",
      fieldName: "生产工单日期",
      fieldCode: "produceDate",
      fieldType: "date",
      fieldRemark: "生产工单的日期"
    },
    // workshop: 车间编码、车间名称
    {
      objectCode: "workshop",
      fieldName: "车间编码",
      fieldCode: "workshopCode",
      fieldType: "text",
      fieldRemark: "车间实体的唯一标识"
    },
    {
      objectCode: "workshop",
      fieldName: "车间名称",
      fieldCode: "workshopName",
      fieldType: "text",
      fieldRemark: "车间的名称"
    },
    // productionProcedure: 生产工序编码、生产工序名称、 生产工序顺序
    {
      objectCode: "productionProcedure",
      fieldName: "生产工序编码",
      fieldCode: "productionProcedureCode",
      fieldType: "text",
      fieldRemark: "生产工序实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "productionProcedure",
      fieldName: "生产工序名称",
      fieldCode: "productionProcedureName",
      fieldType: "text",
      fieldRemark: "生产工序的名称"
    },
    {
      objectCode: "productionProcedure",
      fieldName: "生产工序顺序",
      fieldCode: "productionProcedureOrder",
      fieldType: "num",
      fieldRemark: "生产工序的顺序"
    },
    // productionMaterialRequirement: 生产物料名称、生产物料数量
    {
      objectCode: "productionMaterialRequirement",
      fieldName: "生产物料名称",
      fieldCode: "productionMaterialName",
      fieldType: "text",
      fieldRemark: "生产物料的名称"
    },
    {
      objectCode: "productionMaterialRequirement",
      fieldName: "生产物料数量",
      fieldCode: "productionMaterialQuantity",
      fieldType: "num",
      fieldRemark: "生产物料的数量"
    },

    // productionProductInfo: 生产产品名称、生产产品型号、生产产品价格
    {
      objectCode: "productionProductInfo",
      fieldName: "生产产品名称",
      fieldCode: "productionProductName",
      fieldType: "text",
      fieldRemark: "生产产品的名称"
    }
  ],
  "processing": [
    // processingOrder: 加工工单编码、加工工单日期
    {
      objectCode: "processingOrder",
      fieldName: "加工工单编码",
      fieldCode: "processingCode",
      fieldType: "text",
      fieldRemark: "加工工单实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "processingOrder",
      fieldName: "加工工单日期",
      fieldCode: "processingDate",
      fieldType: "date",
      fieldRemark: "加工工单的日期"
    },
    // processingProcedure: 加工工序编码、加工工序名称、 加工工序顺序
    {
      objectCode: "processingProcedure",
      fieldName: "加工工序编码",
      fieldCode: "processingProcedureCode",
      fieldType: "text",
      fieldRemark: "加工工序实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "processingProcedure",
      fieldName: "加工工序名称",
      fieldCode: "processingProcedureName",
      fieldType: "text",
      fieldRemark: "加工工序的名称"
    },
    {
      objectCode: "processingProcedure",
      fieldName: "加工工序顺序",
      fieldCode: "processingProcedureOrder",
      fieldType: "num",
      fieldRemark: "加工工序的顺序"
    },
    // factory: 工厂编码、工厂名称、工厂地址
    {
      objectCode: "factory",
      fieldName: "工厂编码",
      fieldCode: "factoryCode",
      fieldType: "text",
      fieldRemark: "工厂实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "factory",
      fieldName: "工厂名称",
      fieldCode: "factoryName",
      fieldType: "text",
      fieldRemark: "工厂的名称"
    },
    {
      objectCode: "factory",
      fieldName: "工厂地址",
      fieldCode: "factoryAddress",
      fieldType: "text",
      fieldRemark: "工厂的地址"
    },
    // processingMaterialRequirement: 加工物料名称、加工物料数量
    {
      objectCode: "processingMaterialRequirement",
      fieldName: "加工物料名称",
      fieldCode: "processingMaterialName",
      fieldType: "text",
      fieldRemark: "加工物料的名称"
    },
    {
      objectCode: "processingMaterialRequirement",
      fieldName: "加工物料数量",
      fieldCode: "processingMaterialQuantity",
      fieldType: "num",
      fieldRemark: "加工物料的数量"
    },
    // processingProductInfo: 加工产品名称、加工产品型号、加工产品价格
    {
      objectCode: "processingProductInfo",
      fieldName: "加工产品名称",
      fieldCode: "processingProductName",
      fieldType: "text",
      fieldRemark: "加工产品的名称"
    },
    {
      objectCode: "processingProductInfo",
      fieldName: "加工产品型号",
      fieldCode: "processingProductModel",
      fieldType: "text",
      fieldRemark: "加工产品的型号"
    },
    {
      objectCode: "processingProductInfo",
      fieldName: "加工产品价格",
      fieldCode: "processingProductPrice",
      fieldType: "float",
      fieldRemark: "加工产品的价格"
    }
  ],
// 质检报告
// 实体
//   - 质检报告: qualityReport
// 值对象
//   - 检查结果: inspectionResult
//   - 审核结果: reviewResult
  "qualityInspection": [
    // qualityReport: 质检报告编码、质检报告日期、质检报告结果
    {
      objectCode: "qualityReport",
      fieldName: "质检报告编码",
      fieldCode: "qualityReportCode",
      fieldType: "text",
      fieldRemark: "质检报告实体的唯一标识",
      isNecessary: true
    },
    {
      objectCode: "qualityReport",
      fieldName: "质检报告日期",
      fieldCode: "qualityReportDate",
      fieldType: "date",
      fieldRemark: "质检报告的日期"
    },
    {
      objectCode: "qualityReport",
      fieldName: "质检报告结果",
      fieldCode: "qualityReportResult",
      fieldType: "text",
      fieldRemark: "质检报告的结果"
    },
    // inspectionResult: 检查结果
    {
      objectCode: "inspectionResult",
      fieldName: "检查结果",
      fieldCode: "inspectionResult",
      fieldType: "text",
      fieldRemark: "检查结果"
    },
    // reviewResult: 审核结果
    {
      objectCode: "reviewResult",
      fieldName: "审核结果",
      fieldCode: "reviewResult",
      fieldType: "text",
      fieldRemark: "审核结果"
    }
  ],
// 溯源码
// 实体
//   - 溯源码: traceCode
//   - 溯源信息: traceInfo
// 值对象
//   - 环节信息: stepInfo
  "trace": [
    // traceCode: 溯源码编码
    {
      objectCode: "traceCode",
      fieldName: "溯源码编码",
      fieldCode: "traceCode",
      fieldType: "text",
      fieldRemark: "溯源码实体的唯一标识",
      isNecessary: true
    },
    // traceInfo: 溯源信息
    {
      objectCode: "traceInfo",
      fieldName: "溯源信息",
      fieldCode: "traceInfo",
      fieldType: "text",
      fieldRemark: "溯源信息"
    },
    // stepInfo: 环节信息
    {
      objectCode: "stepInfo",
      fieldName: "环节信息",
      fieldCode: "stepInfo",
      fieldType: "text",
      fieldRemark: "环节信息"
    }
  ]
}
