package org.mossmc.mosscg.StatusBox.Command;

import org.mossmc.mosscg.MossLib.Object.ObjectCommand;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;
import org.mossmc.mosscg.StatusBox.Main;

import java.util.ArrayList;
import java.util.List;

public class CommandReload extends ObjectCommand {
    @Override
    public List<String> prefix() {
        List<String> prefixList = new ArrayList<>();
        prefixList.add("reload");
        prefixList.add("reloadConfig");
        return prefixList;
    }

    @Override
    public boolean execute(String[] args, ObjectLogger logger) {
        Main.reloadConfig();
        return true;
    }
}
