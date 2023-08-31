package fr.skytryx.pigmanlobby;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Util {

    public static void StainedGlass(int min, int max, Inventory inv){
        ItemStack stainedglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta imstainedglass = stainedglass.getItemMeta();
        imstainedglass.setDisplayName(" ");
        stainedglass.setItemMeta(imstainedglass);
        for(int i = min; i < max; i++){
            inv.setItem(i, stainedglass);
        }
    }


    public static ItemStack CreateItem(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        if(!lore.isEmpty()) itemmeta.setLore(lore);
        item.setItemMeta(itemmeta);
        return item;
    }
    public static void CreateRecipe(ItemStack item, List<String> shape, Map<Character, Material> map, String name){
        NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PigmanSurvie")), name);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(shape.get(0), shape.get(1), shape.get(2));
        map.forEach(recipe::setIngredient);
        Bukkit.getServer().addRecipe(recipe);
    }

    public static ItemStack CreateHead(String name, String owner){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headmeta = (SkullMeta) head.getItemMeta();
        headmeta.setOwningPlayer(Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(owner))));
        headmeta.setDisplayName("§6"+name+" §blucky block");
        head.setItemMeta(headmeta);
        return head;
    }
}
