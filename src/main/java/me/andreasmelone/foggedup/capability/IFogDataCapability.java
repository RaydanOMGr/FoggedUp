package me.andreasmelone.foggedup.capability;

import com.mojang.blaze3d.shaders.FogShape;
import me.andreasmelone.foggedup.util.OptionalFloat;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface IFogDataCapability {
    /**
     * Gets the fog shape for this player
     * @return The current fog shape, in case of null, uses default
     */
    @NotNull Optional<FogShape> getFogShape();

    /**
     * Sets the fog shape for this player
     * @param fogShape The new fog shape
     */
    void setFogShape(@NotNull FogShape fogShape);

    /**
     * Removes the fog shape for this player, thereby setting it to default
     */
    void clearFogShape();

    /**
     * Gets the fog start value for this player
     *
     * @return The fog start value as an optional float, as it can be null
     *         if set to default/cleared
     */
    @NotNull OptionalFloat getFogStart();

    /**
     * Sets the fog start value for this player
     * @param fogStart The fog start value
     */
    void setFogStart(float fogStart);

    /**
     * Removes the fog start value for this player, thereby setting it to default
     */
    void clearFogStart();

    /**
     * Gets the fog end value for this player
     * @return The fog end value as an optional float
     */
    @NotNull OptionalFloat getFogEnd();

    /**
     * Sets the fog end value for this player
     * @param fogEnd The fog end value, as it can be null
     *               if set to default/cleared
     */
    void setFogEnd(float fogEnd);

    /**
     * Removes the fog end value for this player, thereby setting it to default
     */
    void clearFogEnd();
}
