package fr.skytryx.pigmanlobby.events;

import fr.skytryx.pigmanlobby.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class Jump implements Listener {

    public static Map<Player, Integer> jump_timer = new HashMap<>();
    Map<Player, Integer> scheduler = new HashMap<>();
    @EventHandler
    public void PPPress(PlayerInteractEvent event){
        if(event.getAction().equals(Action.PHYSICAL)){
            if(Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)){
                jump_timer.put(event.getPlayer(), 0);
                event.getPlayer().setAllowFlight(false);
                event.getPlayer().sendMessage("§c[Jump] §bLe jump commence, bonne chance!");
                event.getPlayer().getInventory().setItem(8, Util.CreateItem(Material.BARRIER, "§cArreter le jump", Collections.singletonList("§cPour revenir au début du jump")));
                if(scheduler.containsKey(event.getPlayer())){
                    Bukkit.getScheduler().cancelTask(scheduler.get(event.getPlayer()));
                }
                int a = Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PigmanLobby")), ()->{
                    if(jump_timer.containsKey(event.getPlayer())){
                        if(jump_timer.get(event.getPlayer()) == -1){
                            int task_id = scheduler.get(event.getPlayer());
                            scheduler.remove(event.getPlayer());
                            jump_timer.remove(event.getPlayer());
                            Bukkit.getScheduler().cancelTask(task_id);
                        } else{
                            jump_timer.put(event.getPlayer(), jump_timer.get(event.getPlayer())+1);
                        }
                    }
                }, 20L, 20L);
                scheduler.put(event.getPlayer(), a);
            } else if(event.getClickedBlock().getType().equals(Material.STONE_PRESSURE_PLATE)){
                if(jump_timer.containsKey(event.getPlayer())){
                    event.getPlayer().getInventory().remove(Material.BARRIER);
                    event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, 1, 0));
                    event.getPlayer().sendMessage("§c[Jump] §bTu as finit le jump en §6"+jump_timer.get(event.getPlayer())+" §bsecondes");
                    event.getPlayer().setAllowFlight(true);
                    jump_timer.put(event.getPlayer(), -1);

                }
            }
        } else if(Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(event.getAction())){
            if(event.getItem() != null){
                if(event.getItem().getType() == Material.BARRIER && event.getItem().getItemMeta().getDisplayName().equals("§cArreter le jump")){
                    if(jump_timer.containsKey(event.getPlayer())){
                        jump_timer.put(event.getPlayer(), -1);
                        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, 1, 0));
                        event.getPlayer().sendMessage("§c[Jump] §bTu as arrêté le jump.");
                        event.getPlayer().setAllowFlight(true);
                        event.getPlayer().getInventory().remove(Material.BARRIER);
                    }
                }
            }
        }
    }
    @EventHandler
    public void ifLeaving(PlayerQuitEvent event){
        if(jump_timer.containsKey(event.getPlayer())){
            jump_timer.put(event.getPlayer(), -1);
        }
    }
}
