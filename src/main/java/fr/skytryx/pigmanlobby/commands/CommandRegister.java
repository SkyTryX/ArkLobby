package fr.skytryx.pigmanlobby.commands;

import fr.skytryx.pigmanlobby.events.LoginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class CommandRegister implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 2) return false;
        if(!strings[0].equals(strings[1])) return false;
        if(!(commandSender instanceof Player)) return false;

        final File loginfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("PigmanLobby")).getDataFolder(), "login.yml");
        final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);
        Player player = (Player) commandSender;

        if(loginconfig.get(String.valueOf(player.getUniqueId())) == null){
            player.sendMessage("§c[Login] §bYou successfully logged in!");
            loginconfig.set(String.valueOf(player.getUniqueId()), strings[1]);
            player.clearActivePotionEffects();
            LoginManager.LoginAwaiting.remove(player);
        }
        return true;
    }
}
