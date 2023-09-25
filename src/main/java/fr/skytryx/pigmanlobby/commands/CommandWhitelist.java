package fr.skytryx.pigmanlobby.commands;

import fr.skytryx.pigmanlobby.events.Whitelist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandWhitelist implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1){
            if(Whitelist.list.contains(strings[0])){
                Whitelist.list.remove(strings[0]);
                commandSender.sendMessage("§c[Whitelist] §bTu as enlevé §6"+strings[0]+" §bde la whitelist");
            } else {
                Whitelist.list.add(strings[0]);
                commandSender.sendMessage("§c[Whitelist] §bTu as ajouté §6"+strings[0]+" §bà la whitelist");
            }
            return true;
        }
        return false;
    }
}
