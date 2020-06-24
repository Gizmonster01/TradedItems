package me.gizmonster.tradeditems.listeners;

import me.gizmonster.tradeditems.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickItem implements Listener {
    MainClass main = MainClass.getInstance();
    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(event.getInventory() instanceof MerchantInventory)) {
            return;
        }
        List<String> list = new ArrayList<>();
        list = (List<String>) MainClass.getInstance().getConfig().getList("world_blacklist");
        if (list.contains(player.getWorld().getName())) {
            return;
        }
        // The raw slot of the villager trade's result is 2. If the click does not occur on this slot, then I will stop the process here.
        if (!(event.getRawSlot() == 2)) {
            return;
        }
        if (event.getCurrentItem() == null) {
            return;
        }
        if (MainClass.getInstance().getConfig().getBoolean("modify_emeralds.enabled") == false) {
            if (event.getCurrentItem().getType() == Material.EMERALD) {
                return;
            }
        }
        if (event.getCurrentItem() == null) {
            return;
        }
        // This might seem weird, but it was quite necessary. It would be incredibly hard if not impossible with the plugin to make it so shift clicking out of the villager inventory
        // will apply the data to every single item. To thwart such exploits, the best course of action is to make it so it cancels the trade when someone attempts to shift click. I
        // closed the inventory because it makes the villager experience go up and the trade burn out (which doesn't actually happen, but isn't refreshed until the inventory is reopened.
        if (!(event.getClick() == ClickType.LEFT)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You cannot [SHIFT] click from villager inventories"));
            event.setCancelled(true);
            new BukkitRunnable(){ public void run(){
                if (!(player.isOnline())) {
                    return;
                }
               player.closeInventory();
            }}.runTaskLater(MainClass.getInstance(), 5);
        }
        //Bukkit.broadcastMessage(event.getRawSlot() + "");
        ItemStack item = event.getCurrentItem();
        String itemDisplay = MainClass.getInstance().stringUtils.capitailizeWord(item.getType().name().replace("_", " ").toLowerCase());
        item.setItemMeta(main.itemModifier.modifiedMeta(item));
    }
}
