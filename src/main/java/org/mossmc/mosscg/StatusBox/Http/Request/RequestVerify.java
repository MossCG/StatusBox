package org.mossmc.mosscg.StatusBox.Http.Request;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import org.mossmc.mosscg.StatusBox.Server.ServerNode;

import java.io.IOException;

public class RequestVerify {
    /**
     * 请求格式：
     * {
     *     type: "verify",
     *     nodeID: "test",
     *     nodeAuth: "1234567890"
     * }
     * 响应格式：
     * {
     *     result: "true/false",
     *     status: "200/401/404",
     *     message: "Message info"
     * }
     */
    public static void getReply(HttpExchange exchange, JSONObject data, JSONObject responseData) throws IOException {
        String ID = data.getString("nodeID");
        String auth = data.getString("nodeAuth");
        if (!ServerNode.nodeMap.containsKey(ID)) {
            responseData.put("result",false);
            responseData.put("status","404");
            responseData.put("message","Node not found.");
            return;
        }
        if (!ServerNode.nodeMap.get(ID).getString("nodeAuth").equals(auth)) {
            responseData.put("result",false);
            responseData.put("status","401");
            responseData.put("message","Node auth wrong.");
            return;
        }
        responseData.put("result",true);
        responseData.put("status","200");
        responseData.put("message","Verify success.");
    }
}
