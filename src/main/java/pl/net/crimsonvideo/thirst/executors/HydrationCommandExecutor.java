package pl.net.crimsonvideo.thirst.executors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.net.crimsonvideo.thirst.Thirst;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooHighError;
import pl.net.crimsonvideo.thirst.exceptions.ValueTooLowError;

import java.util.logging.Level;

public class HydrationCommandExecutor implements CommandExecutor {
    private JavaPlugin plugin;

    public HydrationCommandExecutor(JavaPlugin plugin){this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if  (command.getName().equalsIgnoreCase("hydration")) {
            if ("get".equalsIgnoreCase(args[0])) {
                if (sender instanceof BlockCommandSender) {
                    this.plugin.getLogger().warning("The /hydration get command can't be used with command blocks.");
                    return false;
                }
                if (sender.hasPermission("thirst.admin.get")) {
                    final Player player = plugin.getServer().getPlayer(args[1]);
                    this.plugin.getLogger().info(String.format("Hydration for %s: %.5f", args[2], Thirst.getAPI().hydrationAPI.getHydration(player)));
                    if (sender instanceof Player)
                        sender.sendMessage(String.format("Hydration for %s: %.5f", args[2], Thirst.getAPI().hydrationAPI.getHydration(player)));
                    return true;
                }
                else{
                    plugin.getLogger().warning("Permission \"thirst.admin.get\" not present.");
                    if (sender instanceof Player)
                        sender.sendMessage(ChatColor.RED + "You do not have the permission to do that.");
                    return false;
                }
            }else if ("set".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("thirst.admin.set"))
                {
                    plugin.getLogger().warning("Permission \"thirst.admin.set\" not present.");
                    if (sender instanceof Player)
                        sender.sendMessage(ChatColor.RED + "You do not have the permission to do that.");
                    return false;
                } else {
                    Thirst.getAPI().hydrationAPI.setHydration(plugin.getServer().getPlayer(args[1]), Float.parseFloat(args[2]));
                    return true;
                }
            } else if ("add".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("thirst.admin.add")) {
                    plugin.getLogger().warning("Permission \"thirst.admin.add\" not present.");
                    if (sender instanceof Player)
                        sender.sendMessage(ChatColor.RED + "You do not have the permission to do that.");
                    return false;
                } else {
                    try {
                        Thirst.getAPI().hydrationAPI.addHydration(plugin.getServer().getPlayer(args[1]), Float.parseFloat(args[2]));
                    } catch (ValueTooHighError error) {
                        error.printStackTrace();
                        if (sender instanceof Player)
                            sender.sendMessage(ChatColor.RED + "Value too high.");
                        return false;
                    } catch (ValueTooLowError error) {
                        error.printStackTrace();
                        if (sender instanceof Player)
                            sender.sendMessage(ChatColor.RED + "Value too low.");
                        return false;
                    }
                    return true;
                }
            } else if ("subtract".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("thirst.admin.subtract")) {
                    plugin.getLogger().warning("Permission \"thirst.admin.subtract\" not present.");
                    if (sender instanceof Player)
                        sender.sendMessage(ChatColor.RED + "You do not have the permission to do that.");
                    return false;
                } else {
                    try {
                        Thirst.getAPI().hydrationAPI.subtractHydration(plugin.getServer().getPlayer(args[1]), Float.parseFloat(args[2]));
                    } catch (ValueTooHighError error) {
                        error.printStackTrace();
                        if (sender instanceof Player)
                            sender.sendMessage(ChatColor.RED + "Value too high.");
                        return false;
                    } catch (ValueTooLowError error) {
                        error.printStackTrace();
                        if (sender instanceof Player)
                            sender.sendMessage(ChatColor.RED + "Value too low.");
                        return false;
                    }
                    return true;
                }
            } else {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final BossBar bar = Bukkit.createBossBar("Thirst", BarColor.BLUE, BarStyle.SEGMENTED_20);
                    bar.setProgress(Thirst.getAPI().hydrationAPI.getHydration(player) / 20f);
                    bar.addPlayer(player);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            bar.removeAll();
                        }
                    }.runTaskLater(plugin, 100);
                    return true;
                } else {
                    this.plugin.getLogger().warning("This command can only be used with players.");
                    return false;
                }
            }
        }
        return false;
    }
}
