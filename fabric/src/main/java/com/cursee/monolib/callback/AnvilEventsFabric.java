package com.cursee.monolib.callback;

import oshi.util.tuples.Triplet;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;

public class AnvilEventsFabric {

    public static final Event<Update> UPDATE = EventFactory.createArrayBacked(Update.class, callbacks -> (anvilmenu, left, right, output, itemName, baseCost, player) -> {

        for (Update callback : callbacks) {

            Triplet<Integer, Integer, ItemStack> triple = callback.onUpdate(anvilmenu, left, right, output, itemName, baseCost, player);

            if (triple != null) return triple;
        }

        return null;
    });

    @FunctionalInterface
    public interface Update {
        Triplet<Integer, Integer, ItemStack> onUpdate(AnvilMenu anvilmenu, ItemStack slotLeft, ItemStack slotRight, ItemStack slotOutput, String itemName, int baseCost, Player player);
    }

    public AnvilEventsFabric() {}
}
