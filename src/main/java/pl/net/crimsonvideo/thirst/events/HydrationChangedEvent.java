package pl.net.crimsonvideo.thirst.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HydrationChangedEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    protected boolean isCancelled = false;

    public Player getPlayer() {
        return player;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    private final Player player;
    private float change;

    public boolean isSet() {
        return isSet;
    }

    private final boolean isSet;

    public HydrationChangedEvent(Player player, float change) {
        this.player = player;
        this.change = change;
        this.isSet = false;
    }

    public HydrationChangedEvent(Player player, float change, boolean isSet) {
        this.player = player;
        this.change = change;
        this.isSet = isSet;
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
