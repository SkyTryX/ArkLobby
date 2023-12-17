package fr.skytryx.arklobby.events;

import fr.skytryx.arklobby.commands.CommandWhitelist;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Whitelist implements Listener {

    @EventHandler
    public void wlCheck(AsyncPlayerPreLoginEvent event){
        if(CommandWhitelist.isWhitelisted){
            if(!CommandWhitelist.list.contains(event.getPlayerProfile().getName())){
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Component.text("Tu n'es pas dans la whitelist").color(TextColor.color(255, 0, 0)));
            }
        }
    }
}
