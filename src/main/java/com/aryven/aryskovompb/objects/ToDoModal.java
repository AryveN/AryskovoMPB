package com.aryven.aryskovompb.objects;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.time.LocalDateTime;

public class ToDoModal {
    public Modal createToDoModal(SlashCommandEvent event) {
        TextInput nameInput = TextInput.create("name_input", "Task Name", TextInputStyle.SHORT)
                .setPlaceholder("e.g., Buy Groceries")
                .setRequired(true)
                .build();

        TextInput taskInput = TextInput.create("task_input", "Task Description", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Describe the task")
                .setRequired(false)
                .build();

        TextInput dateInput = TextInput.create("date_input", "Due Date (YYYY-MM-DD HH:mm)", TextInputStyle.SHORT)
                .setPlaceholder("e.g., " + LocalDateTime.now())
                .setRequired(true)
                .build();

        Modal modal = Modal.create("todo_modal", "Add a Todo Task")
                .addComponents(ActionRow.of(nameInput), ActionRow.of(taskInput), ActionRow.of(dateInput))
                .build();

        return modal;
    }
}
