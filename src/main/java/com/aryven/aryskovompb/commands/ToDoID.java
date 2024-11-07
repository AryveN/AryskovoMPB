package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import com.aryven.aryskovompb.objects.ToDoTask;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoID extends SlashCommand {
    @Getter
    private final SlashCommandData commandData;
    private final List<ToDoTask> tasks = new ArrayList<>();

    public ToDoID() {
        this.name = "todo-id";
        this.help = "Show specific task in To-Do list";
        this.guildOnly = false;

        commandData = Commands.slash("todo-id", "Show specific task in To-Do list")
                .addOption(OptionType.STRING, "id", "Task ID", true);
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        String id = Objects.requireNonNull(event.getOption("id")).getAsString();
        ToDoTask task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (task == null) {
            event.reply("Task not found.").setEphemeral(true).queue();
        } else {
            event.reply(task.toString())
                    .addActionRow(
                            Button.primary("mark_done:" + id, "Mark as Done"),
                            Button.secondary("change_date:" + id, "Change Date"),
                            Button.secondary("change_priority:" + id, "Change Priority")
                    )
                    .queue();
        }
    }
}
