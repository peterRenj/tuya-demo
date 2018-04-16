package com.tuya.smart;

import com.tuya.smart.model.RequestMessage;
import com.tuya.smart.model.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * created by renjin on 2018/04/16
 */
public class DeviceList {


    private static final String END_URI = "https://a1.tuyacn.com/api.json";// 调用中国区的API（您可换成其他可用区）

    private static final String ACCESS_ID = "qaphe5d8pyg7fj9d47tt"; // TODO: 请联系涂鸦获取

    private static final String ACCESS_KEY = "pruyrwq4ea9kjav8jr9gwgcptne8xjfm"; // TODO: 请联系涂鸦获取

    public static void main(String[] args) {
           /*
            创建client，accessId&accessKey由涂鸦提供
            accessId作为clientId
            accessKey用于签名
           */
        TuyaCloudClient client = new TuyaCloudClient(ACCESS_ID, ACCESS_KEY, END_URI);

        // 构造HTTPS请求
        RequestMessage request = new RequestMessage();
        request.setApi("tuya.cloud.user.device.list"); //API
        request.setApiVersion("1.0");
        request.setOs("Linux");
        request.setLang("zh");

        // 封装请求参数(接口需要的具体参数请参考API手册)

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", "ay152386280966946xBV");

           /*
            注:
               除注册及获取统计数据等少量接口,大部份接口都需要sessionId。
               具体是否需要sessionId请参考API手册。
               您可以从注册和登录接口返回结果得到,返回结果字段为sid
           */

        // 将请求参数加入到HTTPS请求中
        request.setParams(params);

           /*
            发起请求，获得响应
            如果请求成功, 则response里的result会是个JSON对象封装的结果.
            如果请求失败, 请查看errorMsg和errorCode,进行相应的处理.
           */
        ResponseMessage response = client.sendRequest(request);
        Object result = response.getResult();
        System.out.println(result);
        /**
         * https://a1.tuyacn.com/api.json?a=tuya.cloud.user.device.list&time=1523871042&lang=zh-Hans&v=1.0&os=Linux&clientid=qaphe5d8pyg7fj9d47tt&sign=6423eb6fda926d9e4393691b49abe36b&postData={"uid":"ay152386280966946xBV"}
         */
    }
}
