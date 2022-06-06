package pl.net.crimsonvideo.thirst.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ChunkChangedEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();

    private Chunk previousChunk;

    public Chunk getPreviousChunk() {
        return previousChunk;
    }

    public Chunk getNextChunk() {
        return nextChunk;
    }

    public Player getPlayer() {
        return player;
    }

    private Chunk nextChunk;
    private Player player;

    public ChunkChangedEvent(Chunk previousChunk, Chunk nextChunk, Player player) {
        this.previousChunk = previousChunk;
        this.nextChunk = nextChunk;
        this.player = player;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }
}
