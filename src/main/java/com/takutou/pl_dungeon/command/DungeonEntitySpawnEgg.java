package com.takutou.pl_dungeon.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DungeonEntitySpawnEgg implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(args.length != 1){
            sender.sendMessage("/dungeon num");
            return true;
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            int entityNum = Integer.parseInt(args[0]);

            ItemStack egg = new ItemStack(Material.ZOMBIE_SPAWN_EGG,1);
            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(ChatColor.GRAY + "Dungeon:" + entityNum);
            eggMeta.setLore(Arrays.asList(ChatColor.BLUE + "Spawns a dungeon mob" + "num:"+entityNum));
            egg.setItemMeta(eggMeta);

            player.getInventory().addItem(egg);
        }
        return true;
    }
}
