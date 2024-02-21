package fr.skytryx.arklobby.events;

import fr.skytryx.arklobby.commands.CommandWhitelist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Whitelist implements Listener {

    @EventHandler
    public void wlCheck(AsyncPlayerPreLoginEvent event){
        if(CommandWhitelist.isWhitelisted){
            if(!CommandWhitelist.list.contains(event.getName())){
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "§cYou are not whitelisted");
            }
        }
    }
}
