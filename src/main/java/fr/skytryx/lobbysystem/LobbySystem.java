package fr.skytryx.lobbysystem;

import fr.skytryx.lobbysystem.commands.CommandBuild;
import fr.skytryx.lobbysystem.events.Compass;
import fr.skytryx.lobbysystem.events.DoubleJump;
import fr.skytryx.lobbysystem.events.LobbyProtection;
import fr.skytryx.lobbysystem.events.ScoreboardM;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LobbySystem extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Objects.requireNonNull(getCommand("build")).setExecutor(new CommandBuild());
        getServer().getPluginManager().registerEvents(new Compass(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
        getServer().getPluginManager().registerEvents(new LobbyProtection(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardM(), this);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}