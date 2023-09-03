package fr.skytryx.pigmanlobby.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;

public class DoubleJump implements Listener {

        @EventHandler
        public void setVelocity(PlayerToggleFlightEvent event) {
            Player player = event.getPlayer();
            if (!(Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(player.getGameMode()) || player.isFlying())) {
                event.setCancelled(true);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setAllowFlight(true);
                    }
                }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PigmanLobby")), 20);
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.AMBIENT, 100f, 1f);
            }
        }
    }

