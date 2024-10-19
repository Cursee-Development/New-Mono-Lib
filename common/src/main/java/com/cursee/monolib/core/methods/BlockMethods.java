package com.cursee.monolib.core.methods;

import com.cursee.monolib.core.annotation.Legacy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A collection of methods for operating with the Block class.
 * <br>
 * Some methods are credited to Rick South / Serilum. See:  <a href="https://github.com/Serilum/Collective/blob/1.20.4/Common/src/main/java/com/natamus/collective/functions/BlockFunctions.java">Collective BlockFunctions</a>
 *
 * @see Block
 *
 * */
@SuppressWarnings("unused")
public class BlockMethods {

    /** Checks if a block is the same as a given block. Credited to Rick South / Serilum */
    @Legacy
    public static boolean compareBlockToBlock(Block pBlock0, Block pBlock1) {

        if (pBlock0 == null || pBlock1 == null) {
            return false;
        }

        return pBlock0.equals(pBlock1);
    }

    /** Checks if an item is the same as a given block. Credited to Rick South / Serilum */
    @Legacy
    public static boolean compareBlockToItem(Block pBlock, Item pItem) {

        if (pBlock == null || pItem == null) {
            return false;
        }

        Block toCompare = Block.byItem(pItem);

        return compareBlockToBlock(pBlock, toCompare);
    }

    /** Checks if a stack of items is the same as a given block. Credited to Rick South / Serilum */
    @Legacy
    public static boolean compareBlockToItemStack(Block pBlock, ItemStack pItemStack) {

        if (pBlock == null || pItemStack == null) {
            return false;
        }

        Item toCompare = pItemStack.getItem();

        return compareBlockToItem(pBlock, toCompare);
    }

    /** Checks if a block state is the same as a given block. Credited to Rick South / Serilum */
    @Legacy
    public static boolean compareBlockToBlockState(Block pBlock, BlockState pBlockState) {

        if (pBlock == null || pBlockState == null) {
            return false;
        }

        Block toCompare = pBlockState.getBlock();

        return compareBlockToBlock(pBlock, toCompare);
    }

    /** Checks if a block in a level at a position is the same as a given block. Credited to Rick South / Serilum */
    @Legacy
    public static boolean compareBlockToLevelPosition(Block pBlock, Level pLevel, BlockPos pBlockPos) {

        if (pBlock == null || pLevel == null || pBlockPos == null) {
            return false;
        }

        BlockState toCompare = pLevel.getBlockState(pBlockPos);

        return compareBlockToBlockState(pBlock, toCompare);
    }

}