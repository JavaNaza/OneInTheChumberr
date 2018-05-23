package me.grantis.oneinthechumber.arena;

import me.grantis.oneinthechumber.OneInTheChumber;
import me.grantis.oneinthechumber.config.ArenaConfig;
import me.grantis.oneinthechumber.utils.ChatUtils;
import me.grantis.oneinthechumber.utils.LocationUtils;
import org.bukkit.Bukkit;
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

    static ArenaConfig aConfig = new ArenaConfig(OneInTheChumber.plugin);

    public static ArrayList<Arena> arenas = new ArrayList<Arena>();

    public static Arena createArena(String name, Location loc) {
        for(Arena a : arenas) {
            if(a.getName().equalsIgnoreCase(name)) {
                return null;
            }
        }
        Arena a = new Arena(name, loc, new HashMap<Integer, Location>(),1);

        arenas.add(a);
        aConfig.getConfig().set("Arenas." + name + ".location", LocationUtils.serializeLoc(loc));
        aConfig.save();
        return a;
    }

    public static void removeArena(String name, CommandSender sender) {
        Arena a = null;
        a = getArena(name);
        if(a == null) {
            sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Arena '" + name + "' not found !");
            return;
        }

        else {
            arenas.remove(a);
            aConfig.getConfig().set("Arenas." + name, null);
            aConfig.save();
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
        aConfig.getConfig().set("Arenas." + arenaName + ".spawns." + num, LocationUtils.serializeLoc(loc));
        aConfig.save();
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
            aConfig.getConfig().set("Arenas." + arenaName + ".spawns." + spawnNumber, null);
            aConfig.save();
            sender.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "Spawn '" + spawnNumber + "' has been removed from Arena '" + arenaName + "' !");
        }

        else {
            sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Spawn '" + spawnNumber + "' not found !");
            return;
        }
    }

    public static void loadArenas() {

       ConfigurationSection section = aConfig.getConfig().getConfigurationSection("Arenas");
        System.out.println("--------------------------");
        System.out.println(section);
        System.out.println("--------------------------");
        if(section == null) {
            aConfig.getConfig().createSection("Arenas");
            System.out.println("Section created");
            //return;
        }

        //section = OneInTheChumber.getArenaConfig().getConfig().getConfigurationSection("Arenas");//NOTE
        Set<String> arenaNames = section.getKeys(false);
        if(arenaNames.size() == 0) {
            System.out.println("Arenas size");
            return;
        }
        else {
            for(String arenaName : aConfig.getConfig().getConfigurationSection("Arenas").getKeys(false)) {
                Location loc = LocationUtils.deserializeLoc(aConfig.getConfig().getString("Arenas." + arenaName + ".location"));
                Map<Integer, Location> spawns = new HashMap<Integer, Location>();
                String spawnPath = "Arenas." + arenaName + ".spawns";
                int lastSpawnNumber = 1;
                if(aConfig.getConfig().getConfigurationSection(spawnPath).getKeys(false).isEmpty()) {}
                else {
                    for (String spawnNumber : aConfig.getConfig().getConfigurationSection(spawnPath).getKeys(false)) {
                        int num = Integer.parseInt(spawnNumber);
                        Location spawnsLoc = LocationUtils.deserializeLoc(aConfig.getConfig().getString("Arenas." + arenaName + ".spawns." + num));
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


