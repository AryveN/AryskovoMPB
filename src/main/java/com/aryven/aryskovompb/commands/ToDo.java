package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.ToDoTask;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDo extends SlashCommand {

    private final List<ToDoTask> tasks = new ArrayList<>();

    public ToDo() {
        this.name = "todo";
        this.help = "Show To-Do list";
        this.guildOnly = false;

        SlashCommandData commandData = CommandData("todo", "Show To-Do list")
                .addOption(OptionType.STRING, "date", "Date to show To-Do list for", false);
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        String dateStr = event.getOption("date") != null ? Objects.requireNonNull(event.getOption("date")).getAsString() : null;
        LocalDate dateFilter = dateStr != null ? LocalDate.parse(dateStr) : null;

        List<ToDoTask> filteredTasks = tasks.stream()
                .filter(task -> dateFilter == null || task.getDueDate().equals(dateFilter))
                .toList();

        if (filteredTasks.isEmpty()) {
            event.reply("No tasks found.").setEphemeral(true).queue();
        } else {
            StringBuilder response = new StringBuilder("Tasks:\n");
            for (ToDoTask task : filteredTasks) {
                response.append(task.toString()).append("\n");
            }
            event.reply(response.toString()).queue();
        }
    }
}
