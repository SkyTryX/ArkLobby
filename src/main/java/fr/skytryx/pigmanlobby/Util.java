package fr.skytryx.pigmanlobby;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {

    public static ItemStack CreateItem(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        if(!lore.isEmpty()) itemmeta.setLore(lore);
        item.setItemMeta(itemmeta);
        return item;
    }
}
