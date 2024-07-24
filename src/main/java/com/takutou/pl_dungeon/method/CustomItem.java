package com.takutou.pl_dungeon.method;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CustomItem {
    private JavaPlugin plugin;
    private Material materialType;
    public CustomItem (Material materialType, JavaPlugin plugin){
        this.materialType = materialType;
        this.plugin = plugin;
    }
    public ItemStack createCustomItem(String itemName ,String customID){
        ItemStack item = new ItemStack(materialType,1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin,"custom_id");
        meta.getPersistentDataContainer().set(key , PersistentDataType.STRING,customID);
        meta.setDisplayName(ChatColor.BLUE + itemName);
        meta.setLore(Arrays.asList(ChatColor.BLUE + "Spawns a dungeon mob ID:"+customID));

        item.setItemMeta(meta);
        return item;
    }

}
