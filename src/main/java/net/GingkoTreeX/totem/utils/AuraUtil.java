package net.GingkoTreeX.totem.utils;

import net.minecraft.entity.player.PlayerEntity;

import java.util.List;


public class AuraUtil {
    // 玩家 搜索半径和搜索范围
    public static PlayerEntity attackNearestEntity(PlayerEntity player,double reach) {
        @SuppressWarnings("unchecked") List<PlayerEntity> playerList = (List<PlayerEntity>) player.world.getPlayers();
        for (PlayerEntity target : playerList) {
            // 如果目标实体是自己，则跳过
            if (target == player) {
                continue;
            }
            // 计算目标实体与自己的距离
            double distance = player.distanceTo(target);
            // 如果目标实体在搜索半径内，则返回
            if (distance <= reach) {
            return target;
                }
            }
        return null;
        }
    }
