package org.mossmc.mosscg.StatusBox.Http;

import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpIP {
    public static String getRemoteIP(HttpExchange exchange) {
        String remote;
        if (exchange.getRequestHeaders().containsKey("X-Real-IP")) {
            remote = getIPString(exchange.getRequestHeaders().get("X-Real-IP"));
        } else {
            if (exchange.getRequestHeaders().containsKey("X-forwarded-for")) {
                remote = getIPString(exchange.getRequestHeaders().get("X-forwarded-for"));
            } else {
                remote = exchange.getRemoteAddress().getHostString();
            }
        }
        return remote;
    }

    public static String getIPString(List<String> list) {
        if (list.get(0).contains(",")) {
            String[] clearIPArray = list.get(0).split(",");
            List<String> realIPList = new ArrayList<>(Arrays.asList(clearIPArray));
            return realIPList.get(0);
        } else {
            return list.get(0);
        }
    }
}
