package me.gizmonster.tradeditems.listeners;

import me.gizmonster.tradeditems.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonPush implements Listener {

    MainClass main = MainClass.getInstance();

    @EventHandler
    public void pistonPush(BlockPistonExtendEvent event) {
        int i = 0;
        for (Block block : event.getBlocks()) {
            //Bukkit.broadcastMessage(i + " " + block.getType().toString());
            i = i + 1;
            if (main.tradedBlocks.contains(block.getLocation())) {
                //Bukkit.broadcastMessage(ChatColor.GREEN + "Villager Block Pushed");
                main.tradedBlocks.remove(block.getLocation());
                pushChangeLoc(event.getDirection(), block.getLocation());
            }
        }
    }

    @EventHandler
    public void pistonPull(BlockPistonRetractEvent event) {
        int i = 0;
        for (Block block : event.getBlocks()) {
            //Bukkit.broadcastMessage(i + " " + block.getType().toString());
            i = i + 1;
            if (main.tradedBlocks.contains(block.getLocation())) {
                //Bukkit.broadcastMessage(ChatColor.YELLOW + "Villager Block Pulled");
                main.tradedBlocks.remove(block.getLocation());
                pullChangeLoc(event.getDirection(), block.getLocation());
            }
        }
    }

    public void pushChangeLoc(BlockFace face, Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        switch(face) {
            case UP:
                y = y + 1;
                break;
            case DOWN:
                y = y - 1;
                break;
            case EAST:
                x = x + 1;
                break;
            case WEST:
                x = x - 1;
                break;
            case NORTH:
                z = z - 1;
                break;
            case SOUTH:
                z = z + 1;
                break;
        }
        main.tradedBlocks.add(new Location(loc.getWorld(), x, y , z));
        //Bukkit.broadcastMessage("Block pushed " + ChatColor.RED + face.toString() + ChatColor.WHITE + " was moved to " + ChatColor.GREEN + x + " " + y + " " + z);
    }

    public void pullChangeLoc(BlockFace face, Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        switch(face) {
            case UP:
                y = y + 1;
                break;
            case DOWN:
                y = y - 1;
                break;
            case EAST:
                x = x + 1;
                break;
            case WEST:
                x = x - 1;
                break;
            case NORTH:
                z = z - 1;
                break;
            case SOUTH:
                z = z + 1;
                break;
        }
        main.tradedBlocks.add(new Location(loc.getWorld(), x, y , z));
        //Bukkit.broadcastMessage("Block pulled " + ChatColor.RED + face.toString() + ChatColor.WHITE + " was moved to " + ChatColor.GREEN + x + " " + y + " " + z);
    }
}
