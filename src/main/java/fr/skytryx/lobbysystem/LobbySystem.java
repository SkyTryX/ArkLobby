package fr.skytryx.lobbysystem;

import fr.skytryx.lobbysystem.events.Compass;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbySystem extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Compass(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
