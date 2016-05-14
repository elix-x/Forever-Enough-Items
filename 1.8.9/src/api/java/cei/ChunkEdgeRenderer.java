package cei;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChunkEdgeRenderer
{
  private int chunkEdgeState = 0;
  
  @SubscribeEvent
  public void resetOverlay(WorldEvent.Load event)
  {
    this.chunkEdgeState = 0;
  }
  
  @SubscribeEvent
  public void getKeyPress(TickEvent.ClientTickEvent event)
  {
    if (Minecraft.getMinecraft().renderGlobal == null) {
      return;
    }
    if (ChunkEdgeIndicator.keyBindChunkOverlay.isKeyDown()) {
      this.chunkEdgeState = ((this.chunkEdgeState + 1) % 3);
    }
  }
  
  @SubscribeEvent
  public void renderChunkEdges(RenderWorldLastEvent event)
  {
    if (this.chunkEdgeState == 0) {
      return;
    }
    Entity entity = Minecraft.getMinecraft().thePlayer;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    GL11.glPushMatrix();
    float frame = event.partialTicks;
    double inChunkPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
    double inChunkPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
    double inChunkPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
    GL11.glTranslated(-inChunkPosX, -inChunkPosY, -inChunkPosZ);
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(2.0F);
    worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
    GL11.glTranslatef(entity.chunkCoordX * 16, 0.0F, entity.chunkCoordZ * 16);
    double x = 0.0D;
    double z = 0.0D;
    float redColourR = 0.9F;
    float redColourG = 0.0F;
    float redColourB = 0.0F;
    float redColourA = 1.0F;
    float greenColourR = 0.0F;
    float greenColourG = 0.9F;
    float greenColourB = 0.0F;
    float greenColourA = 0.4F;
    for (int chunkZ = -2; chunkZ <= 2; chunkZ++) {
      for (int chunkX = -2; chunkX <= 2; chunkX++) {
        if ((Math.abs(chunkX) != 2) || (Math.abs(chunkZ) != 2))
        {
          x = chunkX * 16;
          z = chunkZ * 16;
          redColourA = (float)Math.round(Math.pow(1.25D, -(chunkX * chunkX + chunkZ * chunkZ)) * 6.0D) / 6.0F;
          worldrenderer.pos(x, 0.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x, 256.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x + 16.0D, 0.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x + 16.0D, 256.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x, 0.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x, 256.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x + 16.0D, 0.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
          worldrenderer.pos(x + 16.0D, 256.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
        }
      }
    }
    x = z = 0.0D;
    if (this.chunkEdgeState == 2)
    {
      float eyeHeight = (float)(entity.getEyeHeight() + entity.posY);
      int eyeHeightBlock = (int)Math.floor(eyeHeight);
      
      int minY = Math.max(0, eyeHeightBlock - 16);
      int maxY = Math.min(256, eyeHeightBlock + 16);
      boolean renderedSome = false;
      for (int y = 0; y < 257; y++)
      {
        greenColourA = 0.4F;
        if (y < minY) {
          greenColourA = (float)(greenColourA - Math.pow(minY - y, 2.0D) / 500.0D);
        }
        if (y > maxY) {
          greenColourA = (float)(greenColourA - Math.pow(y - maxY, 2.0D) / 500.0D);
        }
        if (greenColourA < 0.01F)
        {
          if (renderedSome) {
            break;
          }
        }
        else
        {
          if (y < 256) {
            for (int n = 1; n < 16; n++)
            {
              worldrenderer.pos(n, y, z).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              worldrenderer.pos(n, y + 1, z).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              
              worldrenderer.pos(x, y, n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              worldrenderer.pos(x, y + 1, n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              
              worldrenderer.pos(n, y, z + 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              worldrenderer.pos(n, y + 1, z + 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              
              worldrenderer.pos(x + 16.0D, y, n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
              worldrenderer.pos(x + 16.0D, y + 1, n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
            }
          }
          worldrenderer.pos(0.0D, y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(16.0D, y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(0.0D, y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(0.0D, y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(16.0D, y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(16.0D, y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(0.0D, y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          worldrenderer.pos(16.0D, y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
          renderedSome = true;
        }
      }
    }
    tessellator.draw();
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
}