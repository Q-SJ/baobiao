package com.iot.baobiao.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.Condition;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.Main;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.iot.baobiao.pojo.BaobiaoOrder;
import com.iot.baobiao.service.BaobiaoOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by jia on 2016/9/6.
 */
@RestController
public class PayController {

    @Autowired
    BaobiaoOrderService baobiaoOrderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String CHARSET = "UTF-8";
//    private static final String APP_ID = "2016083101827293";
//    private static final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKxlL6NDDAYK4749" +
//            "Aaep7v2gwHHjc3PQG//ogspXotHHojP1CDQVPoGYaw0j/wFNcLCUQjtmkA2yzeDf" +
//            "mVio6ODywu2otlZS806xOK2MfqQkCyvs8cCrdM2peWc7lDPo1DOzvlePyqY/1cL2" +
//            "sysAemiD8etRSFStdJcUpikYKmHjAgMBAAECgYAnMnaLY3I3aYBwv8RBj/TD/Cnl" +
//            "ezbD7VyAaihaw2RE2GQ79gmgdfgZVUN54GZZsHbf30XT5bT5OF9xMsXWD8mrA/39" +
//            "T84+WLAe5c0kNdNq3GyvaJsA3jFTJ/UeyiScLpOl2r/4sPh4T+bg8FIQ4lJ4ZBfM" +
//            "r2mCRdh2FuwEZTVFsQJBAOWrbUwxbcCo6Bwp4/me2kGlczt4V39CmwFufF21Jn3I" +
//            "vEZJ6lI/SQFaFG32ppp1yGZK/gyNBIB2a0kkJDkHSFkCQQDAKNBNwIDkGBH9L3ay" +
//            "nLS0PgnUZOyP9RUaFEAk2jgf4o2rPL0TvheSk4NkPvHUK6Vy+2wSIhKP3l81UWlE" +
//            "m7SbAkEA02wNv3g5GoPiuBpv/RiYvpm6DGLp2QLNgnHdFr5t4pjpiKL+jBwp7o6o" +
//            "A9ps3//RLSmX2KHwJPneJYSZu83ScQJAKtEtURXi4nBGmd7YmQX38e2c7Rmr6IvY" +
//            "rt3ySQPi25/0p0I6c0q5H+0EqvKnmfo7vHovY376GdqX5l6EkUgMnwJANQFAkjn3" +
//            "ItRsdKd/iodnq29TPi9CgSelEvD6px6kOTOT1KUXwG+JGNvnp7UL1QPZGdmpt3Lw" +
//            "GgpkIIwbNi3B0Q==";
//    private static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXy" +
//            "iUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi6" +
//            "0j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//    private static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

//    private static final String APP_ID = "2016082000300485";
//    private static final String APP_PRIVATE_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMKXZrFR+rnvYgBs" +
//            "9qz2cE1mCSIBReaqan+5Pf5+02Hyj4HzcNTTWqHFm91IH3wYPyhpM7XlbgJ5yWJt" +
//            "gC4g1lz75r8a+UCyuxP8by1LV/44Gi/TIfLSgATfQ73OcM9imXocRdYz2ZCwqi1g" +
//            "V+b3UDoy/Da5w07gRWizFzS6Vq1rAgMBAAECgYEAqHHc4GRBsRCKeinYwtK1Vhqc" +
//            "j0Yg11Lvy85z3si0fNY26dvs8R5gFydzC/Mx5f8rNPUUYUHQn+4CqOR3D/c291X1" +
//            "iToV2NEVLHeJrOUDknP4oQriqt2w9pZ8rzwZp2jcWvRVUF4zTpEiMppmORP6spRf" +
//            "X6DLZg29SFI6GZWu6TkCQQDp3mim1QBhuS3YONEZgqC69zn0/DGOFkeIx0S18qAu" +
//            "1X4I1FEjVTkY4HPdwihpgYajm0UFg1lk8mTiunHpZRCnAkEA1QF6U1AKjM6zsVdE" +
//            "nRXEDTCC75uVJGSYFJWHHx9Pjyd9vX8nSZV0Z0U4V0ZG0n0yvHj5LRO6U5FCqFRw" +
//            "1WixnQJBALmCKz8SvF/H9N6LiwmSPY6w5q82kNRlRc7wSceNspQT0wqL5+SACG98" +
//            "M0xXY5j1HmiOlHxgCTvyriXOwObivQcCQQCTNaNB4uZ3q/86R/KukbVd3DIRwLfR" +
//            "YAhO6Yxp8Oy+Je/bv/359+Vr3cXzYyldHZOr9/tVsPWr/Y9Q4JLemq1tAkEAlBU7" +
//            "+4EdzFap7e/FMgyKD5DmL8H2iAEuMRRCPL84GhFfK/7PSQ/40NgKxpTgY44NlElH" +
//            "XcRPw5CZu6gqdiNJOA==";
//    private static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0Gq" +
//            "gS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTF" +
//            "Y99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
//    private static final String ALIPAY_GATEWAY = "https://openapi.alipaydev.com/gateway.do";
//
//    private AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY,
//            APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY);
//
//    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

    private static Log log = LogFactory.getLog(PayController.class);

    private static final String TIMEOUT = "30m";

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();


        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();


        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do")
                .setCharset("GBK")
                .setFormat("json")
                .build();
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(), response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    public boolean queryOrder(String outtradeno) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = outtradeno;

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");
                System.out.println("订单已支付成功");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                System.out.println(response.getBody());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                        System.out.println(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                return true;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                System.out.println("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                System.out.println("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                System.out.println("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return false;
    }

    @RequestMapping(value = "/pay/alipay", method = RequestMethod.POST)
    public Map<String, String> alipay(@RequestParam String amount, @RequestParam int userid) {

        Map<String, String> map = new HashMap<String, String>();

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "baobiao" + System.currentTimeMillis() + (long)(Math.random() * 10000000L);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "保标支付";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = amount;

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "2088102172329883";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "2088102172329883";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = TIMEOUT;

//        // 商品明细列表，需填写购买商品详细信息，
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject)
                .setTotalAmount(totalAmount)
                .setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount)
                .setSellerId(sellerId)
                .setBody(body)
                .setOperatorId(operatorId)
                .setStoreId(storeId)
                .setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://120.25.162.238:8080/baobiao/pay/notify");//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
//                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info(outTradeNo + "号订单预下单成功!");

                AlipayTradePrecreateResponse response = result.getResponse();

                BaobiaoOrder order = new BaobiaoOrder(userid, outTradeNo, "", Double.parseDouble(amount), new Date(), 1);
                baobiaoOrderService.insertOrder(order);

                map.put("status", "true");
                map.put("qrcode", response.getQrCode());
                map.put("outtradeno", outTradeNo);

                return map;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        map.put("status", "false");
        map.put("msg", "系统出现异常，请稍后再试！");
        return map;
    }

    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public String notifyResult(HttpServletRequest request, HttpServletResponse response) {
        log.debug("收到支付宝异步通知！");
        Map<String, String> params = new HashMap<String, String>();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
        log.debug("验证签名成功！");
        if (signVerified) {
            String outtradeno = params.get("out_trade_no");
            log.info(outtradeno + "号订单收到支付宝异步通知。");
//            System.out.println("验证签名成功！");


            //若参数中的appid和填入的appid不相同，则为异常通知
            if (!Configs.getAppid().equals(params.get("app_id"))) {
                log.warn("与付款时的appid不同，此为异常通知，应忽略！");
                return "failed";
            }

            //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
            BaobiaoOrder order = baobiaoOrderService.findOrderByOuttradeno(outtradeno);
            if (order == null) {
                log.warn(outtradeno + "查无此订单！");
                return "failed";
            }
            if (order.getAmount() != Double.parseDouble(params.get("total_amount"))) {
                log.warn("与付款时的金额不同，此为异常通知，应忽略！");
                return "failed";
            }

            if (order.getStatus() == BaobiaoOrder.TRADE_SUCCESS) return "success"; //如果订单已经支付成功了，就直接忽略这次通知

            String status = params.get("trade_status");
            if (status.equals("WAIT_BUYER_PAY")) { //如果状态是正在等待用户付款
                if (order.getStatus() != BaobiaoOrder.WAIT_BUYER_PAY) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.WAIT_BUYER_PAY, outtradeno);
            } else if (status.equals("TRADE_CLOSED")) { //如果状态是未付款交易超时关闭，或支付完成后全额退款
                if (order.getStatus() != BaobiaoOrder.TRADE_CLOSED) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_CLOSED, outtradeno);
            } else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { //如果状态是已经支付成功
                if (order.getStatus() != BaobiaoOrder.TRADE_SUCCESS) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_SUCCESS, outtradeno);
            } else {
                baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.UNKNOWN_STATE, outtradeno);
            }
            log.info(outtradeno + "订单的状态已经修改为" + status + "。");
            rabbitTemplate.convertAndSend("pay-success-exchange","pay-success", baobiaoOrderService.findOrderByOuttradeno(outtradeno));
        } else { //如果验证签名没有通过
            return "failed";
        }
        return "success";
    }

    @RequestMapping("/testRabbit")
    public void test() {
        BaobiaoOrder order = new BaobiaoOrder(1, "asjdfj", null, 1, new Date(), BaobiaoOrder.WAIT_BUYER_PAY);
        rabbitTemplate.convertAndSend("pay-success-exchange","pay-success", order);
    }
}
