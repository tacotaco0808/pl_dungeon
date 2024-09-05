package com.takutou.pl_dungeon.command;

import com.takutou.pl_dungeon.method.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CreateDungeonMob implements CommandExecutor {
    private CreatedMobManager createdMobManager;
    private Map<String, MobFactory> mobFactoryMap;

    public CreateDungeonMob(CreatedMobManager mobManager) {
        this.createdMobManager = mobManager;
        this.mobFactoryMap = new HashMap<>();
        // モブタイプに対応するファクトリクラスをマッピング
        mobFactoryMap.put("testzombie", new TestZombieFactory());
        mobFactoryMap.put("dungeonzombie", new DungeonZombieFactory());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 5) {
                sender.sendMessage("Usage: /dungeoncreate <mobType> <mobName> <speed> <maxHealth> <attackDamage>");
                sender.sendMessage(ChatColor.RED + "Error: Invalid mob type. Valid types are: " + mobFactoryMap.keySet());
                return false;
            }

            Player player = (Player) sender;
            Location location = player.getLocation();
            try {
                String mobType = args[0];
                String mobName = args[1];
                int speed = Integer.parseInt(args[2]);
                int maxHealth = Integer.parseInt(args[3]);
                int attackDamage = Integer.parseInt(args[4]);

                // モブタイプに対応するファクトリを取得
                MobFactory factory = mobFactoryMap.get(mobType.toLowerCase());
                //Error
                if (factory == null) {
                    player.sendMessage(ChatColor.RED + "Error: Invalid mob type. Valid types are: " + mobFactoryMap.keySet());
                    return false;
                }
                // ファクトリを使ってモブを生成し、作成したmobリストにプッシュ
                createdMobManager.pushCreatedDungeonMob(factory, mobName, speed, maxHealth, attackDamage);

                player.sendMessage("Mob created successfully!");
            } catch (NumberFormatException e) {
                player.sendMessage("Speed, maxHealth, and attackDamage must be integers.");
                return false;
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Error:\n" + e.getMessage());
                return false;
            } catch (Exception e) {
                player.sendMessage("An error occurred while spawning the mob.");
                e.printStackTrace();
                return false;
            }
            return true;
        }
        sender.sendMessage("This command can only be executed by a player.");
        return false;
    }
}
