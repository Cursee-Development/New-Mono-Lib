package com.cursee.monolib;

import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.monolib.event.EntityJoinLevelEventFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class MonoLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        MonoLib.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);

        ServerEntityEvents.ENTITY_LOAD.register(EntityJoinLevelEventFabric::onEntityJoinLevel);
    }
}
