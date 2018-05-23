package me.grantis.oneinthechumber.commands;

import me.grantis.oneinthechumber.OneInTheChumber;
import me.grantis.oneinthechumber.arena.Arena;
import me.grantis.oneinthechumber.arena.ArenaManager;
import me.grantis.oneinthechumber.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PluginCommands implements CommandExecutor {

    //Config objects here:
   public static List<String> commandListPlayers = new ArrayList<String>();
   public static List<String> commandListAdmin = new ArrayList<String>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

            if (commandLabel.equalsIgnoreCase("oitc")) {
                    if(args.length == 0 && args.length < 1) {
                        if(sender.hasPermission(ChatUtils.adminPermission())) {
                          for(String s : commandListAdmin) {
                              sender.sendMessage(ChatUtils.preffix() + s);
                          }
                        }
                    }
                    else  if (args.length == 1 && args.length < 2) {
                        if (args[0].equalsIgnoreCase("reload")) {
                            if(sender.hasPermission(ChatUtils.adminPermission())) {

                                OneInTheChumber.plugin.reloadFiles();

                                sender.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "Configs has been reloaded !");
                            }
                            else {
                                sender.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                            }
                        }
                        else if (args[0].equalsIgnoreCase("remove")) {
                            if(sender.hasPermission(ChatUtils.adminPermission())) {
                                sender.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc remove <ARENA NAME>");
                                sender.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc remove spawn <SPAWN> <ARENA NAME>");
                            }
                            else {
                                sender.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                            }
                        }
                    }
                    else if (args.length == 2 && args.length < 3) {
                        if (args[0].equalsIgnoreCase("remove")) {

                            if (sender.hasPermission(ChatUtils.adminPermission())) {

                                if (args[1].equalsIgnoreCase("spawn")) {
                                    sender.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc remove spawn <SPAWN> <ARENA NAME>");
                                } else {
                                    ArenaManager.removeArena(args[1], sender);
                                }
                            } else {
                                sender.hasPermission(ChatUtils.preffix() + ChatUtils.noAccess());
                            }
                        }
                    }

                    else if(args.length == 3 && args.length < 4) {
                        if(args[0].equalsIgnoreCase("remove")) {
                            if(sender.hasPermission(ChatUtils.adminPermission())) {
                                if(args[1].equalsIgnoreCase("spawn")) {
                                    try {
                                        int num = Integer.parseInt(args[2]);
                                        sender.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc remove spawn <SPAWN> <ARENA NAME>");
                                    }catch(NumberFormatException e) {
                                        sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Can not use letters to find spawn !");
                                        }
                                    }
                            }
                            else {
                                sender.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                            }
                        }
                    }

                    else if(args.length == 4 && args.length < 5) {
                        if(args[0].equalsIgnoreCase("remove")) {
                            if(sender.hasPermission(ChatUtils.adminPermission())) {
                                if(args[1].equalsIgnoreCase("spawn")) {
                                    try {
                                        int num = Integer.parseInt(args[2]);
                                        String arenaName = args[3];
                                        ArenaManager.removeSpawn(arenaName, num, sender);
                                    }catch(NumberFormatException e) {
                                        sender.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Can not use letters to find spawn !");
                                    }
                                }
                            }
                            else {
                                sender.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                            }
                        }
                    }
            }

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (commandLabel.equalsIgnoreCase("oitc")) {
                if (args.length == 1 && args.length < 2) {
                    //ArenaCommands
                    if(player.hasPermission(ChatUtils.adminPermission())) {
                        if (args[0].equalsIgnoreCase("create")) {
                            player.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc create <ARENAS NAME>");
                        }
                        else if(args[0].equalsIgnoreCase("addspawn")) {
                            player.sendMessage(ChatUtils.preffix() + ChatColor.WHITE + "/oitc addspawn <ARENA>");
                        }
                    }
                    else {
                        player.sendMessage(ChatUtils.preffix() + ChatColor.RED + "You don't have access to this command !");
                    }
                }
                else if (args.length == 2 && args.length < 3) {
                    //ArenaCommands
                    if(args[0].equalsIgnoreCase("addspawn")) {
                        if(player.hasPermission(ChatUtils.adminPermission())) {
                            ArenaManager.addSpawn(args[1], player.getLocation(), player);
                        }
                        else {
                            player.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                        }
                    }

                    if (args[0].equalsIgnoreCase("create")) {
                        if(player.hasPermission(ChatUtils.adminPermission())) {
                            Arena a = ArenaManager.createArena(args[1], player.getLocation());
                            if (a == null) {
                                player.sendMessage(ChatUtils.preffix() + ChatColor.RED + "ERROR: Arena '" + args[1] + "' already exists !");
                            } else {
                                player.sendMessage(ChatUtils.preffix() + ChatColor.GREEN + "Arena '" + args[1] + "' successfully created !");
                            }
                        }
                        else {
                            player.sendMessage(ChatUtils.preffix() + ChatUtils.noAccess());
                        }

                    }
                }
            }
        }
        return true;
    }
}