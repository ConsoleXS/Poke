package com.consolex.poke.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PokeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 9, "Poke Sound Preference");


            ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD, 1);
            ItemMeta amethyst_meta = amethyst.getItemMeta();
            amethyst_meta.setDisplayName(ChatColor.WHITE + "Ping: Amethyst block break");
            amethyst.setItemMeta(amethyst_meta);

            ItemStack respawnAnchor = new ItemStack(Material.RESPAWN_ANCHOR, 1);
            ItemMeta respawnAnchor_meta = respawnAnchor.getItemMeta();
            respawnAnchor_meta.setDisplayName(ChatColor.WHITE + "Ping: Respawn Anchor Fill");
            respawnAnchor.setItemMeta(respawnAnchor_meta);

            ItemStack bell = new ItemStack(Material.BELL, 1);
            ItemMeta bell_meta = bell.getItemMeta();
            bell_meta.setDisplayName(ChatColor.WHITE + "Ping: Bell hit");
            bell.setItemMeta(bell_meta);

            ItemStack slime = new ItemStack(Material.SLIME_BLOCK, 1);
            ItemMeta slime_meta = slime.getItemMeta();
            slime_meta.setDisplayName(ChatColor.WHITE + "Ping: Slimy sound");
            slime.setItemMeta(slime_meta);


            ItemStack pling = new ItemStack(Material.NOTE_BLOCK, 1);
            ItemMeta pling_meta = pling.getItemMeta();
            pling_meta.setDisplayName(ChatColor.WHITE + "Ping: Pling noteblock sound");
            pling.setItemMeta(pling_meta);


            ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta empty_meta = empty.getItemMeta();
            empty_meta.setDisplayName(ChatColor.RED + "Reset " + ChatColor.WHITE + "sound to default");
            empty.setItemMeta(empty_meta);

            ItemStack pingColors = new ItemStack(Material.RED_CONCRETE, 1);
            ItemMeta pingColors_meta = empty.getItemMeta();
            pingColors_meta.setDisplayName("Change poke " + ChatColor.RED + "C" + ChatColor.YELLOW + "o" + ChatColor.GREEN + "l" + ChatColor.BLUE + "o" + ChatColor.LIGHT_PURPLE + "r" + ChatColor.DARK_AQUA + "s");
            pingColors.setItemMeta(pingColors_meta);

            ItemStack[] soundPreferenceItems = {empty, empty, amethyst, respawnAnchor, bell, slime, pling, empty, pingColors};
            gui.setContents(soundPreferenceItems);
            player.openInventory(gui);

        }

        return true;
    }
}
