package pl.net.crimsonvideo.thirst.api;

import org.bukkit.entity.Player;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.ThirstAPI;
import pl.net.crimsonvideo.thirst.data.ThirstData;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.lang.reflect.Field;

public interface IHydrationAPI {
    static Thirst getPlugin() throws NoSuchFieldException, IllegalAccessException {
        Field plugin = ThirstAPI.class.getDeclaredField("plugin");
        plugin.setAccessible(true);
        return (Thirst)plugin.get(Thirst.getAPI());
    }
    /***
     * Gets hydration from player.
     * @param player The player whose hydration will be read.
     * @return The player's hydration.
     * @throws IndexOutOfBoundsException Player has no hydration assigned.
     * @see ThirstData#getPlayerHydration(Player)
     */
    float getHydration(Player player) throws IndexOutOfBoundsException;
    /***
     * Sets hydration for player.
     * @param player The player whose hydration will be set.
     * @param hydration The hydration to set.
     * @see ThirstData#setPlayerHydration(Player, float)
     */
    void setHydration(Player player, float hydration);
    /***
     * Increases hydration of player.
     * @param player The player whose hydration will be increased.
     * @param hydration The hydration ot increase.
     * @throws IndexOutOfBoundsException Player has no hydration assigned.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is below 0.
     * @see ThirstData#addHydration(Player, float)
     */
    void addHydration(Player player, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError;
    /***
     * Subtracts hydration from player.
     * @param player The player whose hydration will be subtracted.
     * @param hydration The hydration to subtract.
     * @throws IndexOutOfBoundsException Player has no hydration assigned.
     * @throws ValueTooHighError Hydration is greater than 20.
     * @throws ValueTooLowError Hydration is lower than 0.
     * @see ThirstData#subtractHydration(Player, float)
     */
    void subtractHydration(Player player, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError;
}
