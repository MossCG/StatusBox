package org.mossmc.mosscg.StatusBox.Command;

import org.mossmc.mosscg.MossLib.Object.ObjectCommand;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;
import org.mossmc.mosscg.StatusBox.BasicInfo;
import org.mossmc.mosscg.StatusBox.Http.HttpWatchDog;
import org.mossmc.mosscg.StatusBox.Main;

import java.util.ArrayList;
import java.util.List;

public class CommandStatus extends ObjectCommand {
    @Override
    public List<String> prefix() {
        List<String> prefixList = new ArrayList<>();
        prefixList.add("status");
        prefixList.add("statistic");
        return prefixList;
    }

    @Override
    public boolean execute(String[] args, ObjectLogger logger) {
        BasicInfo.logger.sendInfo("==========================");
        BasicInfo.logger.sendInfo("StatusBox "+BasicInfo.version+" 统计信息");
        BasicInfo.logger.sendInfo("累计请求数："+ HttpWatchDog.requestCountAll);
        BasicInfo.logger.sendInfo("每秒请求数："+ HttpWatchDog.lastRequestPerSecond);
        BasicInfo.logger.sendInfo("==========================");
        return true;
    }
}
