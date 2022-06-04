package pl.net.crimsonvideo.thirst.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HydrationChangedEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    protected boolean isCancelled = false;

    private final Player player;
    private float change;

    public HydrationChangedEvent(Player player, float change) {
        this.player = player;
        this.change = change;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}
