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
            this.resultMessage = ResultMessage.INVALID_ARGUMENTS;
            return false;
        }
        String destination = args[0];
        if (!LocationTP.store.contains(destination))  {
            this.resultMessage = ResultMessage.NO_NAME;
            return false;
        }
        try {
            var landmark = Landmark.load(LocationTP.store, destination);
            player.teleport(landmark.pos());
        } catch (Exception e) {
            this.resultMessage = ResultMessage.FAIL;
            return true;
        }

        this.resultMessage = ResultMessage.SUCCESS;
        return true;
    }
}
