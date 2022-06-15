package pl.net.crimsonvideo.thirst;

import org.apiguardian.api.API;
import pl.net.crimsonvideo.thirst.listeners.HydrationChangeListener;
import relocate.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.api.IHydrationAPI;
import pl.net.crimsonvideo.thirst.data.ThirstData;
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
public final class Thirst extends JavaPlugin {

    private File file;
    private File thirstDataFile;
    private FileConfiguration config;
    ThirstData thirstData;
    private static ThirstAPI api;

    public Thirst()
    {
        super();
    }

    protected Thirst(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad(){
        this.thirstDataFile = new File(getDataFolder() + "/hydration.dat");
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
        new BukkitRunnable() {

            @Override
            public void run() {
                thirstData.saveData(thirstDataFile.getPath());
            }
        }.runTaskTimerAsynchronously(this,1,getConfig().getLong("autosavetime",60L)*1200L);
        getServer().getPluginManager().registerEvents(new DrinkListener(this),this);
        getServer().getPluginManager().registerEvents(new HydrationChangeListener(this),this);
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
        this.config.set("autosavetime",60L);
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

    static class HydrationAPI implements IHydrationAPI {

        @Override
        public float getHydration(Player player) {
            try {
                return this.getThirstData().getPlayerHydration(player);
            } catch (IndexOutOfBoundsException e){
                this.getThirstData().setPlayerHydration(player,20f);
                return 20f;
            }
        }

        @Override
        public void setHydration(Player player, float hydration) {
            this.getThirstData().setPlayerHydration(player, hydration);
        }

        @Override
        public void addHydration(Player player, float hydration) throws ValueTooHighError, ValueTooLowError {
            try {
                this.getThirstData().addHydration(player, hydration);
            } catch (IndexOutOfBoundsException e){
                this.getThirstData().setPlayerHydration(player,20f);
            }
        }

        @Override
        public void subtractHydration(Player player, float hydration) throws ValueTooHighError, ValueTooLowError {
            try {
                this.getThirstData().subtractHydration(player, hydration);
            } catch (IndexOutOfBoundsException e) {
                this.getThirstData().setPlayerHydration(player,20f-hydration);
            }
        }

        private ThirstData getThirstData() {
            return IHydrationAPI.getPlugin().thirstData;
        }
    }
}
