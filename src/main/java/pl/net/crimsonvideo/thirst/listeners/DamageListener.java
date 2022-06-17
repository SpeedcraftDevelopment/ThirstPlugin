package pl.net.crimsonvideo.thirst.listeners;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Localisation;
import pl.net.crimsonvideo.thirst.Thirst;

public class DamageListener implements Listener {
    @EventHandler
    public void onPlayerDamage(@NotNull EntityDamageEvent event) {
        if (event.getEntity() instanceof Player){
           if (Thirst.getAPI().hydrationAPI.getHydration((Player)event.getEntity()) == 0)
               ActionBarAPI.sendActionBar((Player)event.getEntity(),ChatColor.RED + Localisation.getLocalisationForPlayer((Player) event.getEntity()).getLocalisedString("thirst.actionbar.dehydrate"));
        }
    }
}
