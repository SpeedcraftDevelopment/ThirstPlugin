package pl.net.crimsonvideo.thirst.data;

import org.apiguardian.api.API;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.util.UUID;

@API(status = API.Status.INTERNAL,since = "0.3-SNAPSHOT")
public interface IThirstData {
    void setPlayerHydration(@NotNull Player p, float hydration);
    void setPlayerHydration(@NotNull UUID p, float hydration);
    float getPlayerHydration(@NotNull Player p) throws IndexOutOfBoundsException;
    float getPlayerHydration(@NotNull UUID p) throws IndexOutOfBoundsException;
    void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError;
    void addHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError;
    void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError;
    void subtractHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError;
}
