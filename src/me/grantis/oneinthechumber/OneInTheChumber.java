package me.grantis.oneinthechumber;

import me.grantis.oneinthechumber.arena.ArenaManager;
import me.grantis.oneinthechumber.commands.PluginCommands;
import me.grantis.oneinthechumber.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class OneInTheChumber extends JavaPlugin {

    private ConfigFile arenas;
    private ConfigFile config;

    private Logger logger = Bukkit.getLogger();
    public static OneInTheChumber plugin;


    @Override
    public void onEnable() {
        plugin = this;
        this.loadFiles();
        this.registerCommands();
        ArenaManager.loadArenas();
        logger.info(ChatColor.GREEN + "OneInTheChumber has been enabled !");
    }

    public void onDisable() {
        this.reloadFiles();
        logger.info(ChatColor.GOLD + "OneInTheChumber has been enabled !");
        plugin = null;
    }



    public void registerCommands() {
        PluginCommands.commandListAdmin.add(ChatColor.WHITE +"/oitc reload");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc create <ARENAS NAME>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc remove <ARENA NAME>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc addspawn <ARENA>");
        PluginCommands.commandListAdmin.add(ChatColor.WHITE + "/oitc remove spawn <SPAWN> <ARENA NAME>");
        getCommand("oitc").setExecutor(new PluginCommands());
    }


    public void reloadFiles() {
        arenas.reload();
        config.reload();
    }

    public ConfigFile getArenasFile() {
        return arenas;
    }

    public ConfigFile getConfigFile() {
        return config;
    }

    public void loadFiles() {
        this.arenas = new ConfigFile(this, "arenas.yml");
        this.config = new ConfigFile(this, "config.yml");
        this.config.load();
        this.arenas.load();
    }

}
