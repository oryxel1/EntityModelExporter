package org.oryxel.entitymodelexporter.mixin.impl;

import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ModelPart.class)
public interface ModelPartAccessor {
    @Accessor
    List<ModelPart.Cube> getCubes();
}
