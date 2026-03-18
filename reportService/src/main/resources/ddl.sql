CREATE TABLE report_config (
    pr_key_id VARCHAR(32) PRIMARY KEY,
    req_para_val TEXT,
    field_content_old_val TEXT,
    field_content_new_val TEXT,
    upd_stamp TIMESTAMP,
    create_stamp TIMESTAMP,
    model_name VARCHAR(32)
);

COMMENT ON TABLE report_config IS '业务报表配置表';
COMMENT ON COLUMN report_config.pr_key_id IS '主键ID';
COMMENT ON COLUMN report_config.req_para_val IS '导出配置JSON';
COMMENT ON COLUMN report_config.field_content_old_val IS '查看配置JSON';
COMMENT ON COLUMN report_config.field_content_new_val IS '汇总配置JSON';
COMMENT ON COLUMN report_config.upd_stamp IS '更新时间戳';
COMMENT ON COLUMN report_config.create_stamp IS '创建时间戳';
COMMENT ON COLUMN report_config.model_name IS '模板名称';
