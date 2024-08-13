package org.example.location_tp;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.location_tp.command.LandmarkDispatcher;

import java.io.File;
import java.io.IOException;

public final class LocationTP extends JavaPlugin {
    public static YamlConfiguration store;
    private static File storeFile;

    @Override
    public void onEnable() {
        storeFile = new File(getDataFolder(), "store.yml");
        store = YamlConfiguration.loadConfiguration(storeFile);

        getCommand("landmark").setExecutor(new LandmarkDispatcher());
        getCommand("landmark").setTabCompleter(new LandmarkDispatcher());
    }

    @Override
    public void onDisable() {
        try {
            store.save(storeFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save store.yml!", e);
        }
    }

    public static void saveStore() throws IOException {
        store.save(storeFile);
    }
}
