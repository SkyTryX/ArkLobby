package fr.skytryx.pigmanlobby.commands;

import fr.skytryx.pigmanlobby.events.LoginManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class CommandLogin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 1) return false;
        if(!(commandSender instanceof Player)) return false;

        final File loginfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("PigmanLobby")).getDataFolder(), "login.yml");
        final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);
        Player player = (Player) commandSender;

        if(loginconfig.get(String.valueOf(player.getUniqueId())) == null){
            return false;
        } else{
            if(loginconfig.get(String.valueOf(player.getUniqueId())) == strings[0]){
                player.sendMessage("§c[Login] §bYou successfully logged in!");
                player.clearActivePotionEffects();
                LoginManager.LoginAwaiting.remove(player);
            } else{
                player.kick(Component.text("Wrong Password").color(TextColor.color(255, 0, 0)));
            }
        }
        return true;
    }
}
