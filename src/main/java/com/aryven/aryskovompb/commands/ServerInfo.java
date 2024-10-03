package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class ServerInfo extends SlashCommand {
    public ServerInfo() {
        this.name = "serverinfo";
        this.help = "Server info command";
        this.guildOnly = false;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.replyEmbeds(new Embeds().serverInfo(event)).setEphemeral(true).queue();
    }
}
