package org.mossmc.mosscg.StatusBox;

import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.MossLib.Command.CommandManager;
import org.mossmc.mosscg.MossLib.Config.ConfigManager;
import org.mossmc.mosscg.MossLib.File.FileCheck;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;
import org.mossmc.mosscg.StatusBox.Command.CommandReload;
import org.mossmc.mosscg.StatusBox.Command.CommandStatus;
import org.mossmc.mosscg.StatusBox.Http.HttpServerLoader;
import org.mossmc.mosscg.StatusBox.Server.ServerConfig;
import org.mossmc.mosscg.StatusBox.Web.Send.SendVerify;
import org.mossmc.mosscg.StatusBox.Web.WebBasic;

public class Main {
    public static void main(String[] args) {
        //预加载部分
        long startTime = System.currentTimeMillis();
        FileCheck.checkDirExist("./StatusBox");
        ServerConfig.initConfigs();
        ObjectLogger logger = new ObjectLogger("./StatusBox/logs");
        BasicInfo.logger = logger;

        //基础信息输出
        logger.sendInfo("欢迎使用StatusBox软件");
        logger.sendInfo("软件版本：" + BasicInfo.version);
        logger.sendInfo("软件作者：" + BasicInfo.author);

        //配置读取
        logger.sendInfo("正在读取配置文件......");
        BasicInfo.config = ConfigManager.getConfigObject("./StatusBox", "config.yml", "config.yml");
        if (!BasicInfo.config.getBoolean("enable")) {
            logger.sendInfo("你还没有完成配置文件的设置哦~");
            logger.sendInfo("快去配置一下吧~");
            logger.sendInfo("配置文件位置：./StatusBox/config.yml");
            System.exit(0);
        }
        if (BasicInfo.config.getString("runMode").equals("SERVER")) BasicInfo.mode = BasicInfo.runMode.SERVER;
        logger.sendInfo("运行模式：" + BasicInfo.mode.name());
        BasicInfo.debug = BasicInfo.config.getBoolean("debug");

        //对应模式启动
        switch (BasicInfo.mode) {
            case CLIENT:
                startClient();
                break;
            case SERVER:
                startServer();
                break;
            default:
                break;
        }

        long completeTime = System.currentTimeMillis();
        logger.sendInfo("启动完成！耗时："+(completeTime-startTime)+"毫秒！");
    }

    public static void startClient() {
        //WebAPI初始化
        BasicInfo.logger.sendInfo("正在初始化WebAPI......");
        WebBasic.initWeb();
        //验证节点及密码信息是否正确
        BasicInfo.logger.sendInfo("正在验证节点信息......");
        JSONObject data = SendVerify.send();
        if (data==null || !data.getBoolean("result")) {
            BasicInfo.logger.sendInfo("节点信息验证失败！请检测配置或确认网络连通性！");
            System.exit(0);
        }
        //命令行初始化
        CommandManager.initCommand(BasicInfo.logger,true);
        CommandManager.registerCommand(new CommandReload());
    }

    public static void startServer() {
        //读取配置文件
        BasicInfo.logger.sendInfo("正在读取节点配置文件......");
        ServerConfig.readConfigs();
        //启动HttpAPI
        BasicInfo.logger.sendInfo("正在启动HttpAPI......");
        HttpServerLoader.load();
        //命令行初始化
        CommandManager.initCommand(BasicInfo.logger,true);
        CommandManager.registerCommand(new CommandReload());
        CommandManager.registerCommand(new CommandStatus());
    }

    public static void reloadConfig() {
        BasicInfo.logger.sendInfo("正在重载配置文件......");
        BasicInfo.config = ConfigManager.getConfigObject("./StatusBox", "config.yml", "config.yml");
        BasicInfo.debug = BasicInfo.config.getBoolean("debug");
        ServerConfig.readConfigs();
        BasicInfo.logger.sendInfo("配置文件重载完成！");
    }
}
