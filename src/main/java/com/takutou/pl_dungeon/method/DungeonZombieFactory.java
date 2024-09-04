package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.DungeonZombie;
import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.plugin.Plugin;

// DungeonZombie用のファクトリクラス
public class DungeonZombieFactory implements MobFactory {
    @Override
    public EntityObject createMob(String mobName, int speed, int maxHealth, int attackDamage, Plugin plugin) {
        return new DungeonZombie(mobName, speed, maxHealth, attackDamage, plugin);
    }
}
