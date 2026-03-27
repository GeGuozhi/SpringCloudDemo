INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('同业存单', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "sheetNameTemplate": "#{branch}分行",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''同业存单'' AS biz_type"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_b",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''同业存单'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,biz_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''同业存单'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
    "common": {
        "dataHeaderRowIndex": 1,
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1,coop_name2,coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''同业存单'' AS biz_type"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_b",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,coop_name2,coop_name3,biz_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''同业存单'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,coop_name2,coop_name3 biz_type",
                "keyCols": [
                    "coop_name1",
                    "coop_name2",
                    "coop_name3",
                    "biz_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,coop_name2,coop_name3,biz_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''同业存单'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,coop_name2,coop_name3 biz_type",
                "keyCols": [
                    "coop_name1",
                    "coop_name2",
                    "coop_name3",
                    "biz_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
  "summaryType": "POINT",
  "types": "同业存单",
  "querySql": "SELECT
  COALESCE(sum(trade_amount),''--'') AS value1,    COALESCE(sum(trade_count),''--'') AS value3
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND biz_type = #{type};"
}', '2026-03-16 18:03:50.209672', '2026-03-16 18:03:50.209672', '同业存单');
INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('贵金属', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "sheetNameTemplate": "#{branch}分行",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''贵金属业务'' AS biz_type"
            },
            {
                "level": 1,
                "fieldName": "borrow_type",
                "querySql": "SELECT borrow_type FROM dim_borrow_type where borrow_type like ''%贵金属%'' ORDER BY sort_no"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_c",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''贵金属业务'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_c",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''贵金属业务'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
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
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
            }
        ]
    },
    "config": {
        "topDimensions": [
            {
                "level": 0,
                "fieldName": "biz_type",
                "querySql": "SELECT ''贵金属业务'' AS biz_type"
            },
            {
                "level": 1,
                "fieldName": "borrow_type",
                "querySql": "SELECT borrow_type FROM dim_borrow_type where borrow_type like ''%贵金属%'' ORDER BY sort_no"
            }
        ],
        "metrics": [
            {
                "name": "trade_cnt_c",
                "displayName": "笔数",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''贵金属业务'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_c",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''贵金属业务'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
  "summaryType": "RANGE",
  "types": "贵金属业务,境内贵金属,境外贵金属"
  "querySql": "SELECT
  COALESCE(sum(trade_amount),''--'') AS value1,    COALESCE(sum(trade_count),''--'') AS value2
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND (CASE WHEN #{type} = ''贵金属业务'' THEN biz_type
      ELSE borrow_type END) = #{type};"
}', '2026-03-16 18:04:19.300819', '2026-03-16 18:04:19.300819', '贵金属业务');
INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('债券交易', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "sheetNameTemplate": "#{branch}分行",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
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
                "displayName": "持有数量",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''债券交易'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,biz_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''债券交易'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
    "common": {
        "dataHeaderRowIndex": 1,
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1,coop_name2,coop_name3 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
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
                "displayName": "持有数量",
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,coop_name2,coop_name3,biz_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''债券交易'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,coop_name2,coop_name3 biz_type",
                "keyCols": [
                    "coop_name1",
                    "coop_name2",
                    "coop_name3",
                    "biz_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_b",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND coop_name1 = #{coop_name1} AND coop_name2 = #{coop_name2} AND coop_name3 = #{coop_name3} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1,coop_name2,coop_name3,biz_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''债券交易'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,coop_name2,coop_name3 biz_type",
                "keyCols": [
                    "coop_name1",
                    "coop_name2",
                    "coop_name3",
                    "biz_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
  "summaryType": "RANGE",
  "types": "债券交易",
  "querySql": "SELECT
  COALESCE(sum(trade_amount),''--'') AS value1,    COALESCE(sum(trade_count),''--'') AS value3
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND biz_type = #{type};"
}', '2026-03-16 18:03:50.209672', '2026-03-16 18:03:50.209672', '债券交易');
INSERT INTO "public"."report_config" ("pr_key_id", "req_para_val", "field_content_old_val", "field_content_new_val", "upd_stamp", "create_stamp", "model_name") VALUES ('信用拆借', '{
    "common": {
        "dataHeaderRowIndex": 2,
        "titleTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作情况统计表",
        "excelFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "zipFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与分行辖内客户合作汇总表.zip",
        "zipEntryFileNameTemplate": "#{startDate}至#{endDate}金融市场部线上业务与#{branch}分行辖内客户合作汇总表.xlsx",
        "sheetNameTemplate": "#{branch}分行",
        "leftDimensions": [
            {
                "fieldName": "coop_display",
                "displayName": "合作机构名称",
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
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
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''信用拆借'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_c",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''信用拆借'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
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
                "querySql": "SELECT coop_name1 FROM dim_org WHERE branch = #{branch} ORDER BY id desc"
            },
            {
                "fieldName": "org_attr",
                "displayName": "机构属性",
                "querySql": "SELECT org_attr FROM dim_org WHERE branch = #{branch} AND coop_name1 = #{coop_name1}"
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
                "querySql": "SELECT COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_count), ''--'') AS value FROM fact_trade WHERE biz_type = ''信用拆借'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1,biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            },
            {
                "name": "trade_amt_c",
                "displayName": "规模(亿元)",
                "querySql": "SELECT COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE branch = #{branch} AND biz_type = #{biz_type} AND borrow_type = #{borrow_type} AND coop_name1 = #{coop_name1} AND trade_date >= #{startDate} AND trade_date <= #{endDate}",
                "batchQuerySql": "SELECT coop_name1, biz_type, borrow_type, COALESCE(SUM(trade_amount), ''--'') AS value FROM fact_trade WHERE biz_type = ''信用拆借'' and branch = #{branch} AND trade_date >= #{startDate} AND trade_date <= #{endDate} GROUP BY coop_name1, biz_type, borrow_type",
                "keyCols": [
                    "coop_name1",
                    "biz_type",
                    "borrow_type"
                ],
                "valueColumn": "value"
            }
        ]
    }
}', '{
  "summaryType": "RANGE",
  "types": "信用拆借,本币拆出,本币拆入,外币拆出,外币拆入",
  "querySql": "SELECT
  COALESCE(sum(trade_amount),''--'') AS value1,    COALESCE(sum(trade_count),''--'') AS value2
FROM
  fact_trade
WHERE
  branch = #{branch}
  AND trade_date >= #{startDate}
  AND trade_date <= #{endDate}
  AND (CASE WHEN #{type} = ''信用拆借'' THEN biz_type
      ELSE borrow_type END) = #{type};"
}', '2026-03-16 18:04:19.300819', '2026-03-16 18:04:19.300819', '信用拆借');



INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (69, '北京', '广东顺德农村商业银行股份有限公司', '', NULL, '城商行', '信用拆借', '本币拆入', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (70, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '信用拆借', '本币拆出', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (89, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '信用拆借', '本币拆出', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (108, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '信用拆借', '本币拆出', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (107, '天津', '广东顺德农村商业银行股份有限公司', '', NULL, '城商行', '信用拆借', '本币拆入', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (106, '天津', '法国巴黎银行(中国)有限公司', '', '', '外资行', '信用拆借', '本币拆入', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (87, '杭州', '法国巴黎银行(中国)有限公司', '', '', '外资行', '信用拆借', '本币拆入', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (88, '杭州', '广东顺德农村商业银行股份有限公司', '', NULL, '城商行', '信用拆借', '本币拆入', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (71, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (72, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (73, '北京', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (74, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '债券交易', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (75, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (76, '北京', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (90, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (91, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (92, '杭州', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (93, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '债券交易', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (94, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (95, '杭州', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (109, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (110, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (111, '天津', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (112, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '债券交易', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (113, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '债券交易', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (114, '天津', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '债券交易', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (83, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (96, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (97, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (98, '杭州', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (99, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (100, '杭州', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (101, '杭州', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (102, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (77, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (78, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (79, '北京', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (80, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (81, '北京', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (82, '北京', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (115, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-07-05', 10, 22.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (116, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-08-10', 3, 10.31);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (117, '天津', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-07-12', 8, 31.26);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (118, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-08-03', 4, 15.00);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (119, '天津', '法国巴黎银行(中国)有限公司', '中国同业存单', '中国政府债券', '外资行', '同业存单', '', '', '2025-09-01', 2, 19.57);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (120, '天津', '广东顺德农村商业银行股份有限公司', '中国同业存单', NULL, '城商行', '同业存单', '', '', '2025-09-08', 5, 18.61);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (121, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '同业存单', '', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (104, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境外贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (105, '杭州', '广东顺德农村商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (86, '北京', '广东顺德农村商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (85, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境外贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (84, '北京', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (103, '杭州', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (122, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (123, '天津', '大同市商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境外贵金属业务', '', '2025-09-15', 6, 111.66);
INSERT INTO `finance_report`.`fact_trade` (`id`, `branch`, `coop_name1`, `coop_name2`, `coop_name3`, `org_attr`, `biz_type`, `borrow_type`, `bond_dir`, `trade_date`, `trade_count`, `trade_amount`) VALUES (124, '天津', '广东顺德农村商业银行股份有限公司', NULL, NULL, '城商行', '贵金属业务', '境内贵金属业务', '', '2025-09-15', 6, 111.66);
