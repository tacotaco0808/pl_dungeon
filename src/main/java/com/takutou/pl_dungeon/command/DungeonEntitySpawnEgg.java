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
        if(args.length < 2){
            sender.sendMessage("/dungeon <entity> <maxhealth>");
            return true;
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            String entityName = args[0];
            Material entityTpe ;
            //バリデーション
            if(entityName.equalsIgnoreCase("zombie")){
                entityTpe = Material.ZOMBIE_SPAWN_EGG;
            }else if(entityName.equalsIgnoreCase("skeleton")){
                entityTpe = Material.SKELETON_SPAWN_EGG;
            }else{
                sender.sendMessage(ChatColor.RED + "error:例 zombie,skeleton");
                return true;
            }

            ItemStack egg = new ItemStack(entityTpe,1);

            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(ChatColor.GRAY + "Dungeon:" + entityName);
            eggMeta.setLore(Arrays.asList(ChatColor.BLUE + "Spawns a dungeon:" + entityName));
            egg.setItemMeta(eggMeta);

            player.getInventory().addItem(egg);
        }
        return true;
    }
}
