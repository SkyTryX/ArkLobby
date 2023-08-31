package fr.skytryx.pigmanlobby.events;

import fr.skytryx.pigmanlobby.commands.CommandBuild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyProtection implements Listener {

    @EventHandler
    public void onLobbyBreak(BlockBreakEvent event){
        if(CommandBuild.BuildMap.containsKey(event.getPlayer())){
            if(!CommandBuild.BuildMap.get(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLobbyBreak(BlockPlaceEvent event){
        if(CommandBuild.BuildMap.containsKey(event.getPlayer())){
            if(!CommandBuild.BuildMap.get(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoinBuild(PlayerJoinEvent event){
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 1, 0));
        if(!CommandBuild.BuildMap.containsKey(event.getPlayer())){
            CommandBuild.BuildMap.put(event.getPlayer(), false);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(true);
    }
}
