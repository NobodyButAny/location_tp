package org.example.location_tp.command.landmark;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.location_tp.Landmark;
import org.example.location_tp.LocationTP;
import org.example.location_tp.command.ResultExecutor;
import org.example.location_tp.util.ResultMessage;

import java.util.Objects;

public class List implements ResultExecutor {
    private ResultMessage resultMessage;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        var landmarkNames = LocationTP.store.getKeys(false);
        if (landmarkNames.isEmpty()) {
            this.resultMessage = ResultMessage.NOTHING;
            return true;
        }

        for (var name : landmarkNames) {
            var landmark = Landmark.load(LocationTP.store, name);
            if (Objects.isNull(landmark)) continue;
            player.sendMessage("\n" + ChatColor.YELLOW + landmark.chatString());
        }

        this.resultMessage = ResultMessage.SILENT_SUCCESS;
        return true;
    }

    @Override
    public ResultMessage getResultMessage() {
        return resultMessage;
    }
}
