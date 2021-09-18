//package com.xQuant.platform.app.settle.support;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.xQuant.base.IRKey;
//import com.xQuant.base.exception.IRBaseException;
//import com.xQuant.base.utils.WebContextUtils;
//import com.xQuant.base2.support.config.ConfigSupport;
//import com.xQuant.platform.app.aidSystem.ExecuteContext;
//import com.xQuant.platform.app.aidSystem.common.enums.CommunicateType;
//import com.xQuant.platform.app.aidSystem.common.enums.HandleStatus;
//import com.xQuant.platform.app.aidSystem.common.enums.PlatformType;
//import com.xQuant.platform.app.aidSystem.converter.PayFundConverter;
//import com.xQuant.platform.app.aidSystem.entity.AidSystemBriefData;
//import com.xQuant.platform.app.aidSystem.entity.PayFundEntry;
//import com.xQuant.platform.app.aidSystem.entity.PayFundResult;
//import com.xQuant.platform.app.aidSystem.exception.AidFailedException;
//import com.xQuant.platform.app.aidSystem.utils.AidSystemUtils;
//import com.xQuant.platform.app.common.DataDictionary;
//import com.xquant.pufaguigu.constant.CommonConfig;
//import com.xquant.pufaguigu.constant.InterName;
//import com.xquant.pufaguigu.constant.ResponseCode;
//import com.xquant.pufaguigu.service.PayFundService;
//import com.xquant.pufaguigu.util.XMLUtils;
//
//@Component
//public class PayFundConverterForSpdsvNew implements PayFundConverter {
//
//    private static org.slf4j.Logger logger = LoggerFactory
//            .getLogger(PayFundConverterForSpdsvNew.class);
//    @Autowired
//    private PayFundService payFundService = WebContextUtils.getBean("payFundService");
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<ExecuteContext> encode(AidSystemBriefData aidSystemBriefData, PayFundEntry payFundEntry) {
//        List<ExecuteContext> list = new ArrayList<ExecuteContext>();
//        ExecuteContext context = new ExecuteContext();
//        context.setCommunicateType(CommunicateType.Socket);
//
//        HashMap<String, String> extInfoMap = (HashMap<String, String>) payFundEntry.getExtInfo();
//        if ("0".equals(extInfoMap.get("PCORDE"))) {
//            //不通过核心支付Core，改为对接UPP系统
//            context.setPlatformType(PlatformType.Payment);
//            context.setSendPacket(buildReqXmlForPC(aidSystemBriefData, payFundEntry));
//            context.setPara("PCORDE", "PC");
//        } else if ("1".equals(extInfoMap.get("PCORDE"))) {
//            context.setPlatformType(PlatformType.Core);
//            context.setSendPacket(buildReqXmlForDE(aidSystemBriefData, payFundEntry, extInfoMap));
//            context.setPara("PCORDE", "DE");
//        } else {
//            return null;
//        }
//        list.add(context);
//        return list;
//    }
//
//    @SuppressWarnings("unchecked")
//    private Object buildReqXmlForPC(AidSystemBriefData aidSystemBriefData,
//                                    PayFundEntry payFundEntry) {
//        String xmlStr = "";
//        try {
//            AidSystemUtils.logDebug(this.getClass(), "开始拼装报文");
//            Map<String, String> headMap = XMLCommon.setCommonHeadMapNew(aidSystemBriefData,
//                    InterName.PAY_PATH_CODE_PAY, CommonConfig.CHANNEL_CODE_NEW,
//                    payFundEntry.getFlowNo(), CommonConfig.TEMPLATE_CODE, InterName.PAY_FUND_SERVICE_CODE);
//
//            Map<String, String> bodyMap = new HashMap<String, String>();
//
//            //付款人账号
//            bodyMap.put("payeracc", payFundEntry.getAcctCode());
//            //付款人名称
//            bodyMap.put("payername", payFundEntry.getAcctName());
//            //实际付款人账号
//            bodyMap.put("realpayeracc", payFundEntry.getAcctCode());
//            //实际付款人名称
//            bodyMap.put("realpayername", payFundEntry.getAcctName());
//            //交易金额
//            bodyMap.put("amount", new DecimalFormat("###0.00").format(Math.abs(payFundEntry.getTransFunds())));
//            //币种
//            bodyMap.put("currency", null == payFundEntry.getCurrency() ? "CNY" : payFundEntry.getCurrency());
//            //付款人开户行号
//            bodyMap.put("payeraccbank", null == payFundEntry.getAcctBankCode() ? "" : payFundEntry.getAcctBankCode());
//            HashMap<String, String> extInfoMap = (HashMap<String, String>) payFundEntry.getExtInfo();
//            ConfigSupport config = ConfigSupport.getInstance();
//            if ("X_CNBD_QSS".equals(extInfoMap.get("HOSTMARKET"))) {
//                // 收款单位名称
//                bodyMap.put("payeename", config.getProperty("market.qss.payee.accname"));
//                // 收款账号
//                bodyMap.put("payeeacc", config.getProperty("market.qss.payee.accno"));
//                // 收款人开户行
//                bodyMap.put("payeeaccbank", config.getProperty("market.qss.payee.bankcode"));
//            } else if ("X_CNBD_ZZD".equals(extInfoMap.get("HOSTMARKET"))) {
//                // 收款单位名称
//                bodyMap.put("payeename", config.getProperty("market.zzd.payee.accname"));
//                // 收款账号
//                bodyMap.put("payeeacc", config.getProperty("market.zzd.payee.accno"));
//                // 收款人开户行
//                bodyMap.put("payeeaccbank", config.getProperty("market.zzd.payee.bankcode"));
//            } else if ("X_CNBD_QSS_IRS".equals(extInfoMap.get("HOSTMARKET"))) {
//                // 收款单位名称
//                bodyMap.put("payeename", config.getProperty("market.qss.irs.payee.accname"));
//                // 收款账号
//                bodyMap.put("payeeacc", config.getProperty("market.qss.irs.payee.accno"));
//                // 收款人开户行
//                bodyMap.put("payeeaccbank", config.getProperty("market.qss.irs.payee.bankcode"));
//            } else {
//                // 收款单位名称
//                bodyMap.put("payeename", payFundEntry.getCustAcctName());
//                // 收款账号
//                bodyMap.put("payeeacc", payFundEntry.getCustAcctCode());
//                // 收款人开户行
//                bodyMap.put("payeeaccbank", payFundEntry.getCustAcctBankCode());
//            }
//
//            //客户号
//            客户号
//            bodyMap.put("customerno", extInfoMap.containsKey("customerno") ? extInfoMap.get("customerno") : "");
//            // 业务优先级
//            bodyMap.put("priority", "0");
//            if (DataDictionary.TRADE_TYPE_SWP_IR_FST_L.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_FST_S.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_RECEIVE_L.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_RECEIVE_S.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_END_L.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_END_S.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_TERMINATION_L.equals(extInfoMap.get("TRD_TYPE"))
//            		||DataDictionary.TRADE_TYPE_SWP_IR_TERMINATION_S.equals(extInfoMap.get("TRD_TYPE"))
//            		) { // 利率互换
//            	if("CNY".equals(bodyMap.get("currency"))) { // 本币
//            		// 业务类型编码
//                    bodyMap.put("busitype", config.getProperty("pay.pc.llhh.busitype", "A200"));
//                    // 业务种类编码
//                    bodyMap.put("busikind", config.getProperty("pay.pc.llhh.busikind", "02126"));
//            	}else { // 外币
//            		// 业务类型编码
//                    bodyMap.put("busitype", config.getProperty("pay.pc.wh.busitype", "A1122"));
//                    // 业务种类编码
//                    bodyMap.put("busikind", config.getProperty("pay.pc.wh.busikind", "02014"));
//            	}
//            }else if ( // 外汇掉期
//            		DataDictionary.TRADE_TYPE_SWAP_XR_IN.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_SWAP_XR_OUT.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_SWAP_XR_IN_NEAR.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_SWAP_XR_IN_FAR.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_SWAP_XR_OUT_NEAR.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_SWAP_XR_OUT_FAR.equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1501200".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1501201".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1501300".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1501301".equals(extInfoMap.get("TRD_TYPE"))
//            		// 外汇期权
//            		|| "1701000".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701001".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701210".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701211".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701301".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701300".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701201".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1701200".equals(extInfoMap.get("TRD_TYPE"))
//            		// 外汇即期
//            		|| "1301000".equals(extInfoMap.get("TRD_TYPE"))
//            		|| "1301001".equals(extInfoMap.get("TRD_TYPE"))
//            		// 外汇远期
//            		|| DataDictionary.TRADE_TYPE_FWD_XR_IN.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_FWD_XR_OUT.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_FWD_XR_IN_END.equals(extInfoMap.get("TRD_TYPE"))
//            		|| DataDictionary.TRADE_TYPE_FWD_XR_OUT__END.equals(extInfoMap.get("TRD_TYPE"))){
//            	// 业务类型编码
//                bodyMap.put("busitype", config.getProperty("pay.pc.wh.busitype", "A1122"));
//                // 业务种类编码
//                bodyMap.put("busikind", config.getProperty("pay.pc.wh.busikind", "02014"));
//            }else {
//            	// 业务类型编码
//                bodyMap.put("busitype", config.getProperty("pay.pc.mr.busitype", "A100"));
//                // 业务种类编码
//                bodyMap.put("busikind", config.getProperty("pay.pc.mr.busikind", "02102"));
//            }
//            String memo = payFundEntry.getMemo();
//            // 备注
//            bodyMap.put("remark", StringUtils.isBlank(memo) ? "" : memo);
//            // 附言
//            bodyMap.put("postscript", StringUtils.isBlank(memo) ? "" : memo);
//            //手续费类型
//            bodyMap.put("feetype","0");
//            //实际手续费
//            bodyMap.put("feeamount","0.00");
//            //标准手续费
//            bodyMap.put("feeamount1","0.00");
//            //业务场景
//            bodyMap.put("waitscene","00");
//
//            xmlStr = XMLUtils.getXMLAndXMLLength(XMLUtils.buildRequestXML(XMLTemplat.PAY_FUND_NEW,
//                    headMap, CommonConfig.REQUEST_XML_HEAD_NODE, bodyMap, CommonConfig.REQUEST_XML_BODY_NODE));
//
//            logger.debug("支付报文拼接完成： "+xmlStr);
//
//        } catch (Exception e) {
//            Logger.getLogger(PayFundConverterForSpdsvNew.class).debug("报文拼接错误！");
//            AidSystemUtils.logDebug(this.getClass(), "报文拼接错误: " + e);
//            throw new AidFailedException(e.getMessage());
//        }
//        AidSystemUtils.logDebug(this.getClass(), "拼装报文完成");
//        return xmlStr;
//    }
//
//    private Object buildReqXmlForDE(AidSystemBriefData aidSystemBriefData,
//                                    PayFundEntry payFundEntry, HashMap<String, String> extInfoMap) {
//        String xmlStr = "";
//        try {
//            AidSystemUtils.logDebug(this.getClass(), "开始拼装报文");
//
//            Map<String, String> headMap = XMLCommon.setCommonHeadMap(
//                    aidSystemBriefData, payFundEntry.getFlowNo(), "100038",
//                    CommonConfig.CHANNEL_CODE);
//            Map<String, String> bodyMap = new HashMap<String, String>();
//            String batchNo = null;
//            try {
//                batchNo = getBatchNo(aidSystemBriefData);
//            } catch (Exception e) {
//                AidSystemUtils.logDebug(this.getClass(), "BatchNo生成失败！ " + e);
//                throw new IRBaseException("BatchNo生成失败！ " + e.getMessage());
//            }
//
//            // S001序号开始（S002/S003）
//            bodyMap.put("BatchNoA", batchNo);
//            bodyMap.put("Description", extInfoMap.get("ORDID"));
//            bodyMap.put("Balancing", "Y");
//            // S001序号开始（S002/S003）
//            bodyMap.put("BatchNoB", batchNo);
//            bodyMap.put("CurrNo", "1");
//            bodyMap.put("TxnMain", "ISD");
//            bodyMap.put("Txnoffset", "ISD");
//            bodyMap.put("Drcr", "D");
//            bodyMap.put("ValueDt", extInfoMap.get("VALUEDT").replaceAll("-", ""));
//            bodyMap.put("CCY", null == payFundEntry.getCurrency() ? "CNY" : payFundEntry.getCurrency());
//            if (IRKey.CURRENCRY_CNY.equals(payFundEntry.getCurrency())) {
//                bodyMap.put("AccountA", "301040100");
//                // 对手方账户
//                bodyMap.put("AccountB", payFundEntry.getCustAcctCode());
//                // 机构号payFundEntry
//                bodyMap.put("Brn", payFundEntry.getCustAcctCode().substring(0, 3));
//            } else {
//            	if(DataDictionary.INST_DIRECTION_IN.equals(payFundEntry.getDirection())){
//					bodyMap.put("AccountA", payFundEntry.getAcctCode());
//					bodyMap.put("AccountB", "139010600");
//				}else{
//					bodyMap.put("AccountA", "263020800");
//					bodyMap.put("AccountB", payFundEntry.getAcctCode());
//				}
//                bodyMap.put("Brn", payFundEntry.getAcctCode().substring(0, 3));
//            }
//            bodyMap.put("AmtA", new DecimalFormat("###0.00").format(Math.abs(payFundEntry.getTransFunds())));
//            bodyMap.put("AuthStat", "A");
//            // 空
//            bodyMap.put("ExRate", "");
//            bodyMap.put("BokDt", extInfoMap.get("BOKDT").replaceAll("-", ""));
//            // 空
//            bodyMap.put("LcyAmt", "");
//
//            bodyMap.put("AmtB", new DecimalFormat("###0.00").format(Math.abs(payFundEntry.getTransFunds())));
//            bodyMap.put("SlNo", "1");
//
//
//            xmlStr = XMLUtils.getXMLAndXMLLength(XMLUtils.buildRequestXML(
//                    XMLTemplat.PAY_INNER_FUND, headMap, bodyMap, InterName.PAY_FUND_INNER_REQUEST));
//
//            logger.debug("支付报文拼接完成： "+xmlStr);
//
//        } catch (Exception e) {
//            Logger.getLogger(PayFundConverterForSpdsvNew.class).debug("报文拼接错误！");
//            AidSystemUtils.logDebug(this.getClass(), "报文拼接错误: " + e);
//            throw new IRBaseException(e.getMessage());
//        }
//        AidSystemUtils.logDebug(this.getClass(), "拼装报文完成");
//        return xmlStr;
//    }
//
//    private String getBatchNo(AidSystemBriefData briefData) throws Exception {
//        payFundService.updateBatchNo(briefData.getBaseDate());
//        int num = payFundService.getBatchNo(briefData.getBaseDate());
//        String batchNo = Integer.toString(num, 36);
//        int length = batchNo.length();
//        StringBuffer sb = null;
//        while (length < 3) {
//            sb = new StringBuffer();
//            sb.append("0").append(batchNo);
//            batchNo = sb.toString();
//            length = batchNo.length();
//        }
//        return "S" + batchNo.toUpperCase();
//    }
//
//    @Override
//    public PayFundResult decode(List<ExecuteContext> executeContextList) {
//        PayFundResult result = new PayFundResult();
//        result.setHandleStatus(HandleStatus.Unknown);
//        AidSystemUtils.logDebug(this.getClass(), "开始解析报文");
//        if (executeContextList != null) {
//            return parseResXml(new String((byte[]) executeContextList.get(0).getRececiePacket()),
//            		executeContextList.get(0).getPara("PCORDE").toString());
//        }
//        AidSystemUtils.logDebug(this.getClass(), "解析报文失败");
//        return result;
//    }
//
//    private PayFundResult parseResXml(String packet, String flag) {
//
//        logger.debug("支付返回报文："+packet);
//
//        PayFundResult result = new PayFundResult();
//        result.setHandleStatus(HandleStatus.Unknown);
//        String str = packet.substring(8, packet.length()).trim().replace("\n", "");
//        Map<String, String> headMap = null;
//        Map<String, String> bodyMap = null;
//
//        if("PC".equals(flag)) {
//        	headMap = XMLUtils.getResponse(str, CommonConfig.RESPONSE_XML_HEAD_NODE);
//            bodyMap = XMLUtils.getResponse(str, CommonConfig.RESPONSE_XML_BODY_NODE);
//            return getPayFundResult(result, headMap, bodyMap, "dealcode", "dealmsg", "busiagentserialno");
//        }else if("DE".equals(flag)){
//        	headMap = XMLUtils.getHeader(str, InterName.PAY_FUND_INNER_RESPONSE);
//            bodyMap = XMLUtils.getBody(str, InterName.PAY_FUND_INNER_RESPONSE);
//            return getPayFundResult(result, headMap, bodyMap, "StatusCode", "ServerStatusCode", "CoreTransJnlNo");
//        }else {
//        	result.setHandleResult("PC_OR_DE：" + flag);
//        	return result;
//        }
//    }
//
//    /**
//     * @param result    结果
//     * @param headerMap
//     * @param bodyMap
//     * @param codeName  响应码节点
//     * @param msgName   响应信息节点
//     * @param noName    响应流水节点
//     * @return
//     */
//    private PayFundResult getPayFundResult(PayFundResult result, Map<String, String> headerMap, Map<String, String> bodyMap,
//                                           String codeName, String msgName, String noName) {
//        // 响应头
//        String dealcode = "";
//        String dealmsg = "";
//
//        // 响应体
//        String bankid = "";
//        if (null != headerMap) {
//            dealcode = headerMap.containsKey(codeName) ? headerMap.get(codeName) : "";
//            dealmsg = headerMap.containsKey(msgName) ? headerMap.get(msgName) : "";
//        }
//
//        if (ResponseCode.RESPONSE_SUCCESS.equals(dealcode)) {
//            if (null != bodyMap) {
//                bankid = bodyMap.containsKey(noName) ? bodyMap.get(noName) : "";
//                result.setFlowNo(bankid);
//                result.setHandleStatus(HandleStatus.Success);
//                result.setHandleResult(dealmsg);
//            } else {
//                result.setHandleStatus(HandleStatus.Failed);
//                result.setHandleResult("解析报文失败，响应报文体为空,响应码为[" + dealcode + "],响应信息为[" + dealmsg + "]");
//                AidSystemUtils.logDebug(this.getClass(), "解析报文失败，响应报文体为空,响应码为[" + dealcode + "],响应信息为[" + dealmsg + "]");
//            }
//        } else {
//            result.setHandleStatus(HandleStatus.Failed);
//            result.setHandleResult("响应码为[" + dealcode + "],响应信息为[" + dealmsg + "]");
//            AidSystemUtils.logDebug(this.getClass(), "响应码为[" + dealcode + "],响应信息为[" + dealmsg + "]");
//        }
//
//        return result;
//    }
//}
