package com.consolex.poke;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Poke extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Poke Enabled! " + ChatColor.AQUA + "@1.0");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getPluginManager().registerEvents(this, this);

        config.addDefault("poke-cooldown", 3);
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Poke Disabled!");
    }

    HashMap<Player, Sound> pokePreference = new HashMap<>();
    List<Player> inCooldown = new ArrayList<>();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (!pokePreference.containsKey(player))
        {
            pokePreference.put(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }
    }


    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event)
    {
        String msg = event.getMessage();
        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if (msg.contains("@" + p.getName()))
            {
                if (inCooldown.contains(event.getPlayer()))
                {
                    msg = msg.replaceAll("@", "");
                    event.getPlayer().sendMessage(ChatColor.RED + "You are currently on ping cooldown!");
                }
                else
                {
                    msg =  msg.replaceAll("@" + p.getName(), ChatColor.YELLOW + "@" + p.getName() + ChatColor.WHITE);
                    p.playSound(p.getLocation(), pokePreference.get(p), 1, 1);
                    inCooldown.add(event.getPlayer());
                    new BukkitRunnable()
                    {
                        @Override
                        public void run() {
                            inCooldown.remove(event.getPlayer());
                        }
                    }.runTaskLater(this, config.getInt("poke-cooldown")* 20L);
                }
            }
        }
        event.setMessage(msg);
    }



}
