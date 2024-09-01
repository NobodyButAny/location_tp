package org.example.location_tp.command;

import org.bukkit.command.CommandExecutor;
import org.example.location_tp.util.ResultMessage;

public interface ResultExecutor extends CommandExecutor {
    ResultMessage getResultMessage();
}
