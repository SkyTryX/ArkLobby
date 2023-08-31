package fr.skytryx.pigmanlobby.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Objects;

public class ScoreboardM implements Listener {


    @EventHandler
    public void JoinScoreboard(PlayerJoinEvent event){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PigmanLobby")),() -> {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("Title", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);


            objective.setDisplayName("§6§lPigmanLand");
            objective.getScore("§7§7-----------------").setScore(3);
            objective.getScore("§bPseudo: §6"+event.getPlayer().getName()).setScore(2);
            if(event.getPlayer().isOp()) objective.getScore("§bGrade: §4Admin").setScore(1);
            else objective.getScore("§bGrade: §7Joueur").setScore(1);
            objective.getScore("§7-----------------").setScore(0);
            event.getPlayer().setScoreboard(scoreboard);
        }, 20L , 10L);
    }
}
