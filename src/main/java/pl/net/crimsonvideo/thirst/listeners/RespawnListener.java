package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawning(@NotNull PlayerRespawnEvent event) {
        Thirst.getAPI().hydrationAPI.setHydration(event.getPlayer(),20);
    }
}
