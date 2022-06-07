package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

import java.util.Objects;

public class DrinkListener implements Listener {
    private final JavaPlugin plugin;

    public DrinkListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrink(@NotNull PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            Thirst.getAPI().hydrationAPI.addHydration(event.getPlayer(), (float) plugin.getConfig().getDouble(String.format("thirst.%s", Objects.requireNonNull(((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType().toString())),1f));
        }
    }
}
