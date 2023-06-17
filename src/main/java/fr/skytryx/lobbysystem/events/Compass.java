package fr.skytryx.lobbysystem.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class Compass implements Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta metacompass  = compass.getItemMeta();
        metacompass.setDisplayName("§6Server Selector");
        compass.setItemMeta(metacompass);
        player.getInventory().setItem(4, compass);
    }

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item == null) return;
        if(item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§6Server Selector")){
            Inventory menu = Bukkit.createInventory(null, 9);
            player.openInventory(menu);
        }
    }
}

