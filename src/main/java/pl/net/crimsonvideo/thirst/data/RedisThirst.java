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
            if (jedis.exists(p.getUniqueId().toString()))
                return Float.parseFloat(jedis.get(p.getUniqueId().toString()));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }

    @Override
    public float getPlayerHydration(@NotNull UUID p) throws IndexOutOfBoundsException {
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            if (jedis.exists(p.toString()))
                return Float.parseFloat(jedis.get(p.toString()));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }

    @Override
    public void addHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        if (hydration > 20) throw new ValueTooHighError("Value added higher than 20");
        else if (hydration < 0) throw new ValueTooLowError("Value subtracted lower than 0");
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            if (jedis.exists(p.getUniqueId().toString()))
                jedis.set(p.getUniqueId().toString(),Float.toString((Float.parseFloat(jedis.get(p.getUniqueId().toString()))+hydration)>20?20:Float.parseFloat(jedis.get(p.getUniqueId().toString()))+hydration));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }

    @Override
    public void addHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooHighError, ValueTooLowError {
        if (hydration > 20) throw new ValueTooHighError("Value added higher than 20");
        else if (hydration < 0) throw new ValueTooLowError("Value subtracted lower than 0");
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            if (jedis.exists(p.toString()))
                jedis.set(p.toString(),Float.toString((Float.parseFloat(jedis.get(p.toString()))+hydration)>20?20:Float.parseFloat(jedis.get(p.toString()))+hydration));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }

    @Override
    public void subtractHydration(@NotNull Player p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        if (hydration > 20) throw new ValueTooHighError("Value added higher than 20");
        else if (hydration < 0) throw new ValueTooLowError("Value subtracted lower than 0");
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            if (jedis.exists(p.getUniqueId().toString()))
                jedis.set(p.getUniqueId().toString(),Float.toString((Float.parseFloat(jedis.get(p.getUniqueId().toString()))-hydration)<0?0:Float.parseFloat(jedis.get(p.getUniqueId().toString()))-hydration));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }

    @Override
    public void subtractHydration(@NotNull UUID p, float hydration) throws IndexOutOfBoundsException, ValueTooLowError, ValueTooHighError {
        if (hydration > 20) throw new ValueTooHighError("Value added higher than 20");
        else if (hydration < 0) throw new ValueTooLowError("Value subtracted lower than 0");
        try (Jedis jedis = pool.getResource()) {
            if (plugin.getConfig().isString("redis.auth.username") && plugin.getConfig().isString("redis.auth.password"))
                jedis.auth(plugin.getConfig().getString("redis.auth.username"), plugin.getConfig().getString("redis.auth.password"));
            if (jedis.exists(p.toString()))
                jedis.set(p.toString(),Float.toString((Float.parseFloat(jedis.get(p.toString()))-hydration)<0?0:Float.parseFloat(jedis.get(p.toString()))-hydration));
            else throw new IndexOutOfBoundsException("No player in database.");
        }
    }
}
