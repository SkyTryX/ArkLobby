package fr.skytryx.pigmanlobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandWhitelist implements CommandExecutor {

    public static List<String> list = new LinkedList<>(Arrays.asList("SkyTryX", "TIMANI", "lupino_craft", "LurmLeveling", "blastspirits", "Pinguino", "Matteo34800", "Arch_ego"));
    public static boolean isWhitelisted = true;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1){
            if(list.contains(strings[0])){
                list.remove(strings[0]);
                commandSender.sendMessage("§c[Whitelist] §bTu as enlevé §6"+strings[0]+" §bde la whitelist");
            } else {
                list.add(strings[0]);
                commandSender.sendMessage("§c[Whitelist] §bTu as ajouté §6"+strings[0]+" §bà la whitelist");
            }
            return true;
        } else if(strings.length == 0){
            if(isWhitelisted){
                isWhitelisted = false;
                commandSender.sendMessage("§c[Whitelist] §cLa whitelist a été §6désactivé");
            } else {
                isWhitelisted = true;
                commandSender.sendMessage("§c[Whitelist] §cLa whitelist a été §6activé");
            }
            return true;
        }
        return false;
    }
}
