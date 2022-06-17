package pl.net.crimsonvideo.thirst.data;

import org.apiguardian.api.API;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@API(status = API.Status.INTERNAL,since = "0.3-SNAPSHOT")
public record RedisThirst(JavaPlugin plugin, JedisPool pool) implements IThirstData{
    @Override
    public void setPlayerHydration(@NotNull Player p, float hydration) {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.getUniqueId().toString(),Float.toString(hydration));
        }
    }

    @Override
    public void setPlayerHydration(@NotNull UUID p, float hydration) {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.toString(),Float.toString(hydration));
        }
    }

    @Override
    public float getPlayerHydration(@NotNull Player p) throws IndexOutOfBoundsException {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            return Float.parseFloat(jedis.get(p.getUniqueId().toString()));
        }
    }

    @Override
    public float getPlayerHydration(@NotNull UUID p) throws IndexOutOfBoundsException {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            return Float.parseFloat(jedis.get(p.toString()));
        }
    }

    @Override
    public void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.getUniqueId().toString(),Float.toString(Float.parseFloat(jedis.get(p.getUniqueId().toString()))+hydration));
        }
    }

    @Override
    public void addHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.toString(),Float.toString(Float.parseFloat(jedis.get(p.toString()))+hydration));
        }
    }

    @Override
    public void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.getUniqueId().toString(),Float.toString(Float.parseFloat(jedis.get(p.getUniqueId().toString()))-hydration));
        }
    }

    @Override
    public void subtractHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            jedis.set(p.toString(),Float.toString(Float.parseFloat(jedis.get(p.toString()))-hydration));
        }
    }
}
