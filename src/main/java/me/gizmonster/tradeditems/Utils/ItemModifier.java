package me.gizmonster.tradeditems.Utils;

import me.gizmonster.tradeditems.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemModifier {

    public ItemMeta modifiedMeta(ItemStack item) {
        String itemDisplay = MainClass.getInstance().stringUtils.capitailizeWord(item.getType().name().replace("_", " ").toLowerCase());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        String name1 = MainClass.getInstance().getConfig().getString("traded_items.name");
        String name2 = ChatColor.translateAlternateColorCodes('&', name1);
        String finalName = name2.replaceAll("%item%", itemDisplay);
        meta.getPersistentDataContainer().set(MainClass.getInstance().tradedkey, PersistentDataType.STRING, "traded");
        meta.setDisplayName(finalName);
        lore.add(ChatColor.translateAlternateColorCodes('&', MainClass.getInstance().getConfig().getString("traded_items.lore")));
        meta.setLore(lore);
        return meta;
    }
}
