package org.example.location_tp.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.example.location_tp.Landmark;
import org.example.location_tp.LocationTP;

import java.util.List;
import java.util.Objects;

/*
Landmark(pos, name, authors[]?, description?)

/landmark {subcommand}
 - list {authors}?
    lists all locations registered, filtered by authors

 - create {name} {authors?}...
    registers a new location at players position
    if authors is not stated assumes caller is authoring
    no duplicate of '' names!

 - delete {name}

 - describe {name} {description}

 - modify {location_name} {field} {new_value}
    cannot assign used or '' name

- tp {name}
    teleports user to desired location
    cannot tp to invalid location
 */
public class LandmarkDispatcher implements TabExecutor {
    public static final List<String> cmdlets = List.of(
            "create",
            "list",
            "tp",
            "delete"
    );

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (strings.length < 1) {
            player.sendMessage(ChatColor.YELLOW + "Недостаточно аргументов!");
            return false;
        }
        // Надо ли тащить валидацию внутрь методов, чтобы было красиво?
        // или вообще куда-то её в другое место выащить?
        switch (strings[0]) {
            case "list" -> player.sendMessage(list(player).resultMessage); // это красиво
            case "create" -> {
                if (strings.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "Не указано название!");
                    return false;
                }
                var result = create(player, strings[1], List.of(strings).subList(2, strings.length));
                player.sendMessage(result.resultMessage);
            }
            case "tp" -> {
                if (strings.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "Не указано место!");
                    return false;
                }
                player.sendMessage(tp(player, strings[1]).resultMessage);
            }
            case "delete" -> {
                if(strings.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "Не указано название!");
                    return false;
                }
                player.sendMessage(delete(strings[1]).resultMessage);
            }
            default -> {
                player.sendMessage(ChatColor.YELLOW + strings[0] + " не является верным командлетом!");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return switch (strings.length) {
//            case 1 -> cmdlets.stream().filter(variant -> variant.matches(strings[0])).toList();
            default -> cmdlets;
        };
    }

    public Result list(Player player) {
        var landmarkNames = LocationTP.store.getKeys(false);
        if (landmarkNames.isEmpty())
            return Result.NOTHING;

        for (var name : landmarkNames) {
            var landmark = Landmark.load(LocationTP.store, name);
            if (Objects.isNull(landmark)) continue;
            player.sendMessage("\n" + ChatColor.YELLOW + landmark.chatString());
        }

        return Result.SILENT_SUCCESS;
    }

    public Result create(Player player, String name, List<String> authors) {
        if (name == null || name.isEmpty()) return Result.NO_NAME;
        if (LocationTP.store.contains(name)) return Result.NAME_OCCUPIED;
        authors = (authors == null || authors.isEmpty()) ? List.of(player.getDisplayName()) : authors;

        try {
            new Landmark(player.getLocation(), name, authors).save(LocationTP.store);
            LocationTP.saveStore();
        } catch (Exception e) {
            return Result.FAIL;
        }
        return Result.SUCCESS;
    }

    public Result tp(Player player, String landmarkName) {
        if (!LocationTP.store.contains(landmarkName)) return Result.NO_NAME;
        try {
            var landmark = Landmark.load(LocationTP.store, landmarkName);
            player.teleport(landmark.pos());
        } catch (Exception e) {
            return Result.FAIL;
        }
        return Result.SUCCESS;
    }

    public Result delete(String name) {
        if (!LocationTP.store.contains(name)) return Result.NO_NAME;
        try {
            LocationTP.store.set(name, null);
            return Result.SUCCESS;
        } catch (Exception e) {
            return Result.FAIL;
        }
    }

    // Возможно перебор, но мне нравится сама идея
    public enum Result {
        SILENT_SUCCESS(""),
        SUCCESS(ChatColor.GREEN + "Успех!"),
        NOTHING(ChatColor.BLUE + "Пусто!"),
        NAME_OCCUPIED(ChatColor.YELLOW + "Указанное имя уже занято!"),
        NO_NAME(ChatColor.YELLOW + "Отсутствует или указанно пустое имя!"),
        FAIL(ChatColor.RED + "Не удалось совершить операцию!");

        public final String resultMessage;

        Result(String text) {
            this.resultMessage = text;
        }
    }
}
