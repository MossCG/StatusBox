package org.mossmc.mosscg.StatusBox.Web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Encrypt.EncryptBase64;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebRequest {
    public static JSONObject sendRequest(JSONObject data) {
        JSONObject result = null;
        try {
            URL targetURL = new URL(BasicInfo.config.getString("APIAddress"));
            HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("User-Agent", "StatusBoxClient/"+BasicInfo.version);
            connection.setDoOutput(true);

            String inputData = EncryptBase64.encode(data.toString());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(inputData);
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder readInfo = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) readInfo.append(inputLine);
            reader.close();
            result = JSON.parseObject(EncryptBase64.decode(readInfo.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
