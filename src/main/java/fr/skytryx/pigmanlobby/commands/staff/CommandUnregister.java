package fr.skytryx.pigmanlobby.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CommandUnregister implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 1) return false;

        final File loginfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("PigmanLobby")).getDataFolder(), "login.yml");
        final YamlConfiguration loginconfig = YamlConfiguration.loadConfiguration(loginfile);
        if(loginconfig.get(String.valueOf(Bukkit.getOfflinePlayer(strings[0]).getUniqueId())) != null){
            loginconfig.set(String.valueOf(Bukkit.getOfflinePlayer(strings[0]).getUniqueId()), null);
            commandSender.sendMessage("§c[Unregister] §bLe joueur §6"+Bukkit.getOfflinePlayer(strings[0]).getName()+" §ba bien été retiré de la database.");
            try {
                loginconfig.save(loginfile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            commandSender.sendMessage("§c[Unregister] §bLe joueur n'a pas été trouvé");
        }
        return true;
    }
}
