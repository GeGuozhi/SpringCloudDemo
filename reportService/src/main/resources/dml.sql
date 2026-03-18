INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('TPL_BOND_001', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1, coop_name2, coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''债券交易'' AS biz_type"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_b",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            }
        ]
    }
}', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1, coop_name2, coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''债券交易'' AS biz_type"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_b",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            }
        ]
    }
}', '
{
  "summaryType": "POINT",
  "types": "债券交易",
  "querySql": "SELECT
  COALESCE(sum(trade_amount),0) AS value1,    COALESCE(sum(trade_count),0) AS value3
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND biz_type = #{type};"
}', '2026-03-16 18:03:50.209672', '2026-03-16 18:03:50.209672', '债券交易');
INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('TPL_CREDIT_001', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1, coop_name2, coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''信用拆借'' AS biz_type"
            },
            {
                "level": 1,
                "fieldName": "borrow_type",
                "querySql": "SELECT borrow_type FROM dim_borrow_type ORDER BY sort_no"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_c",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            },
            {
                "name": "trade_amt_c",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
            }
        ]
    }
}', '{
  "common": {
    "dataHeaderRowIndex": 2,
    "leftDimensions": [
      {
        "fieldName": "coop_display",
        "displayName": "合作机构名称",
        "querySql": "SELECT coop_name1, coop_name2, coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
      },
      {
        "fieldName": "org_attr",
        "displayName": "机构属性",
        "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3}"
      }
    ]
  },
  "config": {
    "topDimensions": [
      {
        "level": 0,
        "fieldName": "biz_type",
        "querySql": "SELECT ''信用拆借'' AS biz_type"
      },
      {
        "level": 1,
        "fieldName": "borrow_type",
        "querySql": "SELECT borrow_type FROM dim_borrow_type ORDER BY sort_no"
      }
    ],
    "metrics": [
      {
        "name": "trade_cnt_c",
        "displayName": "笔数",
        "querySql": "SELECT COALESCE(SUM(trade_count), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
      },
      {
        "name": "trade_amt_c",
        "displayName": "规模(亿元)",
        "querySql": "SELECT COALESCE(SUM(trade_amount), ''0'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}"
      }
    ]
  }
}', '{
  "summaryType": "RANGE",
  "types": "信用拆借,本币拆出,本币拆入,外币拆出,外币拆入",
  "querySql": "SELECT
  COALESCE(sum(trade_amount),0) AS value1,    COALESCE(sum(trade_count),0) AS value2
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND (CASE WHEN #{type} = ''信用拆借'' THEN biz_type
      ELSE borrow_type END) = #{type};"
}', '2026-03-16 18:04:19.300819', '2026-03-16 18:04:19.300819', '信用拆借');
