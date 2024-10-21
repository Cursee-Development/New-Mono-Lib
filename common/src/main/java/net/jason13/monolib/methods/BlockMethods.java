package net.jason13.monolib.methods;

import com.cursee.monolib.core.annotation.Legacy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Deprecated during 2.0 re-factor, opt for BlockMethods from com.cursee.monolib.core.methods
 * <br>
 * This class and it's methods are required until the following list of mods has proper versioning of monolib
 * <br>
 * Affected mods as of April 19th, 2024: DangerClose, MoreBeautifulTorches, MoreBowsAndArrows, AutoMessage, NewSlabVariants, GoldenFoods, HostileMobDropRecipes[Overworld], HostileMobDropRecipes[Nether], HostileMobDropRecipes[End], NewShieldVariants, StackableStewsAndSoups, EatAnOmelette, TimeOnDisplay, MoreUsefulCopper, EnderPack
 */
@Deprecated @Legacy
public class BlockMethods {

    /**
     * Deprecated during 2.0 re-factor, opt for com.cursee.monolib.core.methods.BlockMethods.compareBlockToBlock
     */
    @Deprecated @Legacy
    public static boolean compareBlockToBlock(Block pBlock0, Block pBlock1) {
        if (pBlock0 == null || pBlock1 == null) {
            return false;
        }
        return pBlock0.equals(pBlock1);
    }

    /**
     * Deprecated during 2.0 re-factor, opt for com.cursee.monolib.core.methods.BlockMethods.compareBlockToItem
     */
    @Deprecated @Legacy
    public static boolean compareBlockToItem(Block pBlock, Item pItem) {
        if (pBlock == null || pItem == null) {
            return false;
        }
        Block toCompare = Block.byItem(pItem);
        return compareBlockToBlock(pBlock, toCompare);
    }

    /**
     * Deprecated during 2.0 re-factor, opt for com.cursee.monolib.core.methods.BlockMethods.compareBlockToItemStack
     */
    @Deprecated @Legacy
    public static boolean compareBlockToItemStack(Block pBlock, ItemStack pItemStack) {
        if (pBlock == null || pItemStack == null) {
            return false;
        }
        Item toCompare = pItemStack.getItem();
        return compareBlockToItem(pBlock, toCompare);
    }

    /**
     * Deprecated during 2.0 re-factor, opt for com.cursee.monolib.core.methods.BlockMethods.compareBlockToBlockState
     */
    @Deprecated @Legacy
    public static boolean compareBlockToBlockState(Block pBlock, BlockState pBlockState) {
        if (pBlock == null || pBlockState == null) {
            return false;
        }
        Block toCompare = pBlockState.getBlock();
        return compareBlockToBlock(pBlock, toCompare);
    }

    /**
     * Deprecated during 2.0 re-factor, opt for com.cursee.monolib.core.methods.BlockMethods.compareBlockToLevelPosition
     */
    @Deprecated @Legacy
    public static boolean compareBlockToLevelPosition(Block pBlock, Level pLevel, BlockPos pBlockPos) {
        if (pBlock == null || pLevel == null || pBlockPos == null) {
            return false;
        }
        BlockState toCompare = pLevel.getBlockState(pBlockPos);
        return compareBlockToBlockState(pBlock, toCompare);
    }

}