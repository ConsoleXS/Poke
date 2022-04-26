package com.consolex.poke;

import com.consolex.poke.commands.PokeColors;
import com.consolex.poke.commands.PokeCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.Objects;

public final class Poke extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Poke Enabled! " + ChatColor.AQUA + "@1.0");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("poke").setExecutor(new PokeCommand());
        getCommand("pokecolor").setExecutor(new PokeColors(this));

        config.addDefault("poke-cooldown", 3);
        config.options().copyDefaults(true);
        saveConfig();

        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            pokePreference.put(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            pokeColors.put(p, ChatColor.YELLOW);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Poke Disabled!");
    }

    HashMap<Player, Sound> pokePreference = new HashMap<>();
    HashMap<Player, ChatColor> pokeColors = new HashMap<>();
    List<Player> inCooldown = new ArrayList<>();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (!pokePreference.containsKey(player))
        {
            pokePreference.put(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }
        if (!pokeColors.containsKey(player))
        {
            pokeColors.put(player, ChatColor.YELLOW);
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
                    msg =  msg.replaceAll("@" + p.getName(), pokeColors.get(event.getPlayer()) + "@" + p.getName() + ChatColor.WHITE);
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
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case AMETHYST_SHARD:
                    pokePreference.replace(player, Sound.BLOCK_AMETHYST_BLOCK_BREAK);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case RESPAWN_ANCHOR:
                    pokePreference.replace(player, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case BELL:
                    pokePreference.replace(player, Sound.BLOCK_BELL_USE);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case SLIME_BLOCK:
                    pokePreference.replace(player, Sound.ENTITY_ELDER_GUARDIAN_FLOP);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case NOTE_BLOCK:
                    pokePreference.replace(player, Sound.BLOCK_NOTE_BLOCK_PLING);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case GRAY_STAINED_GLASS:
                    pokePreference.replace(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    player.sendMessage(ChatColor.AQUA + "Preference added!");
                    break;
                case RED_CONCRETE:
                    player.sendMessage(ChatColor.BOLD + "-- Pick a ping color preference --");
                    TextComponent red = new TextComponent("Red");
                    red.setColor(net.md_5.bungee.api.ChatColor.RED);
                    red.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pokecolor red"));
                    red.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(net.md_5.bungee.api.ChatColor.RED + "Change poke color to red")));

                    TextComponent blue = new TextComponent("Blue");
                    blue.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                    blue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pokecolor blue"));
                    blue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(net.md_5.bungee.api.ChatColor.BLUE + "Change poke color to blue")));

                    TextComponent green = new TextComponent("Green");
                    green.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                    green.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pokecolor green"));
                    green.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(net.md_5.bungee.api.ChatColor.GREEN + "Change poke color to green")));

                    TextComponent purple = new TextComponent("Purple");
                    purple.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
                    purple.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pokecolor purple"));
                    purple.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE + "Change poke color to purple")));

                    TextComponent yellow = new TextComponent("Yellow");
                    yellow.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
                    yellow.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pokecolor yellow"));
                    yellow.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(net.md_5.bungee.api.ChatColor.YELLOW + "Change poke color to yellow")));

                    player.spigot().sendMessage(red);
                    player.spigot().sendMessage(blue);
                    player.spigot().sendMessage(green);
                    player.spigot().sendMessage(purple);
                    player.spigot().sendMessage(yellow);
                    break;
            }
            player.playSound(player.getLocation(), pokePreference.get(player), 1, 1);
            player.closeInventory();
            event.setCancelled(true);
        }
    }


    public HashMap<Player, ChatColor> getColorPreferences()
    {
        return pokeColors;
    }
}
