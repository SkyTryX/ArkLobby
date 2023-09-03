package fr.skytryx.pigmanlobby.commands;

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
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (BuildMap.containsKey(player)) {
            if (BuildMap.get(player)) {
                BuildMap.put(player, false);
                player.sendMessage("§c[Build] §bTu ne peux plus §6construire");
            } else {
                BuildMap.put(player, true);
                player.sendMessage("§c[Build] §bTu peux maintenant §6construire");

            }
        } else  player.sendMessage("§c[Build] §bErreur, déco-reco!");
        return true;
    }
}
