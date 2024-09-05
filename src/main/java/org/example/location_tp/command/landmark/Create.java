package org.example.location_tp.command.landmark;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.location_tp.Landmark;
import org.example.location_tp.LocationTP;
import org.example.location_tp.command.ResultExecutor;
import org.example.location_tp.util.ResultMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Create extends ResultExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player) || args.length < 1) return false;

        String name = args[0];
        List<String> authors = List.of(Arrays.copyOfRange(args, 1, args.length));
        authors = authors.isEmpty() ? List.of(player.getDisplayName()) : authors;

        if (name == null || name.isEmpty()) {
            this.resultMessage = ResultMessage.NO_NAME;
            return false;
        }
        if (LocationTP.store.contains(name)) {
            this.resultMessage = ResultMessage.NAME_OCCUPIED;
            return true;
        }
        try {
            new Landmark(player.getLocation(), name, authors).save(LocationTP.store);
            LocationTP.saveStore();
        } catch (Exception e) {
            this.resultMessage = ResultMessage.FAIL;
            return true;
        }

        this.resultMessage = ResultMessage.SUCCESS;
        return true;
    }
}
