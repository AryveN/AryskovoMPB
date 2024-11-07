package com.aryven.aryskovompb.listeners;

import com.aryven.aryskovompb.objects.LocalDateAdapter;
import com.aryven.aryskovompb.objects.ToDoTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import dev.mayuna.mayuslibrary.logging.Logger;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoController extends ListenerAdapter {
    private final List<ToDoTask> tasks = new ArrayList<>();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    private final String FILE_PATH = "tasks.json";

    public ToDoController() {
        loadTasks();
    }

    private void loadTasks() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<ToDoTask>>(){}.getType();
            List<ToDoTask> loadedTasks = gson.fromJson(reader, listType);
            if (loadedTasks != null) {
                tasks.addAll(loadedTasks);
            }
            Logger.debug("Tasks loaded from file.");
        } catch (IOException | JsonSyntaxException e) {
            Logger.error("No previous tasks found, starting fresh.");
        }
    }

    private void saveTasks() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
            Logger.debug("Tasks saved to file.");
        } catch (IOException e) {
            Logger.error("Failed to save tasks to file!\n" + e.getMessage());
        }
    }

    private void addTask(ToDoTask task) {
        tasks.add(task);
        saveTasks();
    }

    private void removeTask(ToDoTask task) {
        tasks.remove(task);
        saveTasks();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("todo_modal")) {
            ModalMapping taskInput = event.getValue("task_input");
            ModalMapping dateInput = event.getValue("date_input");

            if (taskInput != null && dateInput != null) {
                String taskDescription = taskInput.getAsString();
                LocalDate dueDate = LocalDate.parse(dateInput.getAsString());

                ToDoTask newTask = new ToDoTask(taskDescription, dueDate);
                addTask(newTask);

                event.reply("Task added:\n" + newTask).queue();
            } else {
                event.reply("Failed to add task.").setEphemeral(true).queue();
            }
        }
    }
}
