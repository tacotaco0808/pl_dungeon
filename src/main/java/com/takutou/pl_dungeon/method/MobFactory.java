package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.plugin.Plugin;

// モブの生成処理を担当するインターフェース
public interface MobFactory {
    EntityObject createMob(String mobName, int speed, int maxHealth, int attackDamage, Plugin plugin);
}
