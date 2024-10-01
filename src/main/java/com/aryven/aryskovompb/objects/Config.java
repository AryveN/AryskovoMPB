package com.aryven.aryskovompb.objects;

import com.google.gson.JsonPrimitive;
import dev.mayuna.mayusjsonutils.JsonUtil;
import dev.mayuna.mayusjsonutils.objects.MayuJson;
import dev.mayuna.mayuslibrary.logging.Logger;
import lombok.Getter;

public class Config {
    private static @Getter String botToken = "### Bot Token Here ###";
    private static @Getter String botVersion = "### Bot Version Here ###";
    private static @Getter String botPrefix = "### Bot Prefix Here ###";
    private static @Getter String botName = "### Bot Name Here ###";
    private static @Getter String ownerId = "### Owner ID Here ###";

    public static void load() {
        try {
            MayuJson json = JsonUtil.createOrLoadJsonFromFile("./bot_config.json");

            botToken = json.getOrCreate("botToken", new JsonPrimitive(botToken)).getAsString();
            botVersion = json.getOrCreate("botVersion", new JsonPrimitive(botVersion)).getAsString();
            botPrefix = json.getOrCreate("botPrefix", new JsonPrimitive(botPrefix)).getAsString();
            botName = json.getOrCreate("botName", new JsonPrimitive(botName)).getAsString();
            ownerId = json.getOrCreate("ownerId", new JsonPrimitive(ownerId)).getAsString();

            json.saveJson();
        } catch (Exception exception){
            exception.printStackTrace();
            Logger.error("Nastala chyba při načítání bot configu!!");
        }
    }
}
