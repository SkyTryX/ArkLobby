package fr.skytryx.arklobby.events;

import fr.skytryx.arklobby.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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
        if(item.getType() == Material.BOW && Objects.requireNonNull(item.getItemMeta()).getDisplayName().equals("§6ArenaPvP")) {
            player.getInventory().clear();
            player.teleport(new Location(player.getWorld(), 500, 101, 500));
            event.getPlayer().setAllowFlight(false);
            ArenaList.add(player);

            if(Jump.jump_timer.containsKey(player)) {
                Jump.jump_timer.put(player, -1);
            }
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

        } else if(item.getType() == Material.BARRIER && Objects.requireNonNull(item.getItemMeta()).getDisplayName().equals("§cLeave Arena")){
            player.getInventory().clear();
            player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            player.setSaturation(20);
            player.setFoodLevel(20);
            player.getInventory().setItem(0, Compass.ItemCreator(Material.COMPASS, "§6Server Selector"));
            player.getInventory().setItem(1, Compass.ItemCreator(Material.BOW, "§6ArenaPvP"));player.teleport(new Location(player.getWorld(), 0.5, 1, 0.5));
            player.sendMessage("§c[ArenaPvP] §bYou are now in the lobby");
            event.getPlayer().setAllowFlight(true);
            ArenaList.remove(player);
        }
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event){
        ArenaList.remove(event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player && ArenaList.contains(player)){
            if(event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);
                player.getInventory().clear();
                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                player.setSaturation(20);
                player.setFoodLevel(20);
                player.getInventory().setItem(0, Compass.ItemCreator(Material.COMPASS, "§6Server Selector"));
                player.getInventory().setItem(1, Compass.ItemCreator(Material.BOW, "§6ArenaPvP"));
                player.teleport(new Location(player.getWorld(), 0.5, 64, 0.5));
                Bukkit.broadcastMessage("§c[ArenaPvP] §6" + Objects.requireNonNull(player.getKiller()).getName() + " §bkilled §6" + player.getName());
                player.setAllowFlight(true);
                ArenaList.remove(player);
            }
        }
    }
}