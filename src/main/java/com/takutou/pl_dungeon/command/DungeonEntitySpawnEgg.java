package com.takutou.pl_dungeon.command;

import com.takutou.pl_dungeon.method.CustomItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class DungeonEntitySpawnEgg implements CommandExecutor {
    JavaPlugin plugin;
    public DungeonEntitySpawnEgg(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(args.length != 1){
            sender.sendMessage("/dungeon <itemID>");
            return true;
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            String entityNum = args[0];
            CustomItem customEgg = new CustomItem(Material.ZOMBIE_SPAWN_EGG,plugin);
            ItemStack egg = customEgg.createCustomItem("test",entityNum);
            player.getInventory().addItem(egg);

//            ItemStack egg = new ItemStack(Material.ZOMBIE_SPAWN_EGG,1);
//            ItemMeta eggMeta = egg.getItemMeta();
//            eggMeta.setDisplayName(ChatColor.GRAY + "Dungeon:" + entityNum);
//            eggMeta.setLore(Arrays.asList(ChatColor.BLUE + "Spawns a dungeon mob" + "num:"+entityNum));
//            egg.setItemMeta(eggMeta);

//            player.getInventory().addItem(egg);
        }
        return true;
    }
}
