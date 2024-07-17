package com.takutou.pl_dungeon.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DungeonZombieSpawnEgg implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player =(Player) sender;
            ItemStack egg = new ItemStack(Material.ZOMBIE_SPAWN_EGG,1);
            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(ChatColor.RED + "DungeonZombie");
            eggMeta.setLore(Arrays.asList(ChatColor.GRAY+"Spawns a dungeon zombie"));
            egg.setItemMeta(eggMeta);
            player.getInventory().addItem(egg);
        }
        return true;
        
    }
}
