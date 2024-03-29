package fr.skytryx.arklobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandBuild implements CommandExecutor {

    public static Map<Player, Boolean> BuildMap = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (BuildMap.containsKey(player)) {
            if (BuildMap.get(player)) {
                BuildMap.put(player, false);
                player.sendMessage("§c[Build] §bYou can no longer §6build");
            } else {
                BuildMap.put(player, true);
                player.sendMessage("§c[Build] §bYou can now §6build");

            }
        } else  player.sendMessage("§c[Build] §bError, please reconnect!");
        return true;
    }
}
