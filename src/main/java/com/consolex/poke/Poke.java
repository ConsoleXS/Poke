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
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Poke Enabled! " + ChatColor.AQUA + "@1.0");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Poke Disabled!");
    }


    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event)
    {
        String msg = event.getMessage();
        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if (msg.contains("@" + p.getName()))
            {
                msg =  msg.replaceAll("@" + p.getName(), ChatColor.YELLOW + "@" + p.getName() + ChatColor.WHITE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,  1);
            }
        }
        event.setMessage(msg);
    }



}
