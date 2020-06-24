package me.gizmonster.tradeditems.listeners;

import me.gizmonster.tradeditems.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class BreakPlaceBlock implements Listener {
    MainClass main = MainClass.getInstance();
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack blockItem;
        if (event.getHand() == EquipmentSlot.HAND) {
            blockItem = player.getInventory().getItemInMainHand();
        } else {
            blockItem = player.getInventory().getItemInOffHand();
        }
        if (!blockItem.hasItemMeta()) { return; }
        if (!(blockItem.getItemMeta().getPersistentDataContainer().has(main.tradedkey, PersistentDataType.STRING))) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        main.tradedBlocks.add(block.getLocation());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation().add(0.5, 0, 0.5);
        if (event.isCancelled()) {
            return;
        }
        World world = block.getWorld();
        if (main.tradedBlocks.contains(block.getLocation())) {
            main.tradedBlocks.remove(block.getLocation());
            event.setDropItems(false);
            ItemStack item = new ItemStack(block.getType());
            block.getDrops();
            item.setItemMeta(main.itemModifier.modifiedMeta(item));
            world.dropItemNaturally(loc, item);
        }
    }
}
