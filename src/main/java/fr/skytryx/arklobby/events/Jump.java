package fr.skytryx.arklobby.events;

import fr.skytryx.arklobby.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
                event.getPlayer().sendMessage("§c[Jump] §bJump started");
                event.getPlayer().getInventory().setItem(8, Util.CreateItem(Material.BARRIER, "§cStop", Collections.singletonList("§cGet back to the start of the jump")));
                if(scheduler.containsKey(event.getPlayer())){
                    Bukkit.getScheduler().cancelTask(scheduler.get(event.getPlayer()));
                }
                int a = Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ArkLobby")), ()->{
                    if(jump_timer.containsKey(event.getPlayer())){
                        if(jump_timer.get(event.getPlayer()) == -1){
                            int task_id = scheduler.get(event.getPlayer());
                            scheduler.remove(event.getPlayer());
                            jump_timer.remove(event.getPlayer());
                            Bukkit.getScheduler().cancelTask(task_id);
                        } else{
                            jump_timer.put(event.getPlayer(), jump_timer.get(event.getPlayer())+1);
                            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§3Jump: §6"+ jump_timer.get(event.getPlayer()) + "§3s"));
                        }
                    }
                }, 20L, 20L);
                scheduler.put(event.getPlayer(), a);
            } else if(event.getClickedBlock().getType().equals(Material.STONE_PRESSURE_PLATE)){
                if(jump_timer.containsKey(event.getPlayer())){
                    event.getPlayer().getInventory().remove(Material.BARRIER);
                    event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, 1, 0));
                    event.getPlayer().sendMessage("§c[Jump] §bYou finished in §6"+jump_timer.get(event.getPlayer())+" §bsecondes");
                    event.getPlayer().setAllowFlight(true);
                    jump_timer.put(event.getPlayer(), -1);

                }
            }
        } else if(Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(event.getAction())){
            if(event.getItem() != null){
                if(event.getItem().getType() == Material.BARRIER && event.getItem().getItemMeta().getDisplayName().equals("§cStop")){
                    if(jump_timer.containsKey(event.getPlayer())){
                        jump_timer.put(event.getPlayer(), -1);
                        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, 1, 0));
                        event.getPlayer().sendMessage("§c[Jump] §bYou stopped the jump.");
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
