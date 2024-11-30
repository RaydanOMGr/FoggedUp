package me.andreasmelone.foggedup.capability;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.logging.LogUtils;
import me.andreasmelone.foggedup.util.OptionalFloat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;

public class FogDataCapability {
    protected static final Capability<IFogDataCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IFogDataCapability.class);
    }

    static IFogDataCapability localCapability = new FogDataCapabilityImpl();

    public static @NotNull IFogDataCapability getCapability(@NotNull Player player) {
        if(player.isLocalPlayer()) return localCapability;
        return player.getCapability(INSTANCE)
                .orElseThrow(() -> new IllegalStateException("Could not get FogData capability from player"));
    }
    
    public static @NotNull Optional<FogShape> getFogShape(@NotNull Player player) {
        return getCapability(player).getFogShape();
    }

    public static void setFogShape(@NotNull Player player, @NotNull FogShape fogShape) {
        getCapability(player).setFogShape(fogShape);
    }
    
    public static void clearFogShape(@NotNull Player player) {
        getCapability(player).clearFogShape();
    }

    public static @NotNull OptionalFloat getFogStart(@NotNull Player player) {
        return getCapability(player).getFogStart();
    }

    public static void setFogStart(@NotNull Player player, float fogStart) {
        getCapability(player).setFogStart(fogStart);
    }

    public static void clearFogStart(@NotNull Player player) {
        getCapability(player).clearFogStart();
    }

    public static @NotNull OptionalFloat getFogEnd(@NotNull Player player) {
        return getCapability(player).getFogEnd();
    }

    public static void setFogEnd(@NotNull Player player, float fogEnd) {
        getCapability(player).setFogEnd(fogEnd);
    }

    public static void clearFogEnd(@NotNull Player player) {
        getCapability(player).clearFogEnd();
    }

    public static CompoundTag serialize(@NotNull IFogDataCapability capability) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("version", FogDataHandler.VERSION);
        capability.getFogShape().ifPresent(
                shape -> tag.putInt("fogShape", shape.getIndex())
        );
        capability.getFogStart().ifPresent(
                start -> tag.putFloat("fogStart", start)
        );
        capability.getFogEnd().ifPresent(
                end -> tag.putFloat("fogEnd", end)
        );
        return tag;
    }

    public static void deserialize(@NotNull IFogDataCapability capability, @NotNull CompoundTag tag) {
        int version = tag.getInt("version");
        if(version != FogDataHandler.VERSION) {
            LOGGER.warn("Capability data of older format found!");
            LOGGER.warn("Discarding all current data, this will lead to a full reset of the mod");
            return;
        }
        capability.clearFogShape();
        if(tag.contains("fogShape")) {
            capability.setFogShape(FogShape.values()[tag.getInt("fogShape")]);
        }
        capability.clearFogStart();
        if(tag.contains("fogStart")) {
            capability.setFogStart(tag.getFloat("fogStart"));
        }
        capability.clearFogEnd();
        if(tag.contains("fogEnd")) {
            capability.setFogEnd(tag.getFloat("fogEnd"));
        }
    }
}
