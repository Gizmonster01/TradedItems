package me.gizmonster.tradeditems;

import com.sun.prism.PixelSource;
import me.gizmonster.tradeditems.Utils.ItemModifier;
import me.gizmonster.tradeditems.Utils.StringUtils;
import me.gizmonster.tradeditems.commands.TradedItems;
import me.gizmonster.tradeditems.listeners.*;
import me.gizmonster.tradeditems.objects.FileManager;
import me.gizmonster.tradeditems.objects.TradedBlock;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class MainClass extends JavaPlugin {

    private static MainClass main;
    public StringUtils stringUtils;
    public FileConfiguration config;
    public ItemModifier itemModifier;
    public FileManager file;

    public NamespacedKey tradedkey = new NamespacedKey(this, "traded");
    public List<Location> tradedBlocks = new ArrayList<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.config = getConfig();
        System.out.println("|");
        System.out.println("|");
        System.out.println("|            TradedItems has booted up");
        System.out.println("|");
        System.out.println("|     By gizmonster - For Semitart - Version 1.0");
        this.main = this;
        this.stringUtils = new StringUtils();
        this.itemModifier = new ItemModifier();
        this.file = new FileManager();
        getServer().getPluginManager().registerEvents(new InventoryClickItem(), this);
        getServer().getPluginManager().registerEvents(new BreakPlaceBlock(), this);
        getServer().getPluginManager().registerEvents(new StonecutterUse(), this);
        getServer().getPluginManager().registerEvents(new OnCraftItem(), this);
        getServer().getPluginManager().registerEvents(new PistonPush(), this);
        this.getCommand("tradeditems").setExecutor((CommandExecutor) new TradedItems());
        file.loadFile();
    }


    public static MainClass getInstance() {
        return main;
    }

    public List<TradedBlock> getTradedBlocks() {
        List<TradedBlock> blocks = new ArrayList<>();

        for (Location location : tradedBlocks) {
            TradedBlock tBlock = new TradedBlock(location);
            blocks.add(tBlock);
        }
        return blocks;
    }

    @Override
    public void onDisable() {
        file.saveFile();
    }

}
