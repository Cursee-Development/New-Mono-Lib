package com.cursee.monolib.core.methods;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class ItemMethods {

    /** Checks if an item is the same as a given item. */
    public static boolean compareItemToItem(Item pItem0, Item pItem1) {

        if (pItem0 == null || pItem1 == null) {
            return false;
        }

        return pItem0.equals(pItem1);
    }

    /** Checks if a stack of items is the same as a given item. */
    public static boolean compareItemToItemStack(Item pItem, ItemStack pItemStack) {

        if (pItem == null || pItemStack == null) {
            return false;
        }

        Item toCompare = pItemStack.getItem();

        return  compareItemToItem(pItem, toCompare);
    }

    /** Checks if an item entity is the same as a given item. */
    public static boolean compareItemToItemEntity(Item pItem, ItemEntity pItemEntity) {

        if (pItem == null || pItemEntity == null) {
            return false;
        }

        ItemStack toCompare = pItemEntity.getItem();

        return  compareItemToItemStack(pItem, toCompare);
    }
}