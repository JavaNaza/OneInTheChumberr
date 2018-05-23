package me.grantis.oneinthechumber;

import me.grantis.oneinthechumber.arena.Arena;
import me.grantis.oneinthechumber.arena.ArenaManager;
import me.grantis.oneinthechumber.commands.PluginCommands;
import me.grantis.oneinthechumber.config.ArenaConfig;
import me.grantis.oneinthechumber.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class OneInTheChumber extends JavaPlugin {

    private ArenaConfig arenaConfig;
    public static ConfigurationSection section;

    Logger logger = Bukkit.getLogger();
    public static OneInTheChumber plugin;


    @Override
    public void onEnable() {
        plugin = this;
        loadArenaConfig();
        loadConfig();
        registerCommands();
        ConfigurationSection section = arenaConfig.getConfig().getConfigurationSection("Arenas");
        System.out.println("--------------------------");
        System.out.println(section);
        System.out.println("--------------------------");
        ArenaManager.loadArenas();
        logger.info(ChatColor.GREEN + "OneInTheChumber has been enabled !");
    }
    public void onDisable() {
        arenaConfig.load();
        arenaConfig.save();
        logger.info(ChatColor.GOLD + "OneInTheChumber has been enabled !");
    }



    public void registerCommands() {
        PluginCommands.commandListAdmin.add(ChatColor.WHITE +"/oitc reload");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc create <ARENAS NAME>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc remove <ARENA NAME>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc addspawn <ARENA>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc remove spawn <SPAWN> <ARENA NAME>");
        getCommand("oitc").setExecutor(new PluginCommands());

    }

    public void loadArenaConfig() {

        arenaConfig = new ArenaConfig(plugin);
        arenaConfig.getConfig().options().copyDefaults(true);
        arenaConfig.load();

        if(!arenaConfig.getConfig().isConfigurationSection("Arenas")) {
            arenaConfig.getConfig().createSection("Arenas");
        }

        arenaConfig.save();
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
