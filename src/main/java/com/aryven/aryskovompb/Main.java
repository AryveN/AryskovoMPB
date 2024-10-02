package com.aryven.aryskovompb;

import com.aryven.aryskovompb.objects.Config;
import com.aryven.aryskovompb.objects.customColoring;
import dev.mayuna.mayuslibrary.console.colors.Color;
import com.aryven.aryskovompb.commands.*;
import dev.mayuna.mayuslibrary.logging.Logger;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) {
        Config.load();

        //Log settings
        Logger.setColoring(new customColoring());
        Logger.setTimePattern("HH:mm:ss");
        Logger.setFormat("[{type} | {time} | " + Config.getBotName() + " | " + Config.getBotVersion() + "]" + Color.RESET + " {text}");


        Logger.info("""
                                                          \s
                                                          \s
                       d8888 888b     d888 8888888b.  888888b.  \s
                      d88888 8888b   d8888 888   Y88b 888  "88b \s
                     d88P888 88888b.d88888 888    888 888  .88P \s
                    d88P 888 888Y88888P888 888   d88P 8888888K. \s
                   d88P  888 888 Y888P 888 8888888P"  888  "Y88b\s
                  d88P   888 888  Y8P  888 888        888    888\s
                 d8888888888 888   "   888 888        888   d88P\s
                d88P     888 888       888 888        8888888P" \s
                """);
        Logger.info("## Made by AryveN ##");
        Logger.info("System Starting...");

        Logger.info("Loading commands...");
        Logger.info("Commands loaded.");

        Logger.info("Trying to login...");
        try {
            JDABuilder builder = JDABuilder.createDefault(Config.getBotToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.listening("/help"));

            builder.addEventListeners(new Help(), new BotListener())
                    .build().awaitReady();
        } catch (Exception e) {
            Logger.error(String.valueOf(e));
            Logger.error("Error has occured while trying to login!");
        }

        Logger.info("Loading done!");
    }
}

class BotListener extends ListenerAdapter {
    @Override
    public void onReady(net.dv8tion.jda.api.events.session.ReadyEvent event) {
        // Register the slash commands with JDA 5
        event.getJDA().updateCommands()
                .addCommands(Commands.slash("help", "Displays help information"))
                .queue();
        Logger.info("Slash commands registered.");
    }

}
