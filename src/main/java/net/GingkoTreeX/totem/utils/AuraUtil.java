package net.GingkoTreeX.totem.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.List;

import static net.GingkoTreeX.totem.utils.IMinecraft.mc;


@SuppressWarnings("unchecked")
public class AuraUtil {
    // 玩家 搜索半径和搜索范围
    public static Entity getNearestEntityFromReach(PlayerEntity player, double reach) {
        List<Entity> playerList = null ;
        if (mc.world != null) {
            Box searchArea = new Box(
                    player.getX() - reach,
                    player.getY() - reach,
                    player.getZ() - reach,
                    player.getX() + reach,
                    player.getY() + reach,
                    player.getZ() + reach
            );
            playerList = mc.world.getOtherEntities(player,searchArea);
        }
        if (playerList != null) {
            for (Entity target : playerList) {
                // 如果目标实体是自己，则跳过
                if (target == player) {
                    continue;
                }
                if (!target.isAttackable()) {
                    continue;
                }
                // 计算目标实体与自己的距离
                double distance = player.distanceTo(target);
                // 如果目标实体在搜索半径内，则返回
                if (distance <= reach) {
                    return target;
                }
            }
        }

        return null;
    }
    public static List<PlayerEntity> getPlayerFromWorld(PlayerEntity player) {
        return (List<PlayerEntity>) player.world.getPlayers();
    }
}
