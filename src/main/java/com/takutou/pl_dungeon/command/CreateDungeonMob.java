package com.takutou.pl_dungeon.command;

import com.takutou.pl_dungeon.method.MobManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CreateDungeonMob implements CommandExecutor {
    private MobManager mobManager;

    public CreateDungeonMob(MobManager mobManager) {
        this.mobManager = mobManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 5) {
                sender.sendMessage("Usage: /dungeoncreate <mobType> <mobName> <speed> <maxHealth> <attackDamage>");
                return false;
            }
            /*プレイヤーの位置に見本を召喚*/
            Player player = (Player) sender;
            Location location = player.getLocation();
            try {
                String mobType = args[0];
                String mobName = args[1];
                int speed = Integer.parseInt(args[2]);
                int maxHealth = Integer.parseInt(args[3]);
                int attackDamage = Integer.parseInt(args[4]);

                mobManager.spawnDungeonMob(mobType, mobName, speed, maxHealth, attackDamage, location, player);
                player.sendMessage("Mob spawned successfully!");
            } catch (NumberFormatException e) {
                player.sendMessage("Speed, maxHealth, and attackDamage must be integers.");
                return false;
            }catch (IllegalArgumentException e){
                player.sendMessage(ChatColor.RED+ "Error:\n"+ e.getMessage());
                return false;
            }catch (Exception e) {
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
