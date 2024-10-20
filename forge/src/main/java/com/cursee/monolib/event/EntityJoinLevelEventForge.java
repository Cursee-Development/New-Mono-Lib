package com.cursee.monolib.event;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityJoinLevelEventForge {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Sailing.onEntityJoinLevel(event.getLevel(), event.getEntity());
    }
}
