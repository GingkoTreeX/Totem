package net.GingkoTreeX.Totem.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

import java.util.List;


public class AuraUtil {
    // 玩家 搜索半径和搜索范围
    public void attackNearestEntity(PlayerEntity player,double reach) {


        List<PlayerEntity> playerList = (List<PlayerEntity>) player.world.getPlayers();
        for (PlayerEntity target : playerList) {
            // 如果目标实体是自己，则跳过
            if (target == player) {
                continue;
            }

            // 计算目标实体与自己的距离
            double distance = player.distanceTo(target);

            // 如果目标实体在搜索半径内，则进行攻击

            if (distance <= reach) {
                player.setSprinting(true);
                player.setOnGround(false);
                MinecraftClient client = MinecraftClient.getInstance();
                if (MinecraftClient.getInstance().interactionManager != null) {
                    MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                    player.resetLastAttackedTicks();
                    player.setVelocity(4, 0, -4);
                    MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                }
                player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                player.setHeadYaw(player.headYaw + 90L);//扭头，很吊，看起来帅
                PlayerInteractEntityC2SPacket.attack(target, false);
                player.resetLastAttackedTicks();

                player.setVelocity(-4, 0, 4);
                PlayerInteractEntityC2SPacket.attack(target, true);


            }
            if (player.getHealth() < 6) {
                if (distance <= 60) {
                    // 制裁飞行
                    if (player.getY() < target.getY() - 1)
                        player.setPos(player.getX(), player.getY() + 1, player.getZ());


                    if (distance > 30) {
                        player.setVelocity(
                                (target.getX() - player.getX()) / 6,
                                (target.getY() - player.getY()) / 6,
                                (target.getZ() - player.getZ()) / 6
                        );
                    } else if (distance > 5) { // distance <= 30
                        player.setVelocity(
                                (target.getX() - player.getX()) / 3,
                                (target.getY() - player.getY()) / 6,
                                (target.getZ() - player.getZ()) / 3
                        );
                    } else {
                        player.setVelocity(
                                (target.getX() - player.getX()) * 2,
                                (target.getY() - player.getY()) * 2,
                                (target.getZ() - player.getZ()) * 2);
                    }

                    player.setOnGround(false);
                }

            }else {player.setVelocity(3, 3, 3);}
        }
    }

}
