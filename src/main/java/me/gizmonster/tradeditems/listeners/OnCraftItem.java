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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class OnCraftItem implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        CraftingInventory craftingInventory = event.getInventory();
        ItemStack[] items = craftingInventory.getContents();
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (!(item.hasItemMeta())) {
                continue;
            }
            if (item.getItemMeta().getPersistentDataContainer().has(MainClass.getInstance().tradedkey, PersistentDataType.STRING)) {
                if (!(event.getClick() == ClickType.LEFT)) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You cannot [SHIFT] click traded items from crafting interfaces"));
                    event.setCancelled(true);
                    new BukkitRunnable(){ public void run(){
                        if (!(player.isOnline())) {
                            return;
                        }
                        player.closeInventory();
                    }}.runTaskLater(MainClass.getInstance(), 5);
                }
                ItemStack result = craftingInventory.getResult();
                result.setItemMeta(MainClass.getInstance().itemModifier.modifiedMeta(result));
                event.setCurrentItem(result);
                break;
            }
        }
    }
}
