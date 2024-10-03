package com.aryven.aryskovompb.objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class Embeds {
    public MessageEmbed help() {
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("❓ 〃 Help - Update");
        help.setDescription("All commands are slash commands. They can be invoked with /<command name>");
        help.addField("Currently available commands:", """
                        /help - Shows this message\n
        """,false);
        help.setColor(Color.decode("#ce5253"));
        return help.build();
    }
}
