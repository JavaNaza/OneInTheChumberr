package me.grantis.oneinthechumber.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Arena {

    private int spawnNumber = 1;
    String name;
    Location arenaLoc;
    private Map<Integer, Location> spawns = new HashMap<Integer, Location>();
    ArrayList<Player> players = new ArrayList<Player>();

    public Arena(String name, Location arenaLoc, Map<Integer, Location> spawns, int spawnNumber) {
        this.name = name;
        this.arenaLoc = arenaLoc;
        this.spawns = spawns;
        this.spawnNumber = spawnNumber;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public String getName() {
        return name;
    }
    public Map<Integer, Location> getSpawns() {
        return this.spawns;
    }
    public int getSpawnNumber() {
        return this.spawnNumber;
    }
    public void setSpawnNumber(int spawnNumber) {
        this.spawnNumber = spawnNumber;
    }
}
