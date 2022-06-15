package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.events.HydrationChangedEvent;

public class HydrationChangeListener implements Listener {

    private final JavaPlugin plugin;

    public HydrationChangeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHydrationChanged(@NotNull HydrationChangedEvent event){
        if (event.isCancelled())
            return;
        plugin.getLogger().info(String.format("%s's hydration changed by %.5f", event.getPlayer().getName(),event.getChange()));
        event.getPlayer().sendActionBar(ChatColor.DARK_BLUE + new String(new char[((int) Math.floor(Thirst.getAPI().hydrationAPI.getHydration(event.getPlayer())))]).replace('\0', '|') + ChatColor.BLACK + new String(new char[(20-((int) Math.floor(Thirst.getAPI().hydrationAPI.getHydration(event.getPlayer()))))]).replace('\0','|') + ChatColor.WHITE + " - Hydration Level");
    }
}
