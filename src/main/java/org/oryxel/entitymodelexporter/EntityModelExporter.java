package org.oryxel.entitymodelexporter;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import org.oryxel.entitymodelexporter.util.GeometryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class EntityModelExporter implements ModInitializer {
	public static final String MOD_ID = "entitymodelexporter";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
        // TESTTTTTTTTTTTTTTTT
        try {
            Files.write(new File("test.json").toPath(),
                    GeometryUtil.toString(
                            GeometryUtil.fromModelPart(
                                    HangingSignRenderer.createHangingSignLayer(HangingSignRenderer.AttachmentType.WALL).bakeRoot(), "geometry.test", 64, 32)).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}