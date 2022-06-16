package pl.net.crimsonvideo.thirst;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public enum Localisation {
    POLSKI("pl_PL"),
    ENGLISH("en_EN");

    private FileConfiguration config;

    Localisation(String name) {
        try(InputStream file = getClass().getResourceAsStream(String.format("/locale/%s.yml", name))) {
            this.config = new YamlConfiguration();
            assert file != null;
            this.config.load(new InputStreamReader(file));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLocalisedString(String path){
        return this.config.getString(path);
    }

    @Contract("_ -> !null")
    public static Localisation getLocalisationForPlayer(@NotNull Player p){
        if (p.getLocale().equalsIgnoreCase("pol_PL") || p.getLocale().equalsIgnoreCase("pl_pl"))
            return POLSKI;
        else
            return ENGLISH;
    }
}
