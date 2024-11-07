package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import com.aryven.aryskovompb.objects.ToDoModal;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Task extends SlashCommand {
    public Task() {
        this.name = "task";
        this.help = "Create task for To-Do list";
        this.guildOnly = false;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.replyModal(new ToDoModal().createToDoModal(event)).queue();
    }
}
