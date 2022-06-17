package pl.net.crimsonvideo.thirst.data;

import org.apiguardian.api.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.net.crimsonvideo.thirst.api.IHydrationAPI;
import pl.net.crimsonvideo.thirst.events.HydrationChangedEvent;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ThirstData implements IThirstData,Serializable {
    @Serial
    private transient static final long serialVersionUID = 7410964373687906258L;

    private final transient JavaPlugin plugin;

    private final Map<UUID,Float> hydrationMap;

    /**
     * Initializes the hydration data file.
     * @param plugin The plugin the data file belongs to.
     */
    public ThirstData(JavaPlugin plugin) {
        this.plugin = plugin;
        this.hydrationMap = new HashMap<>();
    }

    /***
     * Sets hydration for player.
     * @param p The player whose hydration will be set.
     * @param hydration The hydration to set.
     * @see IHydrationAPI#setHydration(Player, float)
     */
    @Override
    public void setPlayerHydration(@NotNull Player p, float hydration) {
        HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(p,hydration,true);
        Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
        if (!hydrationChangedEvent.isCancelled())
            this.hydrationMap.put(p.getUniqueId(),hydrationChangedEvent.getChange());
    }

    @Override
    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    public void setPlayerHydration(@NotNull UUID p, float hydration) {
        HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(this.plugin.getServer().getPlayer(p),hydration,true);
        Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
        if (!hydrationChangedEvent.isCancelled())
            this.hydrationMap.put(p,hydrationChangedEvent.getChange());
    }

    /***
     * Gets hydration from player.
     * @param p The player whose hydration will be read.
     * @return The player's hydration.
     * @throws IndexOutOfBoundsException The given Player is not in the database.
     * @see IHydrationAPI#getHydration(Player)
     */
    @Override
    public float getPlayerHydration(@NotNull Player p) throws IndexOutOfBoundsException {
        if (this.hydrationMap.containsKey(p.getUniqueId()))
            return this.hydrationMap.get(p.getUniqueId());
        else
            throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
    }

    @Override
    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    public float getPlayerHydration(@NotNull UUID p) throws IndexOutOfBoundsException {
        if (this.hydrationMap.containsKey(p))
            return this.hydrationMap.get(p);
        else
            throw new IndexOutOfBoundsException(p.toString() + " is not in the data file.");
    }

    /***
     * Increases hydration of player.
     * @param p The player whose hydration will be increased.
     * @param hydration The hydration ot increase.
     * @throws IndexOutOfBoundsException Player is not in the data file.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is below 0.
     * @see IHydrationAPI#addHydration(Player, float) 
     */
    @Override
    public void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        if (hydration > 20)
            throw new ValueTooHighError("Hydration greater than 20");
        else if (hydration < 0)
            throw new ValueTooLowError("Hydration below 0");
        else
        {
            HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(p,hydration);
            Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
            if (!hydrationChangedEvent.isCancelled())
            {
                if (this.hydrationMap.containsKey(p.getUniqueId()))
                    this.hydrationMap.compute(p.getUniqueId(),(k,v) -> Math.min(v + hydrationChangedEvent.getChange(), 20f));
                else
                    throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
            }
        }
    }

    @Override
    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    public void addHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        if (hydration > 20)
            throw new ValueTooHighError("Hydration greater than 20");
        else if (hydration < 0)
            throw new ValueTooLowError("Hydration below 0");
        else
        {
            HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(this.plugin.getServer().getPlayer(p),hydration);
            Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
            if (!hydrationChangedEvent.isCancelled())
            {
                if (this.hydrationMap.containsKey(p))
                    this.hydrationMap.compute(p,(k,v) -> Math.min(v + hydrationChangedEvent.getChange(), 20f));
                else
                    throw new IndexOutOfBoundsException(p.toString() + " is not in the data file.");
            }
        }
    }

    /***
     * Subtracts hydration from player.
     * @param p The player whose hydration will be subtracted.
     * @param hydration The hydration to subtract.
     * @throws IndexOutOfBoundsException Player is not in the data file.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is lower than 0.
     * @see IHydrationAPI#subtractHydration(Player, float) 
     */
    @Override
    public void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        if (hydration > 20)
            throw new ValueTooHighError("Hydration above 20");
        else if (hydration < 0)
            throw new ValueTooLowError("Hydration below 0");
        else {
            HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(p,-hydration);
            Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
            if (!hydrationChangedEvent.isCancelled())
                if (this.hydrationMap.containsKey(p.getUniqueId()))
                    this.hydrationMap.compute(p.getUniqueId(),(k,v) -> Math.max(0f,v+hydrationChangedEvent.getChange()));
                else
                    throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
        }
    }

    @Override
    @API(status= API.Status.INTERNAL,since="0.2-SNAPSHOT")
    public void subtractHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        if (hydration > 20)
            throw new ValueTooHighError("Hydration above 20");
        else if (hydration < 0)
            throw new ValueTooLowError("Hydration below 0");
        else {
            HydrationChangedEvent hydrationChangedEvent = new HydrationChangedEvent(this.plugin.getServer().getPlayer(p),-hydration);
            Bukkit.getPluginManager().callEvent(hydrationChangedEvent);
            if (!hydrationChangedEvent.isCancelled())
                if (this.hydrationMap.containsKey(p))
                    this.hydrationMap.compute(p,(k,v) -> Math.max(0f,v+hydrationChangedEvent.getChange()));
                else
                    throw new IndexOutOfBoundsException(p.toString() + " is not in the data file.");
        }
    }

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(Files.newOutputStream(Paths.get(filePath))));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static @Nullable ThirstData loadData(String filePath) throws IOException, ClassNotFoundException {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(Files.newInputStream(Paths.get(filePath))));
            ThirstData data = (ThirstData) in.readObject();
            in.close();
            return data;
    }
}
