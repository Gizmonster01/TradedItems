package me.gizmonster.tradeditems.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.gizmonster.tradeditems.MainClass;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.*;
import java.util.UUID;

public class FileManager {
    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    MainClass main = MainClass.getInstance();

    public void saveFile() {

        File followersFile = new File(main.getDataFolder(), "traded-blocks.json");

        if(!followersFile.exists()) {
            followersFile.getParentFile().mkdirs();
        }
        if(!followersFile.exists()) {
            try {
                followersFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Writer writer = null;
        try {
            writer = new FileWriter(followersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(main.getTradedBlocks(), writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        File followersFile = new File(main.getDataFolder(), "traded-blocks.json");

        if(followersFile.exists()) {
            Reader reader = null;
            try {
                reader = new FileReader(followersFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            TradedBlock[] blocks = gson.fromJson(reader, TradedBlock[].class);
            for(TradedBlock tradedBlock : blocks) {
                if (tradedBlock.getWorld() == null) {
                    continue;
                }
                main.tradedBlocks.add(new Location(tradedBlock.getWorld(), tradedBlock.getX(), tradedBlock.getY(), tradedBlock.getZ()));
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
