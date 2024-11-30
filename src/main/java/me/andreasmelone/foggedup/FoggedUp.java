package me.andreasmelone.foggedup;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import me.andreasmelone.foggedup.capability.FogDataCapability;
import me.andreasmelone.foggedup.capability.FogDataHandler;
import me.andreasmelone.foggedup.commands.GetFogCommand;
import me.andreasmelone.foggedup.commands.SetFogCommand;
import me.andreasmelone.foggedup.packet.PacketManager;
import me.andreasmelone.foggedup.packet.SyncFogDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(FoggedUp.MOD_ID)
public class FoggedUp {
    public static final String MOD_ID = "foggedup";

    public FoggedUp() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(FogDataHandler.class);
        MinecraftForge.EVENT_BUS.addListener(FogDataCapability::register);

        PacketManager.register(
                SyncFogDataPacket.class,
                SyncFogDataPacket::encode,
                SyncFogDataPacket::decode,
                SyncFogDataPacket::messageConsumer
        );
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
    }

    @SubscribeEvent
    public void onFogRenderer(ViewportEvent.RenderFog event) {
        Player player = Minecraft.getInstance().player;
        if(player == null) return;

        FogDataCapability.getFogShape(player).ifPresent(RenderSystem::setShaderFogShape);
        FogDataCapability.getFogStart(player).ifPresent(RenderSystem::setShaderFogStart);
        FogDataCapability.getFogEnd(player).ifPresent(RenderSystem::setShaderFogEnd);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        SetFogCommand.register(event.getDispatcher());
        GetFogCommand.register(event.getDispatcher());
    }
}
