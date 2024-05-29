package org.mossmc.mosscg.StatusBox.Http;

import org.mossmc.mosscg.StatusBox.BasicInfo;

public class HttpWatchDog {
    public static int requestCountAll;
    public static int requestCountSecond;
    public static int maxRequestPerSecond;
    public static int lastRequestPerSecond;
    public static void runWatchdog() {
        requestCountAll = 0;
        requestCountSecond = 0;
        lastRequestPerSecond = 0;
        maxRequestPerSecond = BasicInfo.config.getInteger("httpMaxQPS");
        Thread thread = new Thread(HttpWatchDog::dogThread);
        thread.start();
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public static void dogThread() {
        while (true) {
            try {
                int QPS = requestCountSecond;
                requestCountSecond = 0;
                lastRequestPerSecond = QPS;
                if (QPS >= maxRequestPerSecond) {
                    BasicInfo.logger.sendWarn("当前QPS数异常！实时QPS："+QPS);
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                BasicInfo.logger.sendException(e);
            }
        }
    }
}
