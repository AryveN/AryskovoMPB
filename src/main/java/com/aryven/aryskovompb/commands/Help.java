package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Help extends SlashCommand {

    public Help() {
        this.name = "help";
        this.help = "Help command";
        this.guildOnly = false;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.replyEmbeds(new Embeds().help()).setEphemeral(true).queue();
    }

}
