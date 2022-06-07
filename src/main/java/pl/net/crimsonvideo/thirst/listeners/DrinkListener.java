package pl.net.crimsonvideo.thirst.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

import java.util.Objects;
import java.util.Random;

public class DrinkListener implements Listener {
    private final JavaPlugin plugin;
    private final Random random;

    public DrinkListener(@NotNull JavaPlugin plugin){
        this.plugin = plugin;
        this.random = new Random(plugin.getConfig().getInt("seed",plugin.getServer().hashCode()));
    }

    @EventHandler
    public void onDrink(@NotNull PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            Thirst.getAPI().hydrationAPI.addHydration(event.getPlayer(), (float) plugin.getConfig().getDouble(String.format("thirst.%s", Objects.requireNonNull(((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType().toString())),1f));
        }
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        double temp = event.getTo().getBlock().getTemperature();
        if (random.nextInt(32767)*temp>30000)
            if (random.nextBoolean() ){
                float loss = (random.nextInt(250-15)+15)/100f;
                Thirst.getAPI().hydrationAPI.subtractHydration(event.getPlayer(), (float)(temp<0.15f?loss*temp:loss*(1f+temp)));
            }
    }
}
