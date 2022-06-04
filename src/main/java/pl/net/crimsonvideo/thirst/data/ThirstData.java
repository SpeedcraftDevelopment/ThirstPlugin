package pl.net.crimsonvideo.thirst.data;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ThirstData implements Serializable {
    private transient static final long serialVersionUID = 7410964373687906258L;

    private final transient JavaPlugin plugin;

    private Map<UUID,Float> hydrationMap;

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
     */
    public void setPlayerHydration(@NotNull Player p, float hydration) {
        this.hydrationMap.put(p.getUniqueId(),hydration);
    }

    /***
     * Gets hydration from player.
     * @param p The player whose hydration will be read.
     * @return The player's hydration.
     * @throws IndexOutOfBoundsException The given Player is not in the database.
     */
    public float getPlayerHydration(@NotNull Player p) throws IndexOutOfBoundsException {
        if (this.hydrationMap.containsKey(p.getUniqueId()))
            return this.hydrationMap.get(p.getUniqueId());
        else
            throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
    }

    /***
     * Increases hydration of player.
     * @param p The player whose hydration will be increased.
     * @param hydration The hydration ot increase.
     * @throws IndexOutOfBoundsException Player is not in the data file.
     * @throws ValueTooHighError Hydration is greater than 20.
     */
    public void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError {
        if (hydration > 20)
            throw new ValueTooHighError("Hydration greater than 20");
        else
        {
            if (this.hydrationMap.containsKey(p.getUniqueId()))
                this.hydrationMap.compute(p.getUniqueId(),(k,v) -> Math.min(v + hydration, 20f));
            else
                throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
        }
    }

    /***
     * Subtracts hydration from player.
     * @param p The player whose hydration will be subtracted.
     * @param hydration The hydration to subtract.
     * @throws IndexOutOfBoundsException Player is not in the data file.
     * @throws ValueTooLowError Hydration is greater than 20.
     */
    public void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError {
        if (hydration > 20)
            throw new ValueTooLowError("Hydration above 20");
        else {
            if (this.hydrationMap.containsKey(p.getUniqueId()))
                this.hydrationMap.compute(p.getUniqueId(),(k,v) -> v -= hydration);
            else
                throw new IndexOutOfBoundsException(p.getUniqueId().toString() + " is not in the data file.");
        }
    }
}
