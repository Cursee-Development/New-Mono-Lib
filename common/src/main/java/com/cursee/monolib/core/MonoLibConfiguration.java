package com.cursee.monolib.core;

import com.cursee.monolib.Constants;
import com.cursee.monolib.platform.Services;
import com.cursee.monolib.util.toml.Toml;
import com.cursee.monolib.util.toml.TomlWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MonoLibConfiguration {

    public static final File CONFIG_DIRECTORY = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
    public static final String CONFIG_FILEPATH = CONFIG_DIRECTORY + File.separator + Constants.MOD_ID + ".toml";

    public static boolean debugging = false;
    public static boolean enableAntiRepostingCheck = true;

    public static final Map<String, Object> defaults = new HashMap<String, Object>();

    public static void initialize() {

        defaults.put("debugging", false);
        defaults.put("enableAntiRepostingCheck", true);

        // initialize the config directory if it doesn't exist

        if (!CONFIG_DIRECTORY.isDirectory()) {
            CONFIG_DIRECTORY.mkdir();
        }

        final File CONFIG_FILE = new File(CONFIG_FILEPATH);

        if (!CONFIG_FILE.isFile()) {

            // write file

            try {
                TomlWriter writer = new TomlWriter();
                writer.write(defaults, new File(CONFIG_FILEPATH));
            } catch (IOException exception) {

                Constants.LOG.error("Error(s) occurred while attempting to write " + Constants.MOD_ID + ".toml");
                Constants.LOG.error(exception.getMessage());
                Constants.LOG.error("Have you loaded the mod in the wrong loader?");

                throw new RuntimeException(exception);
            }

        }
        else {

            // read file and import or set to defaults

            try {
                Toml toml = new Toml().read(CONFIG_FILE);
                MonoLibConfiguration.debugging = toml.getBoolean("debugging");
                MonoLibConfiguration.enableAntiRepostingCheck = toml.getBoolean("enableAntiRepostingCheck");
            }
            catch (IllegalStateException exception) {
                Constants.LOG.error("Error(s) occurred while attempting to parse " + Constants.MOD_ID + ".toml");
                Constants.LOG.error(exception.getMessage());
                Constants.LOG.error("Configuration values set to defaults.");
                Constants.LOG.error("Only TOML specification v0.4.0 is valid. Reference: " + "https://www.toml.io/en/v0.4.0");
            }

        }

    }
}
