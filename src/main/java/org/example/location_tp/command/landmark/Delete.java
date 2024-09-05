package org.example.location_tp.command.landmark;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.location_tp.LocationTP;
import org.example.location_tp.command.ResultExecutor;
import org.example.location_tp.util.ResultMessage;

public class Delete extends ResultExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player) || args.length < 1) return false;

        String name = args[0];
        if (!LocationTP.store.contains(name)) {
            this.resultMessage = ResultMessage.of("$no_name").get();
            return true;
        }

        try {
            LocationTP.store.set(name, null);
            this.resultMessage = ResultMessage.of("$success").get();
        } catch (Exception e) {
            this.resultMessage = ResultMessage.of("$fail").get();
        }
        return true;
    }
}
