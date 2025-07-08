package org.oryxel.entitymodelexporter.mixin.interfaces;

import net.minecraft.core.Direction;

import java.util.Map;

public interface IModelPartCube {
    Map<Direction, float[]> hydraulic$getUvs();
}
