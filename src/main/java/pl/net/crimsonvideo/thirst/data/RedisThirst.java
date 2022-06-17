package pl.net.crimsonvideo.thirst.data;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public record RedisThirst(Jedis jedis) implements IThirstData{
    @Override
    public void setPlayerHydration(@NotNull Player p, float hydration) {

    }

    @Override
    public void setPlayerHydration(@NotNull UUID p, float hydration) {

    }

    @Override
    public float getPlayerHydration(@NotNull Player p) throws IndexOutOfBoundsException {
        return 0;
    }

    @Override
    public float getPlayerHydration(@NotNull UUID p) throws IndexOutOfBoundsException {
        return 0;
    }

    @Override
    public void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {

    }

    @Override
    public void addHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {

    }

    @Override
    public void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {

    }

    @Override
    public void subtractHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {

    }
}
