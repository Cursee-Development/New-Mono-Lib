package com.cursee.monolib;

import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.monolib.event.EntityJoinLevelEventForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class MonoLibForge {
    
    public MonoLibForge() {
        MonoLib.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        MinecraftForge.EVENT_BUS.register(EntityJoinLevelEventForge.class);
    }
}