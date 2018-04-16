package com.tuya.smart;

import com.tuya.atop.client.domain.api.ApiRequestDO;
import com.tuya.atop.client.domain.app.AppInfoDO;
import com.tuya.basic.client.domain.base.ApiContextDO;
import com.tuya.smart.internal.MD5Util;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * created by renjin on 2018/04/16
 */
public class AtopThirdCloudMobileSignUtil {


    //封装请求参数
    private static TreeMap<String, String> paramsBuild(ApiRequestDO apiRequestDo) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("a", apiRequestDo.getApi());
        params.put("v", apiRequestDo.getApiContextDo().getApiVersion());
        params.put("lat", apiRequestDo.getApiContextDo().getLat());
        params.put("lon", apiRequestDo.getApiContextDo().getLon());
        params.put("lang", apiRequestDo.getApiContextDo().getLang());
        params.put("deviceId", apiRequestDo.getApiContextDo().getDeviceid());
        params.put("appVersion", apiRequestDo.getApiContextDo().getAppVersion());
        params.put("ttid", apiRequestDo.getApiContextDo().getTtid());
        params.put("os", apiRequestDo.getApiContextDo().getOs());
        params.put("clientId", apiRequestDo.getAppInfoDo().getClientId());
        if (StringUtils.isNotBlank(apiRequestDo.getN4h5())) {
            params.put("n4h5", apiRequestDo.getN4h5());
        }
        params.put("sp", apiRequestDo.getSp());
        params.put("time", apiRequestDo.getT());
        if (StringUtils.isNotBlank(apiRequestDo.getSession())) {
            params.put("sid", apiRequestDo.getSession());
        }
        if (StringUtils.isNotBlank(apiRequestDo.getData())) {
            params.put("postData", apiRequestDo.getData());
        }
        return params;
    }

    //拼接参数字符串
    private static String signAssembly(TreeMap<String, String> params, String secretKey) {
        StringBuilder str = new StringBuilder();
        str.append(secretKey);
        Set<String> keySet = params.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (StringUtils.isBlank(params.get(key))) {
                continue;
            }
            str.append(key);
            str.append("=");
            str.append(params.get(key));
            str.append("|");
        }
        String strValue = str.toString();
        strValue = strValue.substring(0, (strValue.length() - 1));
        return strValue;
    }

    //获取签名
    private static String getSign(ApiRequestDO apiRequestDo, String secretKey) {
        TreeMap<String, String> params = paramsBuild(apiRequestDo);
        String signString = signAssembly(params, secretKey);
        return signString;
    }

    //对签名进行MD5
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (Exception e) {
           // logger.warn("MD5加密异常!", e);
        }
        return s;
    }

    public static void main(String[] args) {
        ApiRequestDO apiRequestDo = new ApiRequestDO();

        apiRequestDo.setApi("tuya.cloud.user.device.list");

        ApiContextDO apiContextDO = new ApiContextDO();
        apiContextDO.setApiVersion("1.0");
        apiContextDO.setOs("Linux");
        apiContextDO.setLang("zh-Hans");

        AppInfoDO appInfoDO = new AppInfoDO();
        appInfoDO.setClientId("qaphe5d8pyg7fj9d47tt");
        apiRequestDo.setAppInfoDo(appInfoDO);

        apiRequestDo.setT("1523871042");//
        apiRequestDo.setData("{\"uid\":\"ay152386280966946xBV\"}");
        apiRequestDo.setApiContextDo(apiContextDO);

        String s = getSign(apiRequestDo, "pruyrwq4ea9kjav8jr9gwgcptne8xjfm");
        String sign = MD5Util.getMD5(s.getBytes());
        System.out.println(sign);
    }
}
