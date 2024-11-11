package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.listeners.ToDoController;
import com.aryven.aryskovompb.objects.Embeds;
import com.aryven.aryskovompb.objects.ToDoModal;
import com.aryven.aryskovompb.objects.ToDoTask;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.util.List;

public class ToDo extends SlashCommand {
    private final ToDoController toDoController;

    public ToDo() {
        this.name = "todo";
        this.help = "Show To-Do list";
        this.guildOnly = false;
        this.aliases = new String[]{"task"};
        this.toDoController = new ToDoController();
        this.children = new SlashCommand[]{
                new ToDoListSubCommand(),
                new ToDoCreateSubCommand(),
                new ToDoShowTaskSubCommand()
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

    private class ToDoShowTaskSubCommand extends SlashCommand {
        public ToDoShowTaskSubCommand() {
            this.name = "show";
            this.help = "Show details of a task";
            this.guildOnly = false;
        }

        protected void execute(SlashCommandEvent event) {
            List<ToDoTask> tasks = toDoController.getTasks();

            if (tasks.isEmpty()) {
                event.replyEmbeds(new Embeds().todoListEmptyEmbed()).setEphemeral(true).queue();
                return;
            }

            int[] taskIndex = {0};
            ToDoTask task = tasks.get(taskIndex[0]);
            MessageEmbed taskEmbed = new Embeds().todoTaskEmbed(task);

            event.replyEmbeds(taskEmbed)
                    .addActionRow(
                            Button.primary("previous", "◀️").withDisabled(taskIndex[0] == 0),
                            Button.danger("remove", "🗑️ Remove"),
                            Button.success("edit", "✏️ Edit"),
                            Button.primary("next", "▶️").withDisabled(taskIndex[0] == tasks.size() - 1)
                    )
                    .setEphemeral(true)
                    .queue(interactionHook -> {
                        interactionHook.retrieveOriginal().queue(message -> {
                            message.getJDA().addEventListener(new ListenerAdapter() {
                                @Override
                                public void onButtonInteraction(ButtonInteractionEvent buttonEvent) {
                                    // Ensure the interaction is on the correct message
                                    if (!buttonEvent.getMessageId().equals(message.getId())) return;

                                    switch (buttonEvent.getButton().getId()) {
                                        case "previous":
                                            if (taskIndex[0] > 0) taskIndex[0]--;
                                            break;
                                        case "next":
                                            if (taskIndex[0] < tasks.size() - 1) taskIndex[0]++;
                                            break;
                                        case "edit":
                                            // Open edit modal or trigger edit functionality
                                            buttonEvent.reply("Editing task...").setEphemeral(true).queue();
                                            return;
                                        case "remove":
                                            // Confirm and remove task
                                            tasks.remove(taskIndex[0]);
                                            toDoController.removeTask(task);
                                            if (taskIndex[0] >= tasks.size()) taskIndex[0]--;
                                            break;
                                    }

                                    // Update the embed with the new task
                                    ToDoTask currentTask = tasks.isEmpty() ? null : tasks.get(taskIndex[0]);
                                    if (currentTask != null) {
                                        buttonEvent.editMessageEmbeds(new Embeds().todoTaskEmbed(currentTask)).queue();
                                    } else {
                                        buttonEvent.editMessage("No more tasks available.").setEmbeds().queue();
                                    }

                                    // Update button states based on task index
                                    buttonEvent.getHook().editOriginalComponents(
                                            ActionRow.of(
                                                    Button.primary("previous", "◀️").withDisabled(taskIndex[0] == 0),
                                                    Button.danger("remove", "🗑️ Remove").withDisabled(tasks.isEmpty()),
                                                    Button.success("edit", "✏️ Edit").withDisabled(tasks.isEmpty()),
                                                    Button.primary("next", "▶️").withDisabled(taskIndex[0] == tasks.size() - 1)
                                            )
                                    ).queue();
                                }
                            });
                        });
                    });
        }
    }
}
