package fr.skytryx.arklobby;

import fr.skytryx.arklobby.commands.CommandBuild;
import fr.skytryx.arklobby.commands.CommandLogin;
import fr.skytryx.arklobby.commands.CommandRegister;
import fr.skytryx.arklobby.commands.CommandWhitelist;
import fr.skytryx.arklobby.commands.staff.CommandUnregister;
import fr.skytryx.arklobby.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class ArkLobby extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Objects.requireNonNull(getCommand("build")).setExecutor(new CommandBuild());
        Objects.requireNonNull(getCommand("register")).setExecutor(new CommandRegister());
        Objects.requireNonNull(getCommand("login")).setExecutor(new CommandLogin());
        Objects.requireNonNull(getCommand("unregister")).setExecutor(new CommandUnregister());
        Objects.requireNonNull(getCommand("whitelist")).setExecutor(new CommandWhitelist());

        getServer().getPluginManager().registerEvents(new LoginManager(), this);
        getServer().getPluginManager().registerEvents(new Compass(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
        getServer().getPluginManager().registerEvents(new LobbyProtection(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardM(), this);
        getServer().getPluginManager().registerEvents(new Jump(), this);
        getServer().getPluginManager().registerEvents(new ArenaManager(), this);
        getServer().getPluginManager().registerEvents(new Whitelist(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach(player ->{
            if(player.getLocation().getBlockY() <= -10){
                player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 64, 0.5));
            }
        }), 40L, 40L);

        final File loginfile = new File(this.getDataFolder(), "login.yml");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> LoginManager.LoginAwaiting.forEach(player ->{
            final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);
            if(loginconfig.get(String.valueOf(player.getUniqueId())) == null){
                player.sendMessage("§cEnregistre toi avec /register <password> <password>");
                player.sendMessage("§4Veuillez utiliser un mots de passe unique étant donné qu'ils ne sont pour l'instant pas sécurisés/encryptés");
            } else player.sendMessage("§cConnecte toi avec /login <password>");
        }), 0L, 100L);
        Bukkit.getLogger().info("[ArkLobby] Plugin Enabled");
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        Bukkit.getLogger().info("[ArkLobby] Plugin Disabled");
    }
}