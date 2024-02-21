package fr.skytryx.arklobby.commands;

import fr.skytryx.arklobby.events.LoginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class CommandLogin implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 1) return false;
        if(!(commandSender instanceof Player player)) return false;

        final File loginfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ArkLobby")).getDataFolder(), "login.yml");
        final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);

        if(loginconfig.get(String.valueOf(player.getUniqueId())) == null){
            return false;
        } else{
            if(Objects.equals(loginconfig.getString(String.valueOf(player.getUniqueId())), strings[0])){
                player.sendMessage("§c[Login] §bCorrect password, have fun!");
                for (PotionEffect effect : player.getActivePotionEffects())
                    player.removePotionEffect(effect.getType());
                LoginManager.LoginAwaiting.remove(player);
                LoginManager.IPLogin.put(player.getName(), Objects.requireNonNull(player.getAddress()).getHostName());
                player.setAllowFlight(true);
                player.setFlying(false);
            } else{
                player.kickPlayer("§cWrong password");
            }
        }
            return true;
    }
}

