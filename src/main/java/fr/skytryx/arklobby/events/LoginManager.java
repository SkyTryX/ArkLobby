package fr.skytryx.arklobby.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.InetSocketAddress;
import java.util.*;

public class LoginManager implements Listener {

    public static List<Player> LoginAwaiting = new ArrayList<>();

    public static HashMap<Player, String> IPLogin = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!IPLogin.get(event.getPlayer()).equals(Objects.requireNonNull(event.getPlayer().getAddress()).getHostName())){
            LoginAwaiting.add(event.getPlayer());
            event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 1, 0.5));
            event.getPlayer().addPotionEffects(Arrays.asList(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255, true),
                    new PotionEffect(PotionEffectType.JUMP, 999999, 150, true),
                    new PotionEffect(PotionEffectType.SLOW, 999999, 255, true)));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(LoginAwaiting.contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(LoginAwaiting.contains(event.getPlayer())){
            if(!event.getMessage().startsWith("/register") && !event.getMessage().startsWith("/login")){
                event.setCancelled(true);
            }
        }
    }
}
