package fr.skytryx.pigmanlobby.events;

import fr.skytryx.pigmanlobby.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ArenaManager implements Listener {
    public static List<Player> ArenaList = new ArrayList<>();
    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item == null) return;
        if(LoginManager.LoginAwaiting.contains(event.getPlayer())) return;
        if(item.getType() == Material.BOW && item.getItemMeta().getDisplayName().equals("§6ArenePvP")) {
            player.getInventory().clear();
            player.teleport(new Location(player.getWorld(), 500, 101, 500));
            event.getPlayer().setAllowFlight(false);
            ArenaList.add(player);

            ItemStack casque = new ItemStack(Material.DIAMOND_HELMET);
            ItemStack plastron = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemStack jambiere = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemStack bottes = new ItemStack(Material.DIAMOND_BOOTS);
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
            player.getInventory().setItem(8, Util.CreateItem(Material.BARRIER, "§cQuitter Arène", Collections.singletonList("§cPour revenir au lobby")));

            Arrays.asList(casque,plastron,jambiere,bottes).forEach(piece -> piece.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

            player.getInventory().setHelmet(casque);
            player.getInventory().setChestplate(plastron);
            player.getInventory().setLeggings(jambiere);
            player.getInventory().setBoots(bottes);

        } else if(item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals("§cQuitter Arène")){
            player.getInventory().clear();
            player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            player.setSaturation(20);
            player.getInventory().setItem(0, Compass.ItemCreator(Material.COMPASS, "§6Selecteur de Serveur"));
            player.getInventory().setItem(1, Compass.ItemCreator(Material.BOW, "§6ArenePvP"));player.teleport(new Location(player.getWorld(), 0.5, 1, 0.5));
            player.sendMessage("§c[ArenePvP] §bTu es maintenant au lobby");
            event.getPlayer().setAllowFlight(true);
            ArenaList.remove(player);
        }
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event){
        ArenaList.remove(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(ArenaList.contains(event.getPlayer())){
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            event.getPlayer().setHealth(Objects.requireNonNull(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            event.getPlayer().setSaturation(20);
            event.getPlayer().getInventory().setItem(0, Compass.ItemCreator(Material.COMPASS, "§6Selecteur de Serveur"));
            event.getPlayer().getInventory().setItem(1, Compass.ItemCreator(Material.BOW, "§6ArenePvP"));
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0.5, 1, 0.5));
            Bukkit.broadcastMessage("§c[ArenePvP] §6"+ Objects.requireNonNull(event.getPlayer().getKiller()).getName()+" §ba tué §6"+ event.getPlayer().getName());
            event.getPlayer().setAllowFlight(true);
            ArenaList.remove(event.getPlayer());
        }
    }
}