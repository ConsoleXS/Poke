package com.consolex.poke.commands;

import com.consolex.poke.Poke;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PokeColors implements CommandExecutor {
    private final Poke plugin;

    public PokeColors(Poke plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (args.length == 1)
            {
                if (plugin.getColorPreferences().containsKey(player))
                {
                    if (args[0].equalsIgnoreCase("RED"))
                    {
                        plugin.getColorPreferences().replace(player, ChatColor.RED);
                        player.sendMessage(ChatColor.RED + "Poke color set to red!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1 ,1);
                    }
                    else if (args[0].equalsIgnoreCase("BLUE"))
                    {
                        plugin.getColorPreferences().replace(player, ChatColor.BLUE);
                        player.sendMessage(ChatColor.BLUE + "Poke color set to blue!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1 ,1);
                    }
                    else if (args[0].equalsIgnoreCase("GREEN"))
                    {
                        plugin.getColorPreferences().replace(player, ChatColor.GREEN);
                        player.sendMessage(ChatColor.GREEN + "Poke color set to green!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1 ,1);
                    }
                    else if (args[0].equalsIgnoreCase("PURPLE"))
                    {
                        plugin.getColorPreferences().replace(player, ChatColor.LIGHT_PURPLE);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Poke color set to purple!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1 ,1);
                    }
                    else if (args[0].equalsIgnoreCase("YELLOW"))
                    {
                        plugin.getColorPreferences().replace(player, ChatColor.YELLOW);
                        player.sendMessage(ChatColor.YELLOW + "Poke color set to yellow!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1 ,1);
                    }
                }
                return true;
            }
            else
            {
                return false;
            }

        }
        return true;
    }
}
