package me.gizmonster.tradeditems.listeners;

import me.gizmonster.tradeditems.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecutterInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class StonecutterUse implements Listener {

    @EventHandler
    public void onCutStone(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(event.getInventory() instanceof StonecutterInventory)) {
            return;
        }
        if (!(event.getRawSlot() == 1)) {
            return;
        }
        if (!(event.getInventory().getItem(0)).getItemMeta().getPersistentDataContainer().has(MainClass.getInstance().tradedkey, PersistentDataType.STRING)) {
            return;
        }
        if (!(event.getClick() == ClickType.LEFT)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You cannot [SHIFT] click from the stonecutter inventory"));
            event.setCancelled(true);
            new BukkitRunnable(){ public void run(){
                if (!(player.isOnline())) {
                    return;
                }
                player.closeInventory();
            }}.runTaskLater(MainClass.getInstance(), 5);
        }
        ItemStack item = event.getCurrentItem();
        item.setItemMeta(MainClass.getInstance().itemModifier.modifiedMeta(item));
    }
}
