package pl.net.crimsonvideo.thirst.listeners;

import org.apiguardian.api.API;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

import java.util.*;

public class DrinkListener implements Listener {
    private final JavaPlugin plugin;
//    private final Random random;
    private final Map<UUID, BukkitTask> playerTasks;
    private final float hydrationLoss;

    public DrinkListener(@NotNull JavaPlugin plugin){
        this.plugin = plugin;
//        this.random = new Random(plugin.getConfig().getInt("seed",plugin.getServer().hashCode()));
        this.playerTasks = Collections.synchronizedMap(new HashMap<>());
        this.hydrationLoss = (float) this.plugin.getConfig().getDouble("loss",0.125f);
    }

    @EventHandler
    public void onDrink(@NotNull PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            Thirst.getAPI().hydrationAPI.addHydration(event.getPlayer(), (float) plugin.getConfig().getDouble(String.format("thirst.%s", Objects.requireNonNull(((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType().toString())),1f));
        }
    }

//        @EventHandler
//        public void onPlayerMove(@NotNull PlayerMoveEvent event) {
//            final double temp = event.getTo().getBlock().getTemperature();
//            if (random.nextInt(32767)*temp>30000)
//                if (random.nextBoolean() ){
//                    float loss = (random.nextInt(250-15)+15)/100f;
//                    Thirst.getAPI().hydrationAPI.subtractHydration(event.getPlayer(), (float)(temp<0.15f?loss*temp:loss*(1f+temp)));
//                }
//            if (event.getPlayer().hasPermission("thirst.hydration") && Thirst.getAPI().hydrationAPI.getHydration(event.getPlayer())==0)
//                event.getPlayer().damage(1);
//        }

    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        final UUID playerUUID = event.getPlayer().getUniqueId();
        this.playerTasks.put(playerUUID, new BukkitRunnable() {
            @Override
            public void run() {
                Thirst.getAPI().hydrationAPI.subtractHydration(playerUUID,hydrationLoss);
            }
        }.runTaskTimerAsynchronously(this.plugin,0L,this.plugin.getConfig().getLong("period",10L)));
     }
}
