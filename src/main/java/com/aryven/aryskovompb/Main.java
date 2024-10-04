package com.aryven.aryskovompb;

import com.aryven.aryskovompb.objects.Config;
import com.aryven.aryskovompb.objects.customColoring;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.SlashCommand;
import dev.mayuna.mayuslibrary.console.colors.Color;
import dev.mayuna.mayuslibrary.logging.Logger;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {

        //Initialize client and config + logger setting
        CommandClientBuilder client = new CommandClientBuilder();
        Config.load();
        Logger.setColoring(new customColoring());
        Logger.setTimePattern("HH:mm:ss");
        Logger.setFormat("[{type} | {time} | " + Config.getBotName() + " | " + Config.getBotVersion() + "]" + Color.RESET + " {text}");
        Logger.debug("Logger set up.");
        Logger.debug("Client and Client loaded.");


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

        //Set the client settings
        client.setOwnerId(Config.getOwnerId());
        client.setActivity(Activity.listening("/help"));
        client.setStatus(OnlineStatus.ONLINE);
        client.setPrefix(Config.getBotPrefix());
        client.useHelpBuilder(false);
        Logger.debug("Client setting set up.");

        //Command loading
        Logger.info("Loading commands...");
        Command[] commands = getCommands();
        SlashCommand[] slashCommands = getSlashCommands();
        client.addCommands(commands);
        client.addSlashCommands(slashCommands);

        //Finalize command loading
        CommandClient commandClient = client.build();

        //Login
        Logger.info("Trying to login...");
        try {

             JDABuilder.createDefault(Config.getBotToken())
                     .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                     .enableIntents(GatewayIntent.GUILD_PRESENCES)
                     .enableIntents(GatewayIntent.GUILD_MEMBERS)
                     .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                     .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                     .addEventListeners(commandClient)
                     .build();

        } catch (Exception e) {
            Logger.error(String.valueOf(e));
            Logger.error("Error has occured while trying to login!");
        }

        Logger.info("Loading done!");


    }

    /*
    private static void loadCommands() {
        CommandClientBuilder builder = new CommandClientBuilder();
        JDABuilder jdaBuilder;
        builder.addCommand(new Help());
        builder.addSlashCommand(new Help());
        Logger.debug("Commands loaded!");
    }
     */

    /**
     * Gathers all commands from "commands" package.
     *
     * @return an array of commands
     */
    private static Command[] getCommands() {
        Reflections reflections = new Reflections("com.aryven.aryskovompb.commands");
        Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
        // We have to add all SlashCommands as a fallback
        subTypes.addAll(reflections.getSubTypesOf(SlashCommand.class));
        List<Command> commands = new ArrayList<>();

        for (Class<? extends Command> theClass : subTypes) {
            // Don't load SubCommands or SlashCommands
            if (theClass.getName().contains("SubCommand") || theClass.getName().contains("SlashCommand"))
                continue;

            // Attempt to add commands by instantiating the declared constructor
            try {
                commands.add(theClass.getDeclaredConstructor().newInstance());
                Logger.debug(theClass.getSimpleName() + " Command loaded successfully.");
                //LoggerFactory.getLogger(theClass).debug("Loaded Command Successfully!");
            } catch (Throwable throwable) {
                Logger.error("Error loading Command!\n" + throwable);
                //LoggerFactory.getLogger(theClass).error("Error loading Command!", throwable);
            }
        }
        Logger.debug(commands.size() + " Commands Loaded!");
        return commands.toArray(new Command[0]);
    }

    /**
     * Gathers all SlashCommands from "commands" package.
     *
     * @return an array of commands
     */
    private static SlashCommand[] getSlashCommands() {
        Reflections reflections = new Reflections("com.aryven.aryskovompb.commands");
        Set<Class<? extends SlashCommand>> subTypes = reflections.getSubTypesOf(SlashCommand.class);
        List<SlashCommand> commands = new ArrayList<>();

        for (Class<? extends SlashCommand> theClass : subTypes) {
            // Don't load SubCommands
            if (theClass.getName().contains("SubCommand"))
                continue;
            try {
                commands.add(theClass.getDeclaredConstructor().newInstance());
            } catch (Throwable throwable) {
                Logger.error("Error loading SlashCommand!\n" + throwable);
                //LoggerFactory.getLogger(theClass).error("Failed to load SlashCommand!", throwable);
            }
            Logger.debug(theClass.getSimpleName() + " SlashCommand loaded successfully!");
            //LoggerFactory.getLogger(theClass).debug("Loaded SlashCommand Successfully!");
        }

        Logger.debug(commands.size() + " SlashCommands Loaded");
        return commands.toArray(new SlashCommand[0]);
    }

}

