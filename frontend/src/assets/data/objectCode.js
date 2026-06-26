export const objectCodeOptions = {
  "purchase": [
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
          value: "purchaseOrderItem",
          label: "采购订单项",
        },
        {
          value: "purchaseOrderStatus",
          label: "采购订单状态",
        }
      ],
    },
  ],
  "user": [
    {
      label: "实体",
      options: [
        {
          value: "userInfo",
          label: "用户",
        }
      ],
    },
    {
      label: "值对象",
      options: [],
    },
  ],
  "product": [
    {
      label: "实体",
      options: [
        {
          value: "productInfo",
          label: "产品信息",
        }
      ],
    },
    {
      label: "值对象",
      options: [
        {
          value: "productCategory",
          label: "产品类型",
        }
      ],
    }
  ],
  "warehouse": [
    {
      label: "实体",
      options: [
        {
          value: "repository",
          label: "仓库",
        },
        {
          value: "outbound",
          label: "出库单",
        },
        {
          value: "inbound",
          label: "入库单",
        },
        {
          value: "inventory",
          label: "库存项",
        }
      ]
    },
    {
      label: "值对象",
      options: [
        {
          value: "goodsInfo",
          label: "货物信息",
        }
      ]
    }
  ],
  "sales": [
    {
      label: "实体",
      options: [
        {
          value: "customer",
          label: "客户",
        },
        {
          value: "saleOrder",
          label: "销售订单",
        }
      ]
    },
    {
      label: "值对象",
      options: [
        {
          value: "saleOrderItem",
          label: "销售订单项",
        },
        {
          value: "saleOrderStatus",
          label: "销售订单状态",
        }
      ]
    }
  ],
  "logistics": [
    {
      label: "实体",
      options: [
        {
          value: "transportation",
          label: "发货单",
        },
        {
          value: "logisticsInfo",
          label: "物流公司信息",
        }
      ]
    },
    {
      label: "值对象",
      options: [
        {
          value: "transportationAddress",
          label: "运输地址",
        },
        {
          value: "goodsDetail",
          label: "货物明细",
        }
      ]
    }
  ],
  // 以下是插件域的实体代码选项

  "production": [
    {
      label: "实体",
      options: [
        {
          value: "produce",
          label: "生产工单",
        },
        {
          value: "workshop",
          label: "车间",
        },
        {
          value: "productionProcedure",
          label: "生产工序",
        }
      ]
    },
    {
      label: "值对象",
      options: [ // 物料需求，产品信息
        {
          value: "productionMaterialRequirement",
          label: "生产物料需求",
        },
        {
          value: "productionProductInfo",
          label: "生产产品信息",
        }
      ]
    }
  ],
  "processing": [
    {
      label: "实体",
      options: [ // 加工工单、工厂、工序
        {
          value: "processingOrder",
          label: "加工工单",
        },
        {
          value: "factory",
          label: "工厂",
        },
        {
          value: "processingProcedure",
          label: "加工工序",
        }
      ]
    },
    {
      label: "值对象",
      options: [ // 物料需求，产品信息
        {
          value: "processingMaterialRequirement",
          label: "加工物料需求",
        },
        {
          value: "processingProductInfo",
          label: "加工产品信息",
        }]
    }
  ],
  "qualityInspection": [
    {
      label: "实体",
      options: [ // 质检报告
        {
          value: "qualityReport",
          label: "质检报告",
        }
      ]
    },
    {
      label: "值对象",
      options: [ // 检查结果，审核结果
        {
          value: "inspectionResult",
          label: "检查结果",
        },
        {
          value: "reviewResult",
          label: "审核结果",
        }
      ]
    }
  ],
  "trace": [
    {
      label: "实体",
      options: [ // 溯源码、溯源信息
        {
          value: "traceCode",
          label: "溯源码",
        },
        {
          value: "traceInfo",
          label: "溯源信息",
        }
      ]
    },
    {
      label: "值对象",
      options: [ // 环节信息
        {
          value: "stepInfo",
          label: "环节信息",
        }
      ]
    }
  ]
}
