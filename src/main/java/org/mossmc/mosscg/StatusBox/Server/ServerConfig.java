package org.mossmc.mosscg.StatusBox.Server;

import org.mossmc.mosscg.MossLib.Config.ConfigManager;
import org.mossmc.mosscg.MossLib.File.FileCheck;
import org.mossmc.mosscg.MossLib.Object.ObjectConfig;
import org.mossmc.mosscg.StatusBox.BasicInfo;

import java.io.File;
import java.util.HashMap;

public class ServerConfig {
    public static void initConfigs() {
        File file = new File("./StatusBox/configs");
        if (!file.exists()) {
            FileCheck.checkDirExist("./StatusBox/configs");
            FileCheck.checkFileExist("./StatusBox/configs/example.yml","example.yml");
        }
    }

    public static void readConfigs() {
        ServerNode.nodeMap = new HashMap<>();
        File[] files = new File("./StatusBox/configs").listFiles();
        assert files != null;
        for (File file : files) {
            ObjectConfig config = ConfigManager.getConfigObject("./StatusBox/configs", file.getName(), null);
            if (config == null) return;
            String ID = config.getString("nodeID");
            ServerNode.nodeMap.put(ID,config);
            BasicInfo.logger.sendInfo("读取到节点配置文件："+file.getName()+"，已注册节点："+ID);
        }
    }
}
