package fr.skytryx.arklobby.commands;

import fr.skytryx.arklobby.events.LoginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CommandRegister implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 2) return false;
        if(!strings[0].equals(strings[1])) return false;
        if(!(commandSender instanceof Player)) return false;

        final File loginfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ArkLobby")).getDataFolder(), "login.yml");
        final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);
        Player player = (Player) commandSender;

        if(loginconfig.get(String.valueOf(player.getUniqueId())) == null){
            player.sendMessage("§c[Login] §bEnregistrement completé. Bon jeu!");
            loginconfig.set(String.valueOf(player.getUniqueId()), strings[0]);
            player.clearActivePotionEffects();
            LoginManager.LoginAwaiting.remove(player);
            player.setAllowFlight(true);
            player.setFlying(false);
            try {
                loginconfig.save(loginfile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
