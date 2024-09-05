package org.example.location_tp.command.landmark;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.location_tp.Landmark;
import org.example.location_tp.LocationTP;
import org.example.location_tp.command.ResultExecutor;
import org.example.location_tp.util.ResultMessage;

public class Teleport extends ResultExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        if(args.length < 1) {
            this.resultMessage = ResultMessage.of("$invalid_arguments").get();
            return false;
        }
        String destination = args[0];
        if (!LocationTP.store.contains(destination))  {
            this.resultMessage = ResultMessage.of("$no_name").get();
            return false;
        }
        try {
            var landmark = Landmark.load(LocationTP.store, destination);
            player.teleport(landmark.pos());
        } catch (Exception e) {
            this.resultMessage = ResultMessage.of("$fail").get();
            return true;
        }

        this.resultMessage = ResultMessage.of("$success").get();
        return true;
    }
}
