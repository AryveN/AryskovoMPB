package com.aryven.aryskovompb;

import com.aryven.aryskovompb.objects.Config;
import com.aryven.aryskovompb.objects.customColoring;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import dev.mayuna.mayuslibrary.console.colors.Color;
import dev.mayuna.mayuslibrary.logging.Log;
import dev.mayuna.mayuslibrary.logging.Logger;
import lombok.Getter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    private static @Getter CommandClientBuilder builder;
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
        //loadCommands();
        Logger.info("Commands loaded.");

        Logger.info("Trying to login...");
        try {
            builder = new CommandClientBuilder();
            builder.setOwnerId(Config.getOwnerId());
            builder.setPrefix(Config.getBotPrefix());
            builder.setActivity(Activity.listening("/help"));
            builder.setStatus(OnlineStatus.ONLINE);
            builder.useHelpBuilder(false);
            builder.build();

            //CommandClient commandClient = builder.build();

            JDABuilder.createDefault(Config.getBotToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                    //.addEventListeners(commandClient, new TicketCreateListener(), new TicketAddListener(), new TicketRemoveListener(), new ReactionRolesListener(), new JoinLeaveListener())
                    .build().awaitReady();
        } catch (Exception e) {
            Logger.error(String.valueOf(e));
            Logger.error("Error has occured while trying to login!");
        }

        Logger.info("Loading done!");
    }
/*
    private static void loadCommands() {
        builder = new CommandClientBuilder();
        builder.addSlashCommand(new TicketCreate());
        builder.addSlashCommand(new TicketAdd());
        builder.addSlashCommand(new TicketRemove());
        builder.addSlashCommand(new Help());
        builder.addSlashCommand(new ReactionRoles());
    }
 */
}
