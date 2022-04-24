package com.consolex.poke;

import com.consolex.poke.commands.PokeCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        getCommand("poke").setExecutor(new PokeCommand());

        config.addDefault("poke-cooldown", 3);
        config.options().copyDefaults(true);
        saveConfig();

        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            pokePreference.put(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }
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

    @EventHandler
    public void setPokePreference(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase("Poke Sound Preference")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            switch (event.getCurrentItem().getType()) {
                case AMETHYST_SHARD:
                    pokePreference.replace(player, Sound.BLOCK_AMETHYST_BLOCK_BREAK);
                    break;
                case RESPAWN_ANCHOR:
                    pokePreference.replace(player, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE);
                    break;
                case BELL:
                    pokePreference.replace(player, Sound.BLOCK_BELL_USE);
                    break;
                case SLIME_BLOCK:
                    pokePreference.replace(player, Sound.ENTITY_ELDER_GUARDIAN_FLOP);
                    break;
                case NOTE_BLOCK:
                    pokePreference.replace(player, Sound.BLOCK_NOTE_BLOCK_PLING);
                    break;
                case GRAY_STAINED_GLASS:
                    pokePreference.replace(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    break;
            }
            player.playSound(player.getLocation(), pokePreference.get(player), 1, 1);
            player.sendMessage(ChatColor.AQUA + "New sound set!");
            player.closeInventory();
            event.setCancelled(true);
        }
    }
}
