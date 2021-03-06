package code.elix_x.mods.fei.utils;

import com.mmyzd.llor.LightLevelOverlayReloaded;

import at.feldim2425.moreoverlays.lightoverlay.LightOverlayHandler;
import code.elix_x.mods.fei.ForeverEnoughItemsBase;
import code.elix_x.mods.fei.api.client.IRenderable;
import code.elix_x.mods.fei.api.client.IRenderable.ResourceLocationRenderable;
import code.elix_x.mods.fei.api.utils.ForFEIUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightLevelOverlayToggleFEIUtil extends ForFEIUtil<Integer> {

	public static final String[] descs = {"off", "cross", "number"};
	public static final ResourceLocation[] icons = {new ResourceLocation(ForeverEnoughItemsBase.MODID, "textures/icons/lightoverlay_off.png"), new ResourceLocation(ForeverEnoughItemsBase.MODID, "textures/icons/lightoverlay_cross.png"), new ResourceLocation(ForeverEnoughItemsBase.MODID, "textures/icons/lightoverlay_number.png")};

	public final boolean llor;
	public final boolean moreoverlays;

	public LightLevelOverlayToggleFEIUtil(boolean llor, boolean moreoverlays){
		super("LLOR Toggle", llor && moreoverlays ? new Integer[]{0, 1, 2} : moreoverlays ? new Integer[]{0, 1} : llor ? new Integer[]{0, 2} : new Integer[]{0});
		this.llor = llor;
		this.moreoverlays = moreoverlays;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Integer getCurrent(){
		return moreoverlays && LightOverlayHandler.enabled ? 1 : llor && LightLevelOverlayReloaded.instance.active ? 2 : 0;
	}

	@Override
	public String getDesc(Integer i){
		return I18n.translateToLocal("fei.gui.override.grid.utils.lightoverlay." + descs[i]);
	}

	@Override
	public boolean isEnabled(Integer i){
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onSelect(Integer i){
		if(i == 0){
			if(moreoverlays) LightOverlayHandler.enabled = false;
			if(llor) LightLevelOverlayReloaded.instance.active = false;
		} else if(i == 1){
			if(moreoverlays) LightOverlayHandler.enabled = true;
			if(llor) LightLevelOverlayReloaded.instance.active = false;
		} else if(i == 2){
			if(moreoverlays) LightOverlayHandler.enabled = false;
			if(llor) LightLevelOverlayReloaded.instance.active = true;
		}
	}

	@Override
	public IRenderable getRenderable(Integer i){
		return new ResourceLocationRenderable(icons[i]);
	}

}
