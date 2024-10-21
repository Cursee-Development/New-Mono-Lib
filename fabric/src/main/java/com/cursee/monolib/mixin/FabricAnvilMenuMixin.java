package com.cursee.monolib.mixin;

import com.cursee.monolib.callback.AnvilEventsFabric;
import com.natamus.collective.fabric.callbacks.CollectiveAnvilEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Triplet;

@Mixin(value = AnvilMenu.class, priority = 1001)
public abstract class FabricAnvilMenuMixin extends ItemCombinerMenu {
    public FabricAnvilMenuMixin(MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Shadow private String itemName;
    @Shadow private int repairItemCountCost;
    @Final @Shadow private DataSlot cost;

    @Inject(method = "createResult()V", at = @At(value= "RETURN"))
    public void onCreateAnvilResult(CallbackInfo info) {
        AnvilMenu anvilmenu = (AnvilMenu)(Object)this;
        Container inputslots = this.inputSlots;

        ItemStack left = inputslots.getItem(0);
        ItemStack right = inputslots.getItem(1);
        ItemStack output = this.resultSlots.getItem(0);

        int baseCost = left.getOrDefault(DataComponents.REPAIR_COST, 0) + (right.isEmpty() ? 0 : right.getOrDefault(DataComponents.REPAIR_COST, 0));

        Triplet<Integer, Integer, ItemStack> triple = AnvilEventsFabric.UPDATE.invoker().onUpdate(anvilmenu, left, right, output, itemName, baseCost, this.player);
        if (triple == null) {
            return;
        }

        if (triple.getA() >= 0) {
            cost.set(triple.getA());
        }

        if (triple.getB() >= 0) {
            repairItemCountCost = triple.getB();
        }

        if (triple.getC() != null) {
            this.resultSlots.setItem(0, triple.getC());
        }
    }
}
