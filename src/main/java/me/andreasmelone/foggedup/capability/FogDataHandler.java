package me.andreasmelone.foggedup.capability;

import me.andreasmelone.foggedup.FoggedUp;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FogDataHandler implements INBTSerializable<CompoundTag>, ICapabilityProvider {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(FoggedUp.MOD_ID, "fogdata");
    public static final int VERSION = 1;

    IFogDataCapability capability = new FogDataCapabilityImpl();
    LazyOptional<IFogDataCapability> instance = LazyOptional.of(() -> capability);

    @Override
    public CompoundTag serializeNBT() {
        return FogDataCapability.serialize(capability);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        FogDataCapability.deserialize(capability, nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return FogDataCapability.INSTANCE.orEmpty(cap, instance);
    }

    @SubscribeEvent
    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            FogDataHandler fogDataHandler = new FogDataHandler();
            event.addCapability(FogDataHandler.IDENTIFIER, fogDataHandler);
        }
    }
}
