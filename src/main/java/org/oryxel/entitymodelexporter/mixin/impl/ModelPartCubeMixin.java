package org.oryxel.entitymodelexporter.mixin.impl;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.oryxel.entitymodelexporter.mixin.interfaces.IModelPartCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mixin(ModelPart.Cube.class)
public class ModelPartCubeMixin implements IModelPartCube {
    @Unique
    private final Map<Direction, float[]> hydraulic$uv = new HashMap<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectPolygonInit(int i, int j, float f, float g, float h, float k, float l, float m, float n, float o, float p, boolean bl, float q, float r, Set<Direction> set, CallbackInfo ci) {
        float w = (float)i;
        float x = (float)i + m;
        float y = (float)i + m + k;
        float z = (float)i + m + k + k;
        float aa = (float)i + m + k + m;
        float ab = (float)i + m + k + m + k;
        float ac = (float)j;
        float ad = (float)j + m;
        float ae = (float)j + m + l;

        if (set.contains(Direction.DOWN)) {
            hydraulic$uv.put(Direction.DOWN, new float[] {x, ac, y, ad});
        }

        if (set.contains(Direction.UP)) {
            hydraulic$uv.put(Direction.UP, new float[] {y, ad, z, ac});
        }

        if (set.contains(Direction.WEST)) {
            hydraulic$uv.put(Direction.WEST, new float[] {w, ad, x, ae});
        }

        if (set.contains(Direction.NORTH)) {
            hydraulic$uv.put(Direction.NORTH, new float[] {x, ad, y, ae});
        }

        if (set.contains(Direction.EAST)) {
            hydraulic$uv.put(Direction.EAST, new float[] {y, ad, aa, ae});
        }

        if (set.contains(Direction.SOUTH)) {
            hydraulic$uv.put(Direction.SOUTH, new float[] {aa, ad, ab, ae});
        }
    }

    @Override
    public Map<Direction, float[]> hydraulic$getUvs() {
        return hydraulic$uv;
    }
}
