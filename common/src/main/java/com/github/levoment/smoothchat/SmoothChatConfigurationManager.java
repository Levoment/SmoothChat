package com.github.levoment.smoothchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;

public class SmoothChatConfigurationManager {

    public static void readOrCreateConfig() {
        // Create a Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // If the file doesn't exist
        if (!SmoothChatMod.configFile.exists()) {
            // Create a configuration object
            SmoothChatMod.configurationObject = new ConfigurationObject();
            SmoothChatMod.configurationObject.SmoothChat = true;
            SmoothChatMod.configurationObject.TransitionTimeFloat = 0.5f;
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
