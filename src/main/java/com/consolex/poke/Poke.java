package com.consolex.poke;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Poke extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "----------");
        Bukkit.getLogger().info(ChatColor.YELLOW + "Poke Enabled!" + ChatColor.RED + "@1.0");
        Bukkit.getLogger().info(ChatColor.GREEN + "----------");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event)
    {
        String msg = event.getMessage();
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (msg.contains("@" + p.getName()))
            {
                msg =  msg.replaceAll("@" + p.getName(), ChatColor.YELLOW + "@");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,  1);
            }
        }
        event.setMessage(msg);
    }



}
