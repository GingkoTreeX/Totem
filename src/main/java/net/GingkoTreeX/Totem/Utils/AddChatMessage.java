package net.GingkoTreeX.Totem.Utils;


import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class AddChatMessage {
    public void addGreenChatMessage(String message) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                style.withColor(Formatting.GREEN)
                        .withBold(true)),false);
    }

    public void addRedChatMessage(String message) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                style.withColor(Formatting.RED)
                        .withBold(true)),false);
    }

    public void addPinkChatMessage(String message) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                style.withColor(Formatting.LIGHT_PURPLE)
                        .withBold(true)),false);
    }
}
