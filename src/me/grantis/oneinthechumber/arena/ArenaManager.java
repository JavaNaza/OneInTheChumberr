package me.grantis.oneinthechumber.arena;

import me.grantis.oneinthechumber.OneInTheChumber;
import me.grantis.oneinthechumber.config.ConfigFile;
import me.grantis.oneinthechumber.utils.ChatUtils;
import me.grantis.oneinthechumber.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ArenaManager {

    public static ArrayList<Arena> arenas = new ArrayList<Arena>();

    private static ConfigFile arenaConfig = OneInTheChumber.plugin.getArenasFile();

    public static Arena createArena(String name, Location loc) {
        for(Arena a : arenas) {
            if(a.getName().equalsIgnoreCase(name)) {
                return null;
            }
        }
        Arena a = new Arena(name, loc, new HashMap<>(),1);

        arenas.add(a);

        arenaConfig.getConfig().set("Arenas." + name + ".location", LocationUtils.serializeLoc(loc));
        arenaConfig.save();
        return a;
    }

    public static void removeArena(String name, CommandSender sender) {
        Arena a = getArena(name);
        if(a == null) {
            sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Arena '" + name + "' not found !");
            return;
        }

        else {
            arenas.remove(a);
            arenaConfig.getConfig().set("Arenas." + name, null);
            arenaConfig.save();
            sender.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "Arena '" + name + "' has been removed !");
        }

    }

    public static void addSpawn(String arenaName, Location loc, Player player) {

        Arena a = getArena(arenaName);
        if(a == null) {
            player.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Arena '" + arenaName + "' not found !");
            return;
        }

        int num = a.getSpawnNumber();
        a.getSpawns().put(num, loc);
        arenaConfig.getConfig().set("Arenas." + arenaName + ".spawns." + num, LocationUtils.serializeLoc(loc));
        arenaConfig.save();
        player.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "The '" + num + "' spawn has been added to Arena '" + arenaName + "' !");
        num++;
        a.setSpawnNumber(num);

    }

    public static void removeSpawn(String arenaName, int spawnNumber, CommandSender sender) {
        Arena a = getArena(arenaName);
        if(a == null) {
            sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Arena '" + arenaName + "' not found !");
            return;
        }

        if(a.getSpawns().containsKey(spawnNumber)) {
            a.getSpawns().remove(spawnNumber);
            arenaConfig.getConfig().set("Arenas." + arenaName + ".spawns." + spawnNumber, null);
            arenaConfig.save();
            sender.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "Spawn '" + spawnNumber + "' has been removed from Arena '" + arenaName + "' !");
        }

        else {
            sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Spawn '" + spawnNumber + "' not found !");
            return;
        }
    }

    public static void loadArenas() {

        if(arenaConfig.getConfigurationSection("Arenas") == null)  arenaConfig.createSection("Arenas");

        Set<String> arenaNames = arenaConfig.getConfigurationSection("Arenas").getKeys(false);
        if(arenaNames.size() == 0) {
            return;
        } else {
            for(String arenaName : arenaNames) {
                Location loc = LocationUtils.deserializeLoc(arenaConfig.getConfig().getString("Arenas." + arenaName + ".location"));
                Map<Integer, Location> spawns = new HashMap<>();
                String spawnPath = "Arenas." + arenaName + ".spawns";
                int lastSpawnNumber = 1;
                if(arenaConfig.getConfigurationSection(spawnPath).getKeys(false).isEmpty()) {}
                else {
                    for (String spawnNumber : arenaConfig.getConfigurationSection(spawnPath).getKeys(false)) {
                        int num = Integer.parseInt(spawnNumber);
                        Location spawnsLoc = LocationUtils.deserializeLoc(arenaConfig.getConfig().getString("Arenas." + arenaName + ".spawns." + num));
                        spawns.put(num, spawnsLoc);
                        lastSpawnNumber = num;
                    }
                }
                Arena a = new Arena(arenaName, loc, spawns, lastSpawnNumber);
                arenas.add(a);
                if(!arenas.contains(a)) {
                    arenas.add(a);
                }
            }
        }
    }

    public static Arena getArena(String name) {
        for(Arena a : arenas)
            if(a.getName().equalsIgnoreCase(name))
                return a;
        return null;
    }
}


