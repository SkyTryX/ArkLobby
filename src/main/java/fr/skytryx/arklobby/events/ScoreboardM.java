package fr.skytryx.arklobby.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Objects;

public class ScoreboardM implements Listener {

    public HashMap<Player, Integer> score_list = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void join_sb(PlayerJoinEvent event){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ArkLobby")), () ->{
            Scoreboard sb = Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getNewScoreboard();
            Objective obj = sb.registerNewObjective("Scoreboard", "Dummy");

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§b§lArkxia §f§l- §3§lLobby");

            Team rank = sb.registerNewTeam("r-"+event.getPlayer().getUniqueId().toString().substring(0, 12));
            rank.addEntry("§1");
            rank.setPrefix("§3Rank: ...");

            Team ping = sb.registerNewTeam("p-"+event.getPlayer().getUniqueId().toString().substring(0, 12));
            ping.addEntry("§2");
            ping.setPrefix("§3Ping: ");
            ping.setSuffix("§b"+(event.getPlayer().getPing()+"ms"));


            obj.getScore("§7-------------------").setScore(4);
            obj.getScore("§1").setScore(3);
            obj.getScore("§2").setScore(2);
            obj.getScore("§7§7-------------------").setScore(1);
            obj.getScore("§6arkxia.ddns.net").setScore(0);


            event.getPlayer().setScoreboard(sb);


            int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ArkLobby")), ()-> ping.setSuffix("§b"+(event.getPlayer().getPing()+"ms")), 20L, 20L);

            score_list.put(event.getPlayer(), id);
        }, 20L);
    }

    @EventHandler
    public void onleavesb(PlayerQuitEvent event){
        if(score_list.containsKey(event.getPlayer())){
            Bukkit.getScheduler().cancelTask(score_list.get(event.getPlayer()));
        }
    }
}
