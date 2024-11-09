package com.aryven.aryskovompb.objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ToDoTask {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;

    public ToDoTask(String name, String description, LocalDateTime dueDate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return "**" + id + "** | "+ name +" - " + description + " (Due: " + dueDate + ")";
    }
}
