package pl.net.crimsonvideo.thirst.api;

import org.apiguardian.api.API;
import org.bukkit.entity.Player;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.ThirstAPI;
import pl.net.crimsonvideo.thirst.data.ThirstData;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.lang.reflect.Field;
import java.util.UUID;

@API(status= API.Status.STABLE,since="0.1-SNAPSHOT")
public interface IHydrationAPI {
    static Thirst getPlugin() {
        try {
            Field plugin = ThirstAPI.class.getDeclaredField("plugin");
            plugin.setAccessible(true);
            return (Thirst)plugin.get(Thirst.getAPI());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    /***
     * Gets hydration from player.
     * @param player The player whose hydration will be read.
     * @return The player's hydration.
     * @see ThirstData#getPlayerHydration(Player)
     */
    float getHydration(Player player);

    @API(status= API.Status.STABLE,since="0.2-SNAPSHOT")
    float getHydration(UUID player);
    /***
     * Sets hydration for player.
     * @param player The player whose hydration will be set.
     * @param hydration The hydration to set.
     * @see ThirstData#setPlayerHydration(Player, float)
     */
    void setHydration(Player player, float hydration);

    @API(status= API.Status.STABLE,since="0.2-SNAPSHOT")
    void setHydration(UUID player, float hydration);
    /***
     * Increases hydration of player.
     * @param player The player whose hydration will be increased.
     * @param hydration The hydration ot increase.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is below 0.
     * @see ThirstData#addHydration(Player, float)
     */
    void addHydration(Player player, float hydration) throws ValueTooHighError, ValueTooLowError;

    @API(status= API.Status.STABLE,since="0.2-SNAPSHOT")
    void addHydration(UUID player, float hydration) throws ValueTooHighError, ValueTooLowError;
    /***
     * Subtracts hydration from player.
     * @param player The player whose hydration will be subtracted.
     * @param hydration The hydration to subtract.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is lower than 0.
     * @see ThirstData#subtractHydration(Player, float)
     */
    void subtractHydration(Player player, float hydration) throws ValueTooHighError, ValueTooLowError;

    @API(status= API.Status.STABLE,since="0.2-SNAPSHOT")
    void subtractHydration(UUID player, float hydration) throws ValueTooHighError, ValueTooLowError;
}
