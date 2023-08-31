package fr.skytryx.pigmanlobby.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginManager implements Listener {

    public static List<Player> LoginAwaiting = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        LoginAwaiting.add(event.getPlayer());
        event.getPlayer().addPotionEffects(Arrays.asList(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255, true),
                new PotionEffect(PotionEffectType.JUMP, 999999, 255, true),
                new PotionEffect(PotionEffectType.SLOW, 999999, 255, true)));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(!event.getMessage().equals("login") && !event.getMessage().equals("login")){
            event.setCancelled(true);
        }
    }
}
