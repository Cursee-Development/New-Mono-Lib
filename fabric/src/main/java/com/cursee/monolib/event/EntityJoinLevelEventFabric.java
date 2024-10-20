package com.cursee.monolib.event;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class EntityJoinLevelEventFabric {

    public static void onEntityJoinLevel(Entity entity, ServerLevel level) {
        Sailing.onEntityJoinLevel(level, entity);
    }
}
