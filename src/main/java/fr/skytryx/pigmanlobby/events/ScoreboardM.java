package fr.skytryx.pigmanlobby.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScoreboardM implements Listener {

    Map<Player, Integer> list_scheduler = new HashMap<>();
    @EventHandler
    public void JoinScoreboard(PlayerJoinEvent event){
        int a = Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PigmanLobby")),() -> {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("Arkxia", "dummy", "Lobby Server");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.displayName(Component.text("Arkxia").color(TextColor.color(255, 165, 0)).decorate(TextDecoration.BOLD));
            objective.getScore("§7§7-----------------").setScore(5);
            objective.getScore("§bGrade: §7...").setScore(4);
            objective.getScore("§bPing: §6"+event.getPlayer().getPing()+"§bms").setScore(3);
            objective.getScore(" ").setScore(2);
            objective.getScore("§bTPS: §6"+ Math.round(Arrays.stream(Bukkit.getTPS()).sum()/3)).setScore(1);
            objective.getScore("§7-----------------").setScore(0);
            event.getPlayer().setScoreboard(scoreboard);
        }, 0L , 20L);
        list_scheduler.put(event.getPlayer(), a);
    }

    @EventHandler
    public void QuitScoreboard(PlayerQuitEvent event){
        if(list_scheduler.containsKey(event.getPlayer())){
            Bukkit.getScheduler().cancelTask(list_scheduler.get(event.getPlayer()));
        }
    }
}
