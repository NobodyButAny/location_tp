package org.example.location_tp.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dispatcher implements CommandExecutor {
    private HashMap<String, CommandExecutor> routes;

    public Dispatcher() {
        this.routes = new HashMap<>();
    }

    public Dispatcher add(CommandExecutor route, String name) {
        routes.put(name, route);
        return this;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length < 1 || !routes.containsKey(args[0])) return false;
        var executor = routes.get(args[0]);
        var flag = executor.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        if (executor instanceof ResultExecutor resultExecutor) {
            sender.sendMessage(resultExecutor.getResultMessage().resultMessage);
        }
        return flag;
    }
}
