package pl.net.crimsonvideo.thirst.listeners;

import org.apiguardian.api.API;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;

import java.util.*;

public class DrinkListener implements Listener {
    private final JavaPlugin plugin;
    private final Random random;
    private final Map<UUID, BukkitTask> playerTasks;
    private final Map<UUID,BukkitTask> damageTasks;
    private final float hydrationLoss;

    public DrinkListener(@NotNull JavaPlugin plugin){
        this.plugin = plugin;
        this.random = new Random(plugin.getConfig().getInt("seed",plugin.getServer().hashCode()));
        this.playerTasks = Collections.synchronizedMap(new HashMap<>());
        this.damageTasks = new HashMap<>();
        this.hydrationLoss = (float) this.plugin.getConfig().getDouble("loss",0.125f);
    }

    @EventHandler
    public void onDrink(@NotNull PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    Thirst.getAPI().hydrationAPI.addHydration(event.getPlayer(), (float) plugin.getConfig().getDouble(String.format("thirst.%s", Objects.requireNonNull(((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType().toString())),1f));
                }
            }.runTaskAsynchronously(this.plugin);
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
        final double damage = this.plugin.getConfig().getDouble("damage",0.5);
        if (event.getPlayer().hasPermission("thirst.hydration"))
            this.playerTasks.put(playerUUID, new BukkitRunnable() {
                @Override
                public void run() {
                    if (Thirst.getAPI().hydrationAPI.getHydration(playerUUID) > 0)
                        if (random.nextInt(100) > 95)
                            Thirst.getAPI().hydrationAPI.subtractHydration(playerUUID,calculateTemperatureLoss(hydrationLoss));
                    else if (Thirst.getAPI().hydrationAPI.getHydration(playerUUID) < 0)
                        Thirst.getAPI().hydrationAPI.setHydration(playerUUID,0);
                }

                private float calculateTemperatureLoss(float hydrationLoss) {
                    float temperature = (float) event.getPlayer().getLocation().getBlock().getTemperature();
                    if (temperature > 0.16f)
                        return hydrationLoss * (1 + temperature);
                    else
                        return hydrationLoss * temperature;
                }
            }.runTaskTimerAsynchronously(this.plugin,0L,10L));
        this.damageTasks.put(playerUUID,new BukkitRunnable() {
            @Override
            public void run() {
                if (Thirst.getAPI().hydrationAPI.getHydration(playerUUID) == 0)
                    event.getPlayer().damage(damage);
            }
        }.runTaskTimer(this.plugin,0L,9L));
     }

    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        this.playerTasks.get(event.getPlayer().getUniqueId()).cancel();
        this.playerTasks.remove(event.getPlayer().getUniqueId());
        this.damageTasks.get(event.getPlayer().getUniqueId()).cancel();
        this.damageTasks.remove(event.getPlayer().getUniqueId());
    }
}
