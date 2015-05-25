package blusunrize.immersiveengineering.client.render;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.client.models.ModelDieselGenerator;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityDieselGenerator;

public class TileRenderDieselGenerator extends TileEntitySpecialRenderer
{
	static ModelDieselGenerator model = new ModelDieselGenerator();
	static IModelCustom objmodel = ClientUtils.getModel("immersiveengineering:models/dieselGenerator.obj");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		if(GuiScreen.isShiftKeyDown()) objmodel = ClientUtils.getModel("immersiveengineering:models/dieselGenerator.obj");

		TileEntityDieselGenerator gen = (TileEntityDieselGenerator)tile;
		if(!gen.formed || gen.pos!=31)
			return;
		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);
		GL11.glTranslated(+.5, +.5, +.5);
		
		GL11.glRotatef(gen.facing==3?180: gen.facing==4?90: gen.facing==5?-90: 0, 0,1,0);
		ClientUtils.bindTexture("immersiveengineering:textures/models/dieselGenNew.png");
		objmodel.renderAllExcept("fan");

		GL11.glTranslated(0, .1875, 2.96875);
		GL11.glRotatef(gen.fanRotation+(gen.fanRotationStep*f), 0,0,1);
		objmodel.renderOnly("fan");
		

		GL11.glPopMatrix();
	}
}