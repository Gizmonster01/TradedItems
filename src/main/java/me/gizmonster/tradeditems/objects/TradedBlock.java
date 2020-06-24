package me.gizmonster.tradeditems.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class TradedBlock {

    private int x;
    private int y;
    private int z;
    private String world;

    public TradedBlock(Location loc) {
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
        this.world = loc.getWorld().getName();
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getZ() { return this.z; }
    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }
}
