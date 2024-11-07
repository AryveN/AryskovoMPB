package com.aryven.aryskovompb.objects;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class ToDoModal {
    public Modal createToDoModal(SlashCommandEvent event) {
        TextInput taskInput = TextInput.create("task_input", "Task Description", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Describe the task")
                .setRequired(true)
                .build();

        TextInput dateInput = TextInput.create("date_input", "Due Date (YYYY-MM-DD)", TextInputStyle.SHORT)
                .setPlaceholder("e.g., 2024-11-07")
                .setRequired(true)
                .build();

        Modal modal = Modal.create("todo_modal", "Add a Todo Task")
                .addComponents(ActionRow.of(taskInput), ActionRow.of(dateInput))
                .build();

        return modal;
    }
}
