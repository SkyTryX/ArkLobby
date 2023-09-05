package fr.skytryx.pigmanlobby.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager implements Listener {
    public static List<Player> ArenaList = new ArrayList<>();
    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item == null) return;
        if(LoginManager.LoginAwaiting.contains(event.getPlayer())) return;
        if(item.getType() == Material.DIAMOND_SWORD && item.getItemMeta().getDisplayName().equals("§6Arene de combat"));
        player.teleport(new Location(player.getWorld(), 500, 101, 500));
        ArenaList.add(player);
        // stuff de combat
            // epée diams
            ItemStack épéeDiamant = new ItemStack(Material.DIAMOND_SWORD);
            player.getInventory().setItem(0, épéeDiamant);
            // full diams enchant
            ItemStack casqueDiamant = new ItemStack(Material.DIAMOND_HELMET);
            casqueDiamant.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            player.getInventory().setItem(103, casqueDiamant);

            ItemStack plastronDiamant = new ItemStack(Material.DIAMOND_CHESTPLATE);
            plastronDiamant.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            player.getInventory().setItem(102, plastronDiamant);

            ItemStack jambièresDiamant = new ItemStack(Material.DIAMOND_LEGGINGS);
            jambièresDiamant.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            player.getInventory().setItem(101, jambièresDiamant);

            ItemStack bottesDiamant = new ItemStack(Material.DIAMOND_BOOTS);
            bottesDiamant.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            player.getInventory().setItem(100, bottesDiamant);
            // steak
            ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
            player.getInventory().setItem(7, steak);


    }
}
// 1 definir la zone et le spawn point (500 101 500)
// 2 creer l'item et la fonction de tp
// 3 rescencer les joueur present
// 4 activer les degats
