package com.takutou.pl_dungeon.command;

import com.takutou.pl_dungeon.method.CreatedMobManager;
import com.takutou.pl_dungeon.method.SpawnedMobManager;
import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class GetDungeonMobList implements CommandExecutor {
    private CreatedMobManager createdMobManager;
    private SpawnedMobManager spawnedMobManager;
    public GetDungeonMobList(CreatedMobManager createdMobManager,SpawnedMobManager spawnedMobManager){
        this.createdMobManager = createdMobManager;
        this.spawnedMobManager = spawnedMobManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for(EntityObject createdMob : createdMobManager.getAllDungeonMobs()){//リストのモンスターすべて表示
            Map<String,Object> createdMobData =createdMob.getMonsterData();
            String mobName = (String) createdMobData.get("mobName");
            String mobType = createdMob.getClass().getSimpleName().toLowerCase();
            int mobSpeed = (int) createdMobData.get("speed");
            int mobMaxHealth = (int) createdMobData.get("maxHealth");
            int mobAttackDamage = (int) createdMobData.get("attackDamage");
            String mobKey = (String) createdMobData.get("mobKey");
            sender.sendMessage(
                            ChatColor.GREEN + "----------\n" +
                            ChatColor.GREEN + "MOBタイプ:" + mobType + "\n" +
                            ChatColor.GREEN + "名前　　:" + mobName + "\n" +
                            ChatColor.GREEN + "スピード:" + mobSpeed + "\n" +
                            ChatColor.GREEN + "体力　　:" + mobMaxHealth + "\n" +
                            ChatColor.GREEN + "攻撃力　:" + mobAttackDamage + "\n" +
                            ChatColor.GREEN + "mobKey:" + mobKey + "\n" +
                            ChatColor.GREEN + "----------" + "\n"
            );
        }
        for(EntityObject mob : spawnedMobManager.getAllDungeonMobs()){//リストのモンスターすべて表示
            Map<String,Object> createdMobData =mob.getMonsterData();
            String mobName = (String) createdMobData.get("mobName");
            String mobType = mob.getClass().getSimpleName().toLowerCase();
            int mobSpeed = (int) createdMobData.get("speed");
            int mobMaxHealth = (int) createdMobData.get("maxHealth");
            int mobAttackDamage = (int) createdMobData.get("attackDamage");
            String mobKey = (String) createdMobData.get("mobKey");
            double locX =  mob.getMonsterSpawnLocation().getX();
            double locY =  mob.getMonsterSpawnLocation().getY();
            double locZ =  mob.getMonsterSpawnLocation().getZ();

            sender.sendMessage(
                    ChatColor.GREEN + "----------\n" +
                            ChatColor.GREEN + "MOBタイプ:" + mobType + "\n" +
                            ChatColor.GREEN + "名前　　:" + mobName + "\n" +
                            ChatColor.GREEN + "スピード:" + mobSpeed + "\n" +
                            ChatColor.GREEN + "体力　　:" + mobMaxHealth + "\n" +
                            ChatColor.GREEN + "攻撃力　:" + mobAttackDamage + "\n" +
                            ChatColor.GREEN + "mobKey:" + mobKey + "\n" +
                            ChatColor.GREEN + "座標:" + locX + "l" + locY +"l"+ locZ +"\n" +
                            ChatColor.GREEN + "id:" + mob.getMonsterID() +"\n" +
                            ChatColor.GREEN + "----------" + "\n"
            );
        }
        return true;
    }
}
