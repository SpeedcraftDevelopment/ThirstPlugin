package pl.net.crimsonvideo.thirst;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public enum Localisation {
    POLSKI("pl_PL"),
    ENGLISH("en_EN");

    private File file;
    private FileConfiguration config;

    Localisation(String name) {
        this.file = new File(Objects.requireNonNull(getClass().getResource(String.format("/locale/%s.yml", name))).getFile());
        this.config = new YamlConfiguration();
        try {
            this.config.load(this.file);
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
