package fr.skytryx.arklobby.events;

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

    public static ItemStack ItemCreator(Material mat, String name){
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
        player.getInventory().setItem(0, ItemCreator(Material.COMPASS, "§6Server Selector"));
        player.getInventory().setItem(1, ItemCreator(Material.BOW, "§6ArenaPvP"));
    }

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item == null) return;
        if(LoginManager.LoginAwaiting.contains(event.getPlayer())) return;
        if(item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§6Server Selector")){
            Inventory menu = Bukkit.createInventory(null, 9, "§8Servers");
            menu.addItem(ItemCreator(Material.GRASS_BLOCK, "§6MMORPG"));
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
            out.writeUTF("Connect");
            out.writeUTF(event.getCurrentItem().getItemMeta().getDisplayName().substring(2));
            player.sendPluginMessage(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ArkLobby")), "BungeeCord", out.toByteArray());
            event.setCancelled(true);
            player.sendMessage("§bJoining server "+ event.getCurrentItem().getItemMeta().getDisplayName());
        }
    }
}

