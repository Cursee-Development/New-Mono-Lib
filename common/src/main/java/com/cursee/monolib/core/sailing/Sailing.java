package com.cursee.monolib.core.sailing;

import com.cursee.monolib.core.MonoLibConfiguration;
import com.cursee.monolib.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.commons.lang3.ArrayUtils;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Triplet;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Sailing is derived from Serilum's <a href="https://github.com/Serilum/Collective/blob/1.20.1/Common/src/main/java/com/natamus/collective/check/RegisterMod.java">RegisterMod.java</a>, to advocate against using reposted mods.
 * <br>
 * "Sailing" is a term used in piracy, to denote the act of pirating. (i.e. "Happy Sailing", "Safe Sailing").
 */
public class Sailing {

    public static boolean shouldVerifyJars = true;
    private static final HashMap<String, String> jarFilenameToNameMap = new HashMap<String, String>(); // jarFilenameToNameMap
    private static final CopyOnWriteArrayList<String> registeredJarFilenames = new CopyOnWriteArrayList<String>(); // jarFilenamesDiscovered

    private static String MOD_ID;

    private static String PUBLISHER; // = "Lupin";
    private static String AUTHOR; // = "Jason13";
    private static String PRIMARY_URL; // = "https://www.curseforge.com/members/lupin/projects";
    private static String CURSE_FORGE_URL; // = "https://www.curseforge.com/members/lupin/projects";
    private static String MODRINTH_URL; // = "https://modrinth.com/user/Lupin/mods";

    /**
     * Used to register relevant details during mod initialization. <br>
     *
     * Mods that depend on MonoLib and opt-in to use Sailing are expected to follow the same filename schema: <br>
     * "mod_id - mod_loader - mc_version - mod_version" -> "monolib-fabric-1.20-1.3.0.jar" <br>
     *
     * @param mod_name The name of your mod. (i.e. MonoLib)
     * @param mod_id The identifier for your mod. (i.e. monolib)
     * @param mod_version The version of your mod. (i.e. 1.3.0)
     * @param mc_version_raw The raw Minecraft version for your mod. (i.e. [1.20] or 1.20)
     * @param publisher_author A Pair from the 'oshi.utils.tuples' package full of Strings denoting the publisher and author of the mod.
     * @param primary_curseforge_modrinth A Triplet from the 'oshi.utils.tuples' package full of Strings denoting the relevant project URLs. (GitHub, CurseForge, Modrinth)
     */
    public static void register(String mod_name, String mod_id, String mod_version, String mc_version_raw, Pair<String, String> publisher_author, Triplet<String, String, String> primary_curseforge_modrinth) {

        String mc_version = mc_version_raw.replaceAll("\\[", "").replaceAll("]", "");
        final String MERGED_FILENAME_SCHEMA = mod_id + "-" + "merged" + "-" + mc_version + "-" + mod_version + ".jar";
        Sailing.registeredJarFilenames.add(MERGED_FILENAME_SCHEMA);
        Sailing.jarFilenameToNameMap.put(MERGED_FILENAME_SCHEMA, mod_name);

        Sailing.MOD_ID = mod_id;

        Sailing.PUBLISHER = publisher_author.getA();
        Sailing.AUTHOR = publisher_author.getB();
        Sailing.PRIMARY_URL = primary_curseforge_modrinth.getA();
        Sailing.CURSE_FORGE_URL = primary_curseforge_modrinth.getB();
        Sailing.MODRINTH_URL = primary_curseforge_modrinth.getC();
    }

    /**
     * Used to verify registered mod filenames against installed mod filenames.
     */
    public static boolean onEntityJoinLevel(Level level, Entity entity) {

        final String checkedEntityTag = Sailing.MOD_ID + ".checked";

        Set<String> tags = entity.getTags();
        if (tags.contains(checkedEntityTag)) {
            return true;
        }

        if (!(entity instanceof LivingEntity) || !MonoLibConfiguration.enableAntiRepostingCheck) {
            return true;
        }

        if (entity instanceof Player player) {

            if (Sailing.shouldVerifyJars) {
                Sailing.joinWorldProcess(level, player);
            }
        }
        
        entity.addTag(checkedEntityTag);

        return true;
    }

    public static void joinWorldProcess(Level level, Player player) {

        if (!(level instanceof ServerLevel) || !MonoLibConfiguration.enableAntiRepostingCheck) {
            return;
        }

        List<String> failedJarFilenames = Sailing.checkIfAllJarsExist();
        if (!failedJarFilenames.isEmpty()) {
            if (Sailing.processPreJoinWorldCheck(level)) {

                Sailing.sendMessage(player, "Mod(s) from incorrect sources:", ChatFormatting.RED, Sailing.PRIMARY_URL);
                for (String filename : failedJarFilenames) {
                    Sailing.sendMessage(player, " - " + filename, ChatFormatting.YELLOW, Sailing.PRIMARY_URL);
                }

                Sailing.sendMessage(player, "You are receiving this message because you are using at least one of " + Sailing.PUBLISHER + "'s mods, but probably haven't downloaded them from an original source. Unofficial sources can contain malicious software, supply no income for developers and host outdated versions.", ChatFormatting.RED, Sailing.PRIMARY_URL);
                Sailing.sendMessage(player, Sailing.PUBLISHER + "'s mod downloads are only officially available at CurseForge and Modrinth.", ChatFormatting.DARK_GREEN, Sailing.PRIMARY_URL);
                Sailing.sendMessage(player, "  CF: " + Sailing.CURSE_FORGE_URL, ChatFormatting.YELLOW, Sailing.CURSE_FORGE_URL);
                Sailing.sendMessage(player, "  MR: " + Sailing.MODRINTH_URL, ChatFormatting.YELLOW, Sailing.MODRINTH_URL);
                Sailing.sendMessage(player, "You won't see this message again in this instance. Thank you for reading.", ChatFormatting.DARK_GREEN, Sailing.PRIMARY_URL);
                Sailing.sendMessage(player, "-" + Sailing.AUTHOR, ChatFormatting.YELLOW, Sailing.PRIMARY_URL);

                System.out.println("You are receiving this message because you are using at least one of " + Sailing.PUBLISHER + "'s mods, but probably haven't downloaded them from an original source. Unofficial sources can contain malicious software, supply no income for developers and host outdated versions.");
                System.out.println(Sailing.PUBLISHER + "'s mod downloads are only officially available at CurseForge and Modrinth.");
                System.out.println("  CF: " + Sailing.CURSE_FORGE_URL);
                System.out.println("  MR: " + Sailing.MODRINTH_URL);
                System.out.println("You won't see this message again in this instance. Thank you for reading.");
                System.out.println("-" + Sailing.AUTHOR);

                Sailing.processPostJoinWorldCheck(level);
            }
        }

        shouldVerifyJars = false;
    }

    private static boolean processPreJoinWorldCheck(Level world) {

        String path = Sailing.getWorldPath((ServerLevel)world) + File.separator + "config" + File.separator + Sailing.MOD_ID + File.separator + "checked.txt";
        File checkfile = new File(path);

        if (checkfile.exists()) {
            shouldVerifyJars = false;
        }
        else if (!checkAlternative()) {
            shouldVerifyJars = false;
        }

        return shouldVerifyJars;
    }

    private static void processPostJoinWorldCheck(Level world) {

        shouldVerifyJars = false;
        String path = Sailing.getWorldPath((ServerLevel)world) + File.separator + "config" + File.separator + Sailing.MOD_ID;
        File dir = new File(path);

        if (!dir.mkdirs()) {
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(path + File.separator + "checked.txt", StandardCharsets.UTF_8);
            writer.println("# Please check out https://stopmodreposts.org/ for more information on why this feature exists.");
            writer.println("checked=true");
            writer.close();
        } catch (Exception ignored) { }

        // if we weren't able to create config/monolib/checked.txt, create ../monolib/checked.txt
        Sailing.createRepostingCheckFile();
    }

    public static void createRepostingCheckFile() {

        String alternativePath = Sailing.getConfigDirectory() + File.separator + Sailing.MOD_ID;

        if (new File(alternativePath + File.separator + "checked.txt").isFile()) {
            return;
        }

        File alternativeDirectory = new File(alternativePath);

        if (!alternativeDirectory.mkdirs()) {
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(alternativePath + File.separator + "checked.txt", StandardCharsets.UTF_8);
            writer.println("# Please check out https://stopmodreposts.org/ for more information on why this feature exists.");
            writer.println("checked=true");
            writer.close();
        } catch (Exception ignored) { }
    }

    private static List<String> checkIfAllJarsExist() {

        final List<String> installedModFilenames = Sailing.getInstalledModJars();
        List<String> failedToVerify = new ArrayList<String>();

        for (String filename : registeredJarFilenames) {
            // checking against "monolib-merged-1.20-1.3.0.jar" which is always invalid.
            // mutate "-merged-" to "-fabric-", "-forge-", "-neoforge-"

            if (!(installedModFilenames.contains(filename) || installedModFilenames.contains(filename.replace("-merged-", "-fabric-")) || installedModFilenames.contains(filename.replace("-merged-", "-forge-")) || installedModFilenames.contains(filename.replace("-merged-", "-neoforge-"))) && jarFilenameToNameMap.containsKey(filename)) {
                failedToVerify.add(jarFilenameToNameMap.get(filename));
            }
        }

        if (!failedToVerify.isEmpty()) {
            Collections.sort(failedToVerify);
        }
        return failedToVerify;
    }

    private static boolean checkAlternative() {

        String alternativePath = Sailing.getConfigDirectory() + File.separator + Sailing.MOD_ID + "checked.txt";
        File alternativeFile = new File(alternativePath);

        return !alternativeFile.exists();
    }

    public static List<String> getInstalledModJars() {

        List<String> installedModFilenames = new ArrayList<String>();
        File modDirectory = new File(Sailing.getModDirectory());
        File[] installedFilesArray = modDirectory.listFiles();
        File versionFolder = new File(Sailing.getModDirectory() + File.separator + getCurrentMinecraftVersion());
        File[] installedVersionsArray = versionFolder.listFiles();

        for (File file : ArrayUtils.addAll(installedFilesArray, installedVersionsArray)) {
            if (file.isFile()) {
                String filename = file.getName().replaceAll(" +\\([0-9]+\\)", "");
                installedModFilenames.add(filename);
            }
        }

        return installedModFilenames;
    }

    public static void sendMessage(Player player, String message, ChatFormatting colour, boolean insertNewLineBeforeMessage, String url) {

        if (message.isEmpty()) {
            return;
        }

        if (insertNewLineBeforeMessage) {
            player.sendSystemMessage(Component.literal(""));
        }

        MutableComponent mutableMessage = Component.literal(message);
        mutableMessage.withStyle(colour);

        if (message.contains("http") || !url.isEmpty()) {
            if (url.isEmpty()) {
                for (String word : message.split(" ")) {
                    if (word.contains("http")) {
                        url = word;
                        break;
                    }
                }
            }

            if (!url.isEmpty()) {
                Style clickstyle = mutableMessage.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                mutableMessage.withStyle(clickstyle);
            }
        }

        player.sendSystemMessage(mutableMessage);
    }

    public static void sendMessage(Player player, String message, ChatFormatting color, String url) {
        Sailing.sendMessage(player, message, color, false, url);
    }

    public static String getGameDirectory() {
        return Services.PLATFORM.getGameDirectory();
    }

    public static String getModDirectory() {
        return Sailing.getGameDirectory() + File.separator + "mods";
    }

    public static String getConfigDirectory() {
        return Sailing.getGameDirectory() + File.separator + "config";
    }

    public static String getCurrentMinecraftVersion() {
        return SharedConstants.VERSION_STRING;
    }

    public static String getWorldPath(MinecraftServer server) {
        String path = server.getWorldPath(LevelResource.ROOT).toString();
        return path.substring(0, path.length() - 2);
    }

    public static String getWorldPath(ServerLevel level) {
        return getWorldPath(level.getServer());
    }
}
