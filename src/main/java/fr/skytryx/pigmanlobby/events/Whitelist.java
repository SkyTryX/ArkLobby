package fr.skytryx.pigmanlobby.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;

public class Whitelist implements Listener {
    public static List<String> list = Arrays.asList("SkyTryX", "TIMANI", "lupino_craft", "LurmLeveling", "blastspirits", "Pinguino", "Matteo34800");

    @EventHandler
    public void wlCheck(PlayerJoinEvent event){
        if(!list.contains(event.getPlayer().getName())){
            event.getPlayer().kick(Component.text("Tu n'es pas dans la whitelist").color(TextColor.color(255, 0, 0)));
        }
    }
}
