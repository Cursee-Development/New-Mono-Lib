package com.cursee.monolib.event;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class EntityJoinLevelEventForge {

    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Sailing.onEntityJoinLevel(event.getLevel(), event.getEntity());
    }
}
