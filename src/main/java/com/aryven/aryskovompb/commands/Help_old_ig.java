package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help_old_ig extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("help")) {
            event.replyEmbeds(new Embeds().help()).setEphemeral(true).queue();
        }
    }

}
