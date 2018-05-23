package me.grantis.oneinthechumber.utils;

import me.grantis.oneinthechumber.OneInTheChumber;
import org.bukkit.ChatColor;

public class ChatUtils {

    public static String preffix() {
        String starter = OneInTheChumber.plugin.getConfig().getString("preffix");
        starter = ColourUtils.format(starter);

        return starter;
    }

    public static String noAccess() {
        return ChatColor.RED + "You don't have access to this command !";
    }

    public static String adminPermission() {
        return "oneinthechumber.admin";
    }


}
