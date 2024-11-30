package me.andreasmelone.foggedup.packet;

import com.mojang.blaze3d.shaders.FogShape;
import me.andreasmelone.foggedup.capability.FogDataCapability;
import me.andreasmelone.foggedup.capability.FogDataCapabilityImpl;
import me.andreasmelone.foggedup.capability.IFogDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncFogDataPacket {
    private final IFogDataCapability capability;

    public SyncFogDataPacket(IFogDataCapability capability) {
        this.capability = capability;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(capability.getFogShape().isPresent());
        capability.getFogShape().ifPresent(value -> buf.writeInt(value.getIndex()));

        buf.writeBoolean(capability.getFogStart().isPresent());
        capability.getFogStart().ifPresent(buf::writeFloat);

        buf.writeBoolean(capability.getFogEnd().isPresent());
        capability.getFogEnd().ifPresent(buf::writeFloat);
    }

    public static SyncFogDataPacket decode(FriendlyByteBuf buf) {
        IFogDataCapability capability = new FogDataCapabilityImpl();
        if(buf.readBoolean()) capability.setFogShape(FogShape.values()[buf.readInt()]);
        if(buf.readBoolean()) capability.setFogStart(buf.readFloat());
        if(buf.readBoolean()) capability.setFogEnd(buf.readFloat());
        return new SyncFogDataPacket(capability);
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if(context.getDirection().getReceptionSide().isServer()) return;
            Player player = Minecraft.getInstance().player;
            if(player == null) return;

            FogDataCapability.clearFogShape(player);
            capability.getFogShape().ifPresent(value -> FogDataCapability.setFogShape(player, value));
            FogDataCapability.clearFogStart(player);
            capability.getFogStart().ifPresent(value -> FogDataCapability.setFogStart(player, value));
            FogDataCapability.clearFogEnd(player);
            capability.getFogEnd().ifPresent(value -> FogDataCapability.setFogEnd(player, value));
        });
        context.setPacketHandled(true);
    }
}
