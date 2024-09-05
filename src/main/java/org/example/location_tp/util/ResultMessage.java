package org.example.location_tp.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public enum ResultMessage {
    SILENT_SUCCESS(""),
    SUCCESS(ChatColor.GREEN + "Успех!"),
    NOTHING(ChatColor.BLUE + "Пусто!"),
    NAME_OCCUPIED(ChatColor.YELLOW + "Указанное имя уже занято!"),
    NO_NAME(ChatColor.YELLOW + "Отсутствует или указанно пустое имя!"),
    INVALID_ARGUMENTS(ChatColor.RED + "Указан неверный набор аргументов!"),
    FAIL(ChatColor.RED + "Не удалось совершить операцию!");

    public final String resultMessage;

    ResultMessage(String text) {
        this.resultMessage = text;
    }
}

/*
    $success:
        color: "GREEN"
        ru: "Успех!"
        en: "Success!"
 */
