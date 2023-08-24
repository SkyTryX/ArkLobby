package fr.skytryx.lobbysystem.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class Compass implements Listener {

    public ItemStack ItemCreator(Material mat, String name){
        ItemStack item = new ItemStack(mat);
        ItemMeta itemmeta  = item.getItemMeta();
        itemmeta.setDisplayName(name);
        item.setItemMeta(itemmeta);
        return item;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemCreator(Material.COMPASS, "§6Server Selector"));
    }

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item == null) return;
        if(item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§6Server Selector")){
            Inventory menu = Bukkit.createInventory(null, 9, "§8Servers");
            menu.addItem(ItemCreator(Material.GRASS_BLOCK, "§6SMP"));
            menu.addItem(ItemCreator(Material.RED_BED, "§6Bedwars"));
            menu.addItem(ItemCreator(Material.DIAMOND_ORE, "§c???"));
            menu.addItem(ItemCreator(Material.CRAFTING_TABLE, "§6Test"));
            player.openInventory(menu);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getClickedInventory() == null) return;
        if(event.getCurrentItem() == null) return;
        if(event.getView().getTitle().equals("§8Servers")){
            Player player = (Player) event.getWhoClicked();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            Bukkit.getMessenger().registerOutgoingPluginChannel(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("LobbySystem")), "BungeeCord");
            out.writeUTF("Connect");
            out.writeUTF(event.getCurrentItem().getItemMeta().getDisplayName().substring(2));
            player.sendPluginMessage(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("LobbySystem")), "BungeeCord", out.toByteArray());
            event.setCancelled(true);
            player.sendMessage("§bSending you to "+ event.getCurrentItem().getItemMeta().getDisplayName());
        }
    }
}

