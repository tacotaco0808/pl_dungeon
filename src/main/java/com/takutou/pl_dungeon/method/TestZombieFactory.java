package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.plugin.Plugin;

// TestZombie用のファクトリクラス
public class TestZombieFactory implements MobFactory {
    @Override
    public EntityObject createMob(String mobName, int speed, int maxHealth, int attackDamage, Plugin plugin) {
        return new TestZombie(mobName, speed, maxHealth, attackDamage, plugin);
    }
}
