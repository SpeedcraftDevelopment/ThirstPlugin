package pl.net.crimsonvideo.thirst.listeners;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apiguardian.api.API;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Localisation;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.events.HydrationChangedEvent;

@API(status = API.Status.INTERNAL, since = "0.2-SNAPSHOT")
public record HydrationChangeListener(JavaPlugin plugin) implements Listener {

    @EventHandler
    public void onHydrationChanged(@NotNull HydrationChangedEvent event) {
        if (event.isCancelled())
            return;
        if (plugin.getConfig().getBoolean("log", false))
            plugin.getLogger().info(String.format("%s's hydration changed by %.5f", event.getPlayer().getName(), event.getChange()));
        Audience.audience(event.getPlayer()).sendActionBar(Component.text(new String(new char[((int) Math.floor(Thirst.getAPI().hydrationAPI.getHydration(event.getPlayer())))]).replace('\0', '|')).color(TextColor.color(0x000066)).append(Component.text(new String(new char[(20 - ((int) Math.floor(Thirst.getAPI().hydrationAPI.getHydration(event.getPlayer()))))]).replace('\0', '|')).color(TextColor.color(0x000000))).append(Component.text(" - " + Localisation.getLocalisationForPlayer(event.getPlayer()).getLocalisedString("thirst.actionbar.hydration"))).color(TextColor.color(0xffffff)));
    }
}
