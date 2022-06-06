package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.net.crimsonvideo.thirst.events.ChunkChangedEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!e.getFrom().getChunk().equals(e.getTo().getChunk()))
            Bukkit.getPluginManager().callEvent(new ChunkChangedEvent(e.getFrom().getChunk(),e.getTo().getChunk(), e.getPlayer()));
    }
}
