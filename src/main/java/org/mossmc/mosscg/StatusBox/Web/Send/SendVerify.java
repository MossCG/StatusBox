package org.mossmc.mosscg.StatusBox.Web.Send;

import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Web.WebRequest;

public class SendVerify {
    public static JSONObject send() {
        JSONObject data = new JSONObject();
        data.put("type","verify");
        data.put("nodeID", BasicInfo.config.getString("nodeID"));
        data.put("nodeAuth", BasicInfo.config.getString("nodeAuth"));
        return WebRequest.sendRequest(data);
    }
}
