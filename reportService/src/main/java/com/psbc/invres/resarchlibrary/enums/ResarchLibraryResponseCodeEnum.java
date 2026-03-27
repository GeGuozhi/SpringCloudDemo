package com.psbc.invres.resarchlibrary.enums;

import com.psbc.invres.resarchlibrary.entity.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举
 * <p>
 * 编码规则 Y9971449DCRPXXXX：
 * - Y9971449 固定前缀
 * - DC：第三段（2位），指明错误的二级分类
 *   业务类：密码与认证/PW，数量与限额/AL，权限控制/AC，信息滥缺/DC，内容非法/CE，重复交易/DT，时间与期限/TM，风险控制/RC，业务逻辑/BL
 *   技术类：不确定交易结果/NA，硬件错误/HW，数据内容/DC，I/O读写/RW，数据库/DB，网络通讯/NC，安全服务/SS，组件模块/CM，流量控制/FC，技术逻辑/TL，纯技术性错误/AT
 * - RP：报表服务
 * - XXXX：序号，从 0001 起递增
 */
@Getter
@AllArgsConstructor
public enum ResarchLibraryResponseCodeEnum implements IResponseCode {

    // ==================== 业务类错误（BL - 业务逻辑校验失败）====================

    /**
     * 请求参数缺失
     */
    BL_PARAM_MISSING("Y9971449DCRP0001", "branches/startDate/endDate 不能为空"),

    /**
     * 日期格式非法
     */
    BL_DATE_FORMAT_INVALID("Y9971449DCRP0002", "startDate/endDate 格式必须为 yyyy-MM-dd"),

    /**
     * 日期范围非法
     */
    BL_DATE_RANGE_INVALID("Y9971449DCRP0003", "startDate 不能大于 endDate"),

    /**
     * 分行参数缺失
     */
    BL_BRANCHES_MISSING("Y9971449DCRP0004", "branches 不能为空"),

    /**
     * 模板 common 缺少文件名配置
     */
    BL_TEMPLATE_FILENAME_MISSING("Y9971449DCRP0005", "模板 common 缺少文件名模板配置：excelFileNameTemplate/zipFileNameTemplate/zipEntryFileNameTemplate"),

    /**
     * 预览 - 缺少 dataHeaderRowIndex
     */
    BL_TEMPLATE_HEADER_ROW_MISSING("Y9971449DCRP0006", "模板缺少 dataHeaderRowIndex"),

    /**
     * 预览 - 缺少 leftDimensions
     */
    BL_TEMPLATE_LEFT_DIM_MISSING("Y9971449DCRP0007", "模板缺少 leftDimensions"),

    /**
     * 汇总/预览 JSON 格式非法
     */
    BL_JSON_FORMAT_INVALID("Y9971449DCRP0008", "JSON 格式不正确"),

    /**
     * 汇总 JSON 内容为空
     */
    BL_JSON_CONTENT_EMPTY("Y9971449DCRP0009", "JSON 内容为空"),

    /**
     * 汇总 JSON 缺少 querySql
     */
    BL_SUMMARY_QUERY_SQL_MISSING("Y9971449DCRP0010", "汇总 JSON 缺少 querySql"),

    /**
     * 汇总 JSON 缺少 summaryType
     */
    BL_SUMMARY_TYPE_MISSING("Y9971449DCRP0011", "汇总 JSON 缺少 summaryType"),

    /**
     * 汇总 JSON 缺少 types
     */
    BL_SUMMARY_TYPES_MISSING("Y9971449DCRP0012", "汇总 JSON 缺少 types"),

    /**
     * summaryType 非法枚举值
     */
    BL_SUMMARY_TYPE_INVALID("Y9971449DCRP0013", "summaryType 非法，只能为 POINT 或 RANGE"),

    /**
     * types 解析后为空
     */
    BL_SUMMARY_TYPES_EMPTY("Y9971449DCRP0014", "types 解析后为空"),

    /**
     * 汇总 SQL 参数为空
     */
    BL_SUMMARY_PARAM_MISSING("Y9971449DCRP0015", "汇总 SQL 参数不能为空"),

    /**
     * querySql 为空
     */
    BL_QUERY_SQL_MISSING("Y9971449DCRP0016", "querySql 不能为空"),

    /**
     * 预览请求参数缺失
     */
    BL_PREVIEW_PARAM_MISSING("Y9971449DCRP0017", "startDate/endDate/branch/modelName 不能为空"),

    /**
     * 查看 JSON 缺少 config
     */
    BL_VIEW_CONFIG_MISSING("Y9971449DCRP0018", "查看 JSON 必须包含 config"),

    /**
     * 查看 JSON 缺少 common
     */
    BL_VIEW_COMMON_MISSING("Y9971449DCRP0019", "查看 JSON 必须包含 common"),

    /**
     * 导出配置(req_para_val)为空
     */
    BL_EXPORT_CONFIG_MISSING("Y9971449DCRP0020", "导出配置不允许为空"),

    /**
     * 导出 JSON 缺少 common 或 config
     */
    BL_EXPORT_JSON_STRUCTURE_INVALID("Y9971449DCRP0021", "模板 JSON 必须包含 common 和 config"),

    /**
     * 各模板 common 配置不一致
     */
    BL_TEMPLATE_COMMON_INCONSISTENT("Y9971449DCRP0022", "模板 common 配置不一致"),

    // ==================== 技术类错误（AT - 纯技术性操作失败）====================

    /**
     * 生成压缩包失败
     */
    AT_ZIP_CREATE_FAILED("Y9971449DCRP0023", "生成压缩包失败"),

    /**
     * 生成预览数据失败
     */
    AT_PREVIEW_GENERATE_FAILED("Y9971449DCRP0024", "生成预览数据失败"),

    /**
     * 生成业务报表数据失败
     */
    AT_REPORT_GENERATE_FAILED("Y9971449DCRP0025", "生成业务报表数据失败"),

    /**
     * 生成 Excel 文件失败
     */
    AT_EXCEL_CREATE_FAILED("Y9971449DCRP0026", "生成 Excel 文件失败"),

    /**
     * 未读取到任何有效模板 common 配置
     */
    AT_TEMPLATE_COMMON_EMPTY("Y9971449DCRP0027", "未读取到任何有效模板 common 配置"),

    // ==================== 数据库类错误（DB - 数据库读写失败）====================

    /**
     * report_config 无模板数据
     */
    DB_TEMPLATE_NOT_FOUND("Y9971449DCRP0028", "report_config 无模板数据"),

    /**
     * 执行汇总 SQL 失败
     */
    DB_SUMMARY_SQL_FAILED("Y9971449DCRP0029", "执行汇总 SQL 失败"),

    // ==================== 数据类错误（DC - 数据缺失或不可用）====================

    /**
     * 未找到指定模板
     */
    DC_TEMPLATE_NOT_FOUND("Y9971449DCRP0030", "未找到模板"),

    /**
     * 查看 JSON(field_content_old_val) 为空
     */
    DC_VIEW_JSON_EMPTY("Y9971449DCRP0031", "查看 JSON 为空");

    final String code;
    final String msg;
}
