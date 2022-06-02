package pl.net.crimsonvideo.thirst;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/***
 * The main Plugin class.
 * @author CrimsonVideo
 */
public final class Thirst extends JavaPlugin {

    private File file;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        file = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        int pluginId = 15371;
        Metrics metrics = new Metrics(this,pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
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
        Scanner scan = null;
        try {
            scan = new Scanner(file);

            int row = 0;
            String line = "";

            // iterate through the file line by line
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                // add to the row
                row++;

                // If a tab is found ... \t = tab in regex
                if (line.indexOf("\t") != -1) {
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
        }
        catch (FileNotFoundException e) {
            // this error should never happen if the file exists
            e.printStackTrace();
        }
        catch (IOException e) {
            // failed loading error
            e.printStackTrace();
        }
        catch (InvalidConfigurationException e) {
            // snakeyaml error if the config setup is incorrect.
            e.printStackTrace();
        }
        finally {
            // Close the scanner to avoid memory leaks.
            if (scan != null) {
                scan.close();
            }
        }
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
}
