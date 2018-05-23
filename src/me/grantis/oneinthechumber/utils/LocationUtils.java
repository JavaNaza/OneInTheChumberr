package me.grantis.oneinthechumber.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String serializeLoc(Location l) {
        return l.getWorld().getName()+", "+l.getBlockX()+", "+l.getBlockY()+", "+l.getBlockZ()+", "+l.getYaw()+", "+l.getPitch();
    }

    public static Location deserializeLoc(String s) {

        String[] st = s.split(", ");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]),
                Float.parseFloat(st[4]), Float.parseFloat(st[5]));

    }

}
