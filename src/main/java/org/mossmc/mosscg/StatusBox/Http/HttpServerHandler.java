package org.mossmc.mosscg.StatusBox.Http;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Http.Request.RequestUnknownType;
import org.mossmc.mosscg.StatusBox.Http.Request.RequestVerify;

public class HttpServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange){
        try {
            long receive = System.currentTimeMillis();
            HttpResponse.initBasicResponse(exchange);
            JSONObject data = HttpReader.readRequestData(exchange);
            JSONObject responseData = new JSONObject();
            data.put("remoteIP",HttpIP.getRemoteIP(exchange));
            switch (data.getOrDefault("type","null").toString()) {
                case "verify":
                    RequestVerify.getReply(exchange,data,responseData);
                    break;
                default:
                    RequestUnknownType.getReply(exchange,data,responseData);
                    break;
            }
            responseData.put("time",System.currentTimeMillis()-receive);
            HttpResponse.completeResponse(exchange,responseData,data);
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
        }
    }
}
