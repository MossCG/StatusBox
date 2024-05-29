package org.mossmc.mosscg.StatusBox.Http;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Encrypt.EncryptBase64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpReader {
    //请求内容请使用base64编码，使用POST方法传入数据
    public static JSONObject readRequestData(HttpExchange request) {
        JSONObject data = new JSONObject();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String read;
            while ((read = reader.readLine())!=null) sb.append(read);
            data = JSONObject.parseObject(EncryptBase64.decode(sb.toString()));
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
        }
        return data;
    }
}
