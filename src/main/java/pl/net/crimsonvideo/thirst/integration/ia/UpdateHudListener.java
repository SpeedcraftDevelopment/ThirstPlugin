package pl.net.crimsonvideo.thirst.integration.ia;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerQuantityHudWrapper;
import org.apiguardian.api.API;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.events.HydrationChangedEvent;

@API(status = API.Status.INTERNAL, since = "0.4-SNAPSHOT", consumers = {"pl.net.crimsonvideo.thirst.Thirst"})
public record UpdateHudListener(JavaPlugin plugin) implements Listener {

    @EventHandler
    public void onHydrationChange(@NotNull HydrationChangedEvent ev) {
        if ((round2(Thirst.getAPI().hydrationAPI.getHydration(ev.getPlayer()) + ev.getChange()) != round2(Thirst.getAPI().hydrationAPI.getHydration(ev.getPlayer()))) || ev.isSet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerHudsHolderWrapper wrapper = new PlayerHudsHolderWrapper(ev.getPlayer());
                    PlayerQuantityHudWrapper hud = new PlayerQuantityHudWrapper(wrapper, plugin.getConfig().getString("ia.hud", "itemsadder:thirst"));
                    hud.setFloatValue(Thirst.getAPI().hydrationAPI.getHydration(ev.getPlayer()));
                }
            }.runTask(plugin);
        }
    }

    private static float round2(float val) {
        return ((float) Math.round(val * 100)) / 100;
    }
}
