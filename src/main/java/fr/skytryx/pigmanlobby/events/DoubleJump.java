package fr.skytryx.pigmanlobby.events;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

    public class DoubleJump implements Listener {
        private final Plugin plugin;
        public DoubleJump(Plugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void setFly(PlayerJoinEvent e) {
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(false);
        }

        @EventHandler
        public void setVelocity(PlayerToggleFlightEvent e) {
            Player p = e.getPlayer();
            if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.isFlying()) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
                giveFlight(p);
                p.playSound(e.getPlayer(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.AMBIENT, 100f, 1f);
            }

        }

        private void giveFlight(Player p) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setAllowFlight(true);
                }
            }.runTaskLater(plugin, 20);
        }
    }

