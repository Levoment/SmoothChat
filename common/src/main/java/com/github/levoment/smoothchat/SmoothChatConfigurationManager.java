package com.github.levoment.smoothchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class SmoothChatConfigurationManager {

    public static void readOrCreateConfig() {
        // Create a Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // If the file doesn't exist
        if (!SmoothChatMod.configFile.exists()) {
            // Create a configuration object
            SmoothChatMod.configurationObject = new ConfigurationObject();
            try {
                Writer fileWriter = new FileWriter(SmoothChatMod.configFile);
                gson.toJson(SmoothChatMod.configurationObject, fileWriter);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Reader fileReader = new FileReader(SmoothChatMod.configFile);
                SmoothChatMod.configurationObject = gson.fromJson(fileReader, ConfigurationObject.class);
                fileReader.close();
                // Write the config back in case the mod has been updated and the config does not have new values
                writeConfig();
                // If the object is null
                if (SmoothChatMod.configurationObject == null) {
                    SmoothChatMod.configurationObject = new ConfigurationObject();
                    writeConfig();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void readConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (SmoothChatMod.configFile.exists()) {
                Reader fileReader = new FileReader(SmoothChatMod.configFile);
                SmoothChatMod.configurationObject = gson.fromJson(fileReader, ConfigurationObject.class);
                fileReader.close();
            } else {
                readOrCreateConfig();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void writeConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer fileWriter = new FileWriter(SmoothChatMod.configFile);
            gson.toJson(SmoothChatMod.configurationObject, ConfigurationObject.class, fileWriter);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
