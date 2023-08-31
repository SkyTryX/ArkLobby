package fr.skytryx.pigmanlobby;

import fr.skytryx.pigmanlobby.commands.CommandBuild;
import fr.skytryx.pigmanlobby.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PigmanLobby extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Objects.requireNonNull(getCommand("build")).setExecutor(new CommandBuild());
        getServer().getPluginManager().registerEvents(new Compass(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
        getServer().getPluginManager().registerEvents(new LobbyProtection(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardM(), this);
        getServer().getPluginManager().registerEvents(new Jump(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach(player ->{
            if(player.getLocation().getBlockY() <= -10){
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 64, 0));
            }
        }), 40L, 40L);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}