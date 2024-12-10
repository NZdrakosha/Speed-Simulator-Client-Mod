package client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class SphereEvent {
    public static Map<String, Boolean> activeSphere = new HashMap<>();
    public static void initMap(){
        activeSphere.put("green", true);
        activeSphere.put("red", true);
        activeSphere.put("yellow", true);
        activeSphere.put("blue", true);
    }

    @SubscribeEvent
    public void onRenderSphereYellow(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity cameraEntity = mc.getRenderViewEntity();
        if (cameraEntity == null) return;

        double cameraX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * event.getPartialTicks();
        double cameraY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * event.getPartialTicks();
        double cameraZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * event.getPartialTicks();

        if (activeSphere.get("yellow")) {
            optionsSphere(-29, 199.500, 414, 0.4f, 32, 32, 1.0f, 1.0f, 0.0f, 0.7f, cameraX, cameraY, cameraZ);
        }
        if(activeSphere.get("red")){
            optionsSphere(-29, 199.500, 410, 0.4f, 32, 32, 1.0f, 0.0f, 0.0f, 0.7f, cameraX, cameraY, cameraZ);
        }
        if (activeSphere.get("blue")){
            optionsSphere(-29, 199.500, 406, 0.4f, 32, 32, 0.0f, 0.0f, 1.0f, 0.7f, cameraX, cameraY, cameraZ);
        }
        if (activeSphere.get("green")){
            optionsSphere(-29, 199.500, 402, 0.4f, 32, 32, 0.0f, 1.0f, 0.0f, 0.7f, cameraX, cameraY, cameraZ );
        }
    }

    private static void optionsSphere(double x, double y, double z, float radius, int slices, int stacks, float red, float green, float blue, float alpha, double cameraX, double cameraY, double cameraZ){
        GL11.glPushMatrix();
        GL11.glTranslated(x - cameraX, y - cameraY, z - cameraZ);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        renderSphere(radius, slices, stacks, red, green, blue, alpha);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }

    public static void renderSphere(float radius, int slices, int stacks, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        for (int i = 0; i < stacks; i++) {
            double theta1 = Math.PI * i / stacks;
            double theta2 = Math.PI * (i + 1) / stacks;

            buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);

            for (int j = 0; j <= slices; j++) {
                double phi = 2 * Math.PI * j / slices;

                double x1 = radius * Math.sin(theta1) * Math.cos(phi);
                double y1 = radius * Math.cos(theta1);
                double z1 = radius * Math.sin(theta1) * Math.sin(phi);

                double x2 = radius * Math.sin(theta2) * Math.cos(phi);
                double y2 = radius * Math.cos(theta2);
                double z2 = radius * Math.sin(theta2) * Math.sin(phi);

                buffer.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                buffer.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            }

            tessellator.draw();
        }
    }

}
