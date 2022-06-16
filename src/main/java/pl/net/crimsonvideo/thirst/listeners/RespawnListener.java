package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

public class RespawnListener implements Listener {

    public final JavaPlugin plugin;

    public RespawnListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawning(@NotNull PlayerRespawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Thirst.getAPI().hydrationAPI.setHydration(event.getPlayer(),20);
            }
        }.runTaskAsynchronously(plugin);
    }
}
