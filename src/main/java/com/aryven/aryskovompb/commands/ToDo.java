package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.listeners.ToDoController;
import com.aryven.aryskovompb.objects.Embeds;
import com.aryven.aryskovompb.objects.ToDoModal;
import com.aryven.aryskovompb.objects.ToDoTask;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

import java.util.List;

public class ToDo extends SlashCommand {
    private final ToDoController toDoController;

    public ToDo() {
        this.name = "todo";
        this.help = "Show To-Do list";
        this.guildOnly = false;
        this.aliases = new String[] {"task"};
        this.toDoController = new ToDoController();
        this.children = new SlashCommand[] {
                new ToDoListSubCommand(),
                new ToDoCreateSubCommand()
        };
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        //Bum bo nam bo
    }

    private class ToDoListSubCommand extends SlashCommand {
        public ToDoListSubCommand() {
            this.name = "list";
            this.help = "Show list of tasks in To-Do list";
            this.guildOnly = false;
        }

        @Override
        protected void execute(SlashCommandEvent event) {
            List<ToDoTask> tasks = toDoController.getTasks();

            if (tasks.isEmpty()) {
                event.replyEmbeds(new Embeds().todoListEmptyEmbed()).setEphemeral(true).queue();
                return;
            }

            event.replyEmbeds(new Embeds().todoListEmbed(tasks)).setEphemeral(true).queue();
        }
    }

    private class ToDoCreateSubCommand extends SlashCommand {
        public ToDoCreateSubCommand() {
            this.name = "create";
            this.help = "Add task to To-Do list";
            this.guildOnly = false;
        }

        @Override
        protected void execute(SlashCommandEvent event) {
            event.replyModal(new ToDoModal().createToDoModal(event)).queue();
        }
    }
}
