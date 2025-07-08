package org.oryxel.entitymodelexporter.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.*;
import org.oryxel.entitymodelexporter.mixin.impl.ModelPartAccessor;
import org.oryxel.entitymodelexporter.mixin.interfaces.IModelPartCube;
import org.oryxel.entitymodelexporter.util.gson.EmptyArrayAdapterFactory;
import org.oryxel.entitymodelexporter.util.gson.EmptyMapAdapterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeometryUtil {
    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(new EmptyArrayAdapterFactory())
            .registerTypeAdapterFactory(new EmptyMapAdapterFactory())
            .create();

    public static String toString(ModelEntity model) {
        return GSON.toJson(model);
    }

    /**
     * Create a model entity from model part root
     *
     * @param model the root model part of the model
     * @param geoName the name of the geometry
     * @return the created model entity
     */
    public static ModelEntity fromModelPart(ModelPart model, String geoName, int width, int height) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion("1.16.0");

        Geometry geometry = new Geometry();

        Description description = new Description();
        description.identifier(geoName);
        description.textureWidth(width);
        description.textureHeight(height);
        description.visibleBoundsWidth(2);
        description.visibleBoundsHeight(2);
        description.visibleBoundsOffset(new float[] { 0.0f, 0.25f, 0.0f });
        geometry.description(description);

        List<Bones> bones = new ArrayList<>();


        String lastPartName = null;
        for (ModelPart part : model.getAllParts()) {
            Bones bone = new Bones();
            bones.add(bone);

            bone.rotation(new float[] {part.xRot, part.yRot, part.zRot});
            bone.pivot(new float[] { part.x, part.y, part.z });
            bone.name("p_" + part.hashCode());
            if (lastPartName != null) {
                bone.parent(lastPartName);
            }

            lastPartName = "p_" + part.hashCode();

            ((ModelPartAccessor) ((Object) part)).getCubes().forEach(cube -> {
                Cubes cubes = new Cubes();

                float sizeY = cube.maxY - cube.minY;

                cubes.origin(new float[] {cube.minX, -(cube.minY + sizeY), cube.minZ});
                cubes.size(new float[] {cube.maxX - cube.minX, sizeY, cube.maxZ - cube.minZ});

                Uv uvs = new Uv();
                IModelPartCube iCube = (IModelPartCube) cube;
                for (Map.Entry<Direction, float[]> entry : iCube.hydraulic$getUvs().entrySet()) {
                    float[] uv = entry.getValue();
                    switch (entry.getKey()) {
                        case DOWN -> {
                            Down down = new Down();
                            down.uv(new float[]{uv[0], uv[1]});
                            down.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.down(down);
                        }
                        case UP -> {
                            Up up = new Up();
                            up.uv(new float[]{uv[0], uv[1]});
                            up.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.up(up);
                        }
                        case NORTH -> {
                            North north = new North();
                            north.uv(new float[]{uv[0], uv[1]});
                            north.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.north(north);
                        }
                        case SOUTH -> {
                            South south = new South();
                            south.uv(new float[]{uv[0], uv[1]});
                            south.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.south(south);
                        }
                        case WEST -> {
                            West west = new West();
                            west.uv(new float[]{uv[0], uv[1]});
                            west.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.west(west);
                        }
                        case EAST -> {
                            East east = new East();
                            east.uv(new float[]{uv[0], uv[1]});
                            east.uvSize(new float[]{uv[2] - uv[0], uv[3] - uv[1]});
                            uvs.east(east);
                        }
                    }
                }

                cubes.uv(uvs);
                bone.cubes().add(cubes);
            });
        }

        geometry.bones(bones);

        modelEntity.geometry(List.of(geometry));

        return modelEntity;
    }
}
