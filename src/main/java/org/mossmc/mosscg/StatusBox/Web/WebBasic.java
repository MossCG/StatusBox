package org.mossmc.mosscg.StatusBox.Web;

public class WebBasic {
    public static void initWeb() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        System.setProperty("http.keepAlive", "false");
    }
}
