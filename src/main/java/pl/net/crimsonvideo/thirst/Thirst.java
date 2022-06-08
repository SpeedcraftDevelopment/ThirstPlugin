package pl.net.crimsonvideo.thirst;

import org.apiguardian.api.API;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.api.IHydrationAPI;
import pl.net.crimsonvideo.thirst.data.ThirstData;
import pl.net.crimsonvideo.thirst.events.HydrationChangedEvent;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;
import pl.net.crimsonvideo.thirst.listeners.DrinkListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/***
 * The main Plugin class.
 * @author CrimsonVideo
 */
@API(status = API.Status.INTERNAL,since="0.1-SNAPSHOT")
public final class Thirst extends JavaPlugin implements Listener {

    private File file;
    private File thirstDataFile;
    private FileConfiguration config;
    ThirstData thirstData;
    private static ThirstAPI api;

    private Map<UUID, BossBar> uuidBossBarMap;

    @Override
    public void onLoad(){
        this.thirstDataFile = new File(getDataFolder() + "hydration.dat");
        try {
            this.thirstData = ThirstData.loadData(thirstDataFile.getPath());
            Field plugin = ThirstData.class.getDeclaredField("plugin");
            plugin.setAccessible(true);
            plugin.set(this.thirstData,this);
        } catch (IOException e) {
            if (!thirstDataFile.exists())
            {
                this.thirstData = new ThirstData(this);
                if (!this.thirstData.saveData(thirstDataFile.getPath()))
                    throw new RuntimeException("Can't create data file.");
            }
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        api = new ThirstAPI(this);
    }

    @API(status= API.Status.STABLE,since="0.1-SNAPSHOT")
    public static ThirstAPI getAPI(){
        return api;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        file = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        reloadConfig();
        this.uuidBossBarMap = Collections.synchronizedMap(new HashMap<UUID,BossBar>());
        getServer().getPluginManager().registerEvents(new DrinkListener(this),this);
        int pluginId = 15371;
        Metrics metrics = new Metrics(this,pluginId);
    }

    @Override
    public void onDisable() {
        this.thirstData.saveData(this.thirstDataFile.getPath());
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void saveDefaultConfig() {
        this.config.set("seed",this.getServer().hashCode());
        for (PotionType type : PotionType.values())
            this.config.set(String.format("thirst.%s",type.name()),1f);
        this.saveConfig();
    }

    @Override
    public void saveConfig() {
        try {
            config.save(file);
        }
        catch (IOException e) {
            // print stacktrace if you prefer
            getLogger().severe(
                    "Could not save config.yml due to: " + e.getMessage());
        }
    }

    private void scanConfig() {
        // declare our scanner variable
        try (Scanner scan = new Scanner(file)) {

            int row = 0;
            String line = "";

            // iterate through the file line by line
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                // add to the row
                row++;

                // If a tab is found ... \t = tab in regex
                if (line.contains("\t")) {
                    /*
                     * Tell the user where the tab is! We throw an
                     * IllegalArgumentException here.
                     */
                    String error = ("Tab found in config-file on line # " + row + "!");
                    throw new IllegalArgumentException(error);
                }
            }
            /*
             * load the file, if tabs were found then this will never execute
             * because of IllegalArgumentException
             */
            config.load(file);
        } catch (FileNotFoundException e) {
            // this error should never happen if the file exists
            e.printStackTrace();
        } catch (IOException e) {
            // failed loading error
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            // snakeyaml error if the config setup is incorrect.
            e.printStackTrace();
        }
        // Close the scanner to avoid memory leaks.
    }

    @Override
    public void reloadConfig() {
        if (!file.exists()) {
            // Create file if it doesn't exist. Change appropriately based on
            // your setup
            saveDefaultConfig();
        }
        scanConfig();
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        float hydration;
        final Player player = event.getPlayer();
        try {
            hydration = Thirst.getAPI().hydrationAPI.getHydration(player);
        } catch (IndexOutOfBoundsException e) {
            Thirst.getAPI().hydrationAPI.setHydration(player, 20f);
            hydration = 20f;
        }
        BossBar bar = Bukkit.createBossBar("Thirst", BarColor.BLUE, BarStyle.SEGMENTED_20);
        bar.setProgress(hydration/20f);
        this.uuidBossBarMap.put(player.getUniqueId(),bar);
    }

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event){
        this.uuidBossBarMap.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onHydrationChanged(@NotNull HydrationChangedEvent event){
        final Player player = event.getPlayer();
        if (this.uuidBossBarMap.containsKey(player.getUniqueId()))
        {
            final BossBar bar = this.uuidBossBarMap.get(player.getUniqueId());
            bar.addPlayer(player);
            bar.setProgress(this.thirstData.getPlayerHydration(player)/20f);
        }
    }

    static class HydrationAPI implements IHydrationAPI {

        @Override
        public float getHydration(Player player) throws IndexOutOfBoundsException {
            return this.getThirstData().getPlayerHydration(player);
        }

        @Override
        public void setHydration(Player player, float hydration) {
            this.getThirstData().setPlayerHydration(player, hydration);
        }

        @Override
        public void addHydration(Player player, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
            this.getThirstData().addHydration(player, hydration);
        }

        @Override
        public void subtractHydration(Player player, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
            this.getThirstData().subtractHydration(player, hydration);
        }

        private ThirstData getThirstData() {
            return IHydrationAPI.getPlugin().thirstData;
        }
    }
}
