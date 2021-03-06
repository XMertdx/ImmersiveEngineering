package blusunrize.immersiveengineering.common.blocks.multiblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalDecoration;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalMultiblocks;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySqueezer;

public class MultiblockSqueezer implements IMultiblock
{
	public static MultiblockSqueezer instance = new MultiblockSqueezer();
	static ItemStack[][][] structure = new ItemStack[3][3][3];
	static{
		for(int h=0;h<3;h++)
			for(int l=0;l<3;l++)
				for(int w=0;w<3;w++)
					structure[h][l][w]=(h==1&&(w!=1||l!=1))?new ItemStack(IEContent.blockMetalMultiblocks,1,BlockMetalMultiblocks.META_squeezer): new ItemStack(IEContent.blockMetalDecoration,1,BlockMetalDecoration.META_lightEngineering);
	}
	@Override
	public ItemStack[][][] getStructureManual()
	{
		return structure;
	}
	
	@Override
	public boolean isBlockTrigger(Block b, int meta)
	{
		return b==IEContent.blockMetalMultiblocks && (meta==BlockMetalMultiblocks.META_squeezer);
	}

	@Override
	public boolean createStructure(World world, int x, int y, int z, int side, EntityPlayer player)
	{
		if(side==0||side==1)
			return false;

		int startX=x;
		int startY=y;
		int startZ=z;
		
		for(int l=0;l<3;l++)
			for(int w=-1;w<=1;w++)
				for(int h=-1;h<=1;h++)
				{
					int xx = startX+ (side==4?l: side==5?-l: side==2?-w : w);
					int yy = startY+ h;
					int zz = startZ+ (side==2?l: side==3?-l: side==5?-w : w);
					if(h==0&&(w!=0||l!=1))
					{
						if(!(world.getBlock(xx, yy, zz).equals(IEContent.blockMetalMultiblocks) && world.getBlockMetadata(xx, yy, zz)==BlockMetalMultiblocks.META_squeezer))
							return false;
					}
					else
					{
						if(!(world.getBlock(xx, yy, zz).equals(IEContent.blockMetalDecoration) && world.getBlockMetadata(xx, yy, zz)==BlockMetalDecoration.META_lightEngineering))
							return false;
					}
				}

		for(int l=0;l<3;l++)
			for(int w=-1;w<=1;w++)
				for(int h=-1;h<=1;h++)
				{
					int xx = (side==4?l: side==5?-l: side==2?-w : w);
					int yy = h;
					int zz = (side==2?l: side==3?-l: side==5?-w : w);

					if(h==0&&(w!=0||l!=1))
						world.markBlockForUpdate(startX+xx, startY+yy, startZ+zz);
					else
						world.setBlock(startX+xx, startY+yy, startZ+zz, IEContent.blockMetalMultiblocks, BlockMetalMultiblocks.META_squeezer, 0x3);
					if(world.getTileEntity(startX+xx, startY+yy, startZ+zz) instanceof TileEntitySqueezer)
					{
						TileEntitySqueezer tile = (TileEntitySqueezer)world.getTileEntity(startX+xx,startY+yy,startZ+zz);
						tile.facing=side;
						tile.formed=true;
						tile.pos = l*9 + (h+1)*3 + (w+1);
						tile.offset = new int[]{(side==4?l-1: side==5?1-l: side==2?-w: w),h,(side==2?l-1: side==3?1-l: side==5?-w: w)};
					}
				}
		return true;
	}

}
