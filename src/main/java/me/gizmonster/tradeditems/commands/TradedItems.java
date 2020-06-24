package me.gizmonster.tradeditems.commands;

import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import me.gizmonster.tradeditems.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TradedItems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!(player.hasPermission("tradeditems.admin"))) {
            player.sendMessage(ChatColor.RED + "✘" + ChatColor.GRAY + " You gotta have that permission, pal.");
            return true;
        }
        if (args == null || args.length == 0) {
            player.sendMessage(ChatColor.GREEN + "TradedItems Commands:");
            player.sendMessage(ChatColor.GRAY + "/ti name <name format>" + ChatColor.WHITE + " " + ChatColor.ITALIC + " Supports %item%");
            player.sendMessage(ChatColor.GRAY + "/ti lore <lore line>");
            player.sendMessage(ChatColor.GRAY + "/ti blacklist");
            player.sendMessage(ChatColor.GRAY + "/ti traderify" + ChatColor.WHITE + " " + ChatColor.ITALIC + " Makes item a Traded Item");
            player.sendMessage(ChatColor.GRAY + "/ti reload");
            return true;
        }
        if (args[0].equals("reload")) {
            MainClass.getInstance().reloadConfig();
            player.sendMessage(ChatColor.GREEN + "✔" + ChatColor.GRAY + " Configuration Reloaded");
        }
        if (args[0].equals("blacklist")) {
            List<String> list = new ArrayList<>();
            list = (List<String>) MainClass.getInstance().getConfig().getList("world_blacklist");
            player.sendMessage(ChatColor.GREEN + "Blacklisted Worlds:");
            for (String world : list) {
                player.sendMessage(ChatColor.DARK_GRAY + "➤ " + ChatColor.WHITE + world);
            }
        }
        if (args[0].equals("name")) {
            StringBuilder sb = new StringBuilder(args[1]);
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "✘" + ChatColor.GRAY + " Arguments Needed.");
                return true;
            }
            for (int i = 2; i < args.length; i++) {
                sb.append(' ').append(args[i]);
            }
            MainClass.getInstance().getConfig().set("traded_items.name", sb.toString());
            MainClass.getInstance().saveConfig();
            MainClass.getInstance().reloadConfig();
            player.sendMessage(ChatColor.GREEN + "✔" + ChatColor.GRAY + "Item name format updated to:");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()));
        }
        if (args[0].equals("lore")) {
            StringBuilder sb = new StringBuilder(args[1]);
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "✘" + ChatColor.GRAY + " Arguments Needed.");
                return true;
            }
            for (int i = 2; i < args.length; i++) {
                sb.append(' ').append(args[i]);
            }
            MainClass.getInstance().getConfig().set("traded_items.lore", sb.toString());
            MainClass.getInstance().saveConfig();
            MainClass.getInstance().reloadConfig();
            player.sendMessage(ChatColor.GREEN + "✔" + ChatColor.GRAY + "Item lore line updated to:");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()));
            return true;
        }
        if (args[0].equals("traderify")) {
            if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "✘" + ChatColor.GRAY + " You need an item in your hand for that.");
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            item.setItemMeta(MainClass.getInstance().itemModifier.modifiedMeta(item));
            return true;
        }
        return false;
    }
}
