package org.mossmc.mosscg.StatusBox.Http;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Encrypt.EncryptBase64;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    public static void initBasicResponse(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "*");
        headers.add("Access-Control-Max-Age", "864000");
        headers.add("Access-Control-Allow-Headers", "*");
        headers.add("Access-Control-Allow-Credentials", "true");
    }

    public static void completeResponse(HttpExchange exchange, JSONObject responseData, JSONObject data){
        BasicInfo.sendDebug("["+responseData.toJSONString()+"]["+data.toJSONString()+"]");
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody(), StandardCharsets.UTF_8);
        try {
            exchange.sendResponseHeaders(200,0);
            String dataSend = EncryptBase64.encode(responseData.toString());
            writer.append(dataSend);
            writer.flush();
        } catch (Exception e) {
            BasicInfo.logger.sendWarn("API请求在返回响应时发生错误，原因："+e.getMessage()+"，数据如下：["+data.toString()+"]["+responseData.toString()+"]");
        } finally {
            try {
                writer.close();
                exchange.close();
            } catch (IOException e) {
                BasicInfo.logger.sendWarn("关闭API的输出流时发生错误，原因："+e.getMessage());
            }
        }
    }
}
