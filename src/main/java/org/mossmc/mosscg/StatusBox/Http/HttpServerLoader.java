package org.mossmc.mosscg.StatusBox.Http;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.mossmc.mosscg.StatusBox.BasicInfo;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerLoader {
    public static HttpServerProvider provider;
    public static HttpServer server;

    public static void load() {
        try {
            HttpWatchDog.runWatchdog();
            provider = HttpServerProvider.provider();
            server = provider.createHttpServer(new InetSocketAddress(BasicInfo.config.getInteger("httpPort")),0);
            server.createContext("/API", new HttpServerHandler());
            server.setExecutor(Executors.newFixedThreadPool(BasicInfo.config.getInteger("httpPoolSize")));
            server.start();
            BasicInfo.logger.sendInfo("HttpAPI已于本地0.0.0.0:"+BasicInfo.config.getInteger("httpPort")+"启动!");
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
            BasicInfo.logger.sendWarn("无法加载HttpAPI!");
        }
    }
}
