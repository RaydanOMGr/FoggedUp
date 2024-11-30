package me.andreasmelone.foggedup.capability;

import com.mojang.blaze3d.shaders.FogShape;
import me.andreasmelone.foggedup.util.OptionalFloat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class FogDataCapabilityImpl implements IFogDataCapability {
    @Nullable private FogShape fogShape = null;
    @Nullable private Float fogStart = null;
    @Nullable private Float fogEnd = null;

    @Override
    public @NotNull Optional<FogShape> getFogShape() {
        return Optional.ofNullable(fogShape);
    }

    @Override
    public void setFogShape(@NotNull FogShape fogShape) {
        Objects.requireNonNull(fogShape, "fogShape cannot be directly set to null");
        this.fogShape = fogShape;
    }

    @Override
    public void clearFogShape() {
        this.fogShape = null;
    }

    @Override
    public @NotNull OptionalFloat getFogStart() {
        return OptionalFloat.ofNullable(this.fogStart);
    }

    @Override
    public void setFogStart(float fogStart) {
        this.fogStart = fogStart;
    }

    @Override
    public void clearFogStart() {
        this.fogStart = null;
    }

    @Override
    public @NotNull OptionalFloat getFogEnd() {
        return OptionalFloat.ofNullable(this.fogEnd);
    }

    @Override
    public void setFogEnd(float fogEnd) {
        this.fogEnd = fogEnd;
    }

    @Override
    public void clearFogEnd() {
        this.fogEnd = null;
    }
}
