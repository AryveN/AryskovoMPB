package com.aryven.aryskovompb.objects;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Embeds {
    public MessageEmbed help() {
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("❓ 〃 Help");
        help.setDescription("All commands are slash commands. They can be invoked with /<command name>");
        help.addField("Currently available commands:", """
                        /help - Shows this message
                        /serverInfo - Shows server info about the server
                        /studyweek - Tells you the current study week
        """,false);
        help.setColor(Color.decode("#ce5253"));
        return help.build();
    }

    public MessageEmbed serverInfo(SlashCommandEvent event) {
        EmbedBuilder serverInfo = new EmbedBuilder();
        Guild guild = event.getGuild();
        Member owner = guild.getOwner();

        //Get number of all members

        AtomicLong membersCount = new AtomicLong();
        guild.loadMembers().onSuccess(loadedMembers -> {
            membersCount.set(loadedMembers.size());
        });

        //Get number of online members
        int memberCount = guild.retrieveMetaData()
                .map(Guild.MetaData::getApproximatePresences)
                .complete();

        //Get creation time of a server and format it to Discord Timestamp
        OffsetDateTime creationTime = event.getGuild().getTimeCreated();
        long creationTimestamp = creationTime.toEpochSecond();
        String formattedTimestamp = "<t:" + creationTimestamp + ":F>";

        serverInfo.setTitle("❓ 〃" + guild.getName() + " Info");
        serverInfo.addField("Owner", owner.getUser().getAsMention(), false);
        serverInfo.addField("Text Channels", String.valueOf(guild.getTextChannelCache().size()), true);
        serverInfo.addField("Voice Channels", String.valueOf(guild.getVoiceChannelCache().size()), true);
        serverInfo.addField("Categories", String.valueOf(guild.getCategoryCache().size()) ,true);
        serverInfo.addField("Online Members", String.valueOf(memberCount), true);
        serverInfo.addField("Members", String.valueOf(guild.getMemberCount()), true);
        serverInfo.addField("Roles", String.valueOf(guild.getRoleCache().size()), true);
        serverInfo.addField("Created at", formattedTimestamp, false);
        serverInfo.setFooter("Server ID: " + guild.getId());
        serverInfo.setThumbnail(guild.getIconUrl());
        serverInfo.setColor(Color.decode("#add8e6"));
        return serverInfo.build();
    }

    public MessageEmbed studyweek(long weeksElapsed) {
        EmbedBuilder studyWeek = new EmbedBuilder();
        studyWeek.setTitle("\uD83D\uDCC5 〃 Study Week");
        studyWeek.setDescription("Today is `" + LocalDate.now() + "`");
        studyWeek.addField("Current study week","This week is `"+ weeksElapsed +".` week.",false);
        studyWeek.setColor(Color.decode("#ce5253"));
        return studyWeek.build();
    }

    public MessageEmbed todoListEmbed(List<ToDoTask> tasks) {
        //Sort Tasks
        tasks.sort(Comparator.comparing(ToDoTask::getDueDate));
        //Group tasks by due date
        Map<LocalDate, List<ToDoTask>> tasksByDate = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getDueDate().toLocalDate()));

        List<LocalDate> sortedDates = tasksByDate.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        EmbedBuilder todoListEmbed = new EmbedBuilder();
        for (LocalDate date : sortedDates) {
            List<ToDoTask> taskList = tasksByDate.get(date);
            StringBuilder taskDescription = new StringBuilder();
            for(ToDoTask task : taskList) {
                LocalDateTime dueDateTime = task.getDueDate();
                String dueTime = dueDateTime.format(timeFormatter);

                ZoneId pragueZoneId = ZoneId.of("Europe/Prague");
                long timestamp = dueDateTime.atZone(pragueZoneId).toEpochSecond();

                taskDescription.append(task.getName())
                        .append(" - ")
                        .append(dueTime)
                        .append(" (<t:").append(timestamp).append(":R>)\n");
            }
            todoListEmbed.addField(dateFormatter.format(date), taskDescription.toString(), false);
        }

        todoListEmbed.setTitle("\uD83D\uDDD2\uFE0F 〃 To-Do List");
        todoListEmbed.setColor(Color.decode("#ce5253"));
        return todoListEmbed.build();
    }

    public MessageEmbed todoListEmptyEmbed() {
        EmbedBuilder todoListEmptyEmbed = new EmbedBuilder();
        todoListEmptyEmbed.setTitle("\uD83D\uDDD2\uFE0F 〃 To-Do List");
        todoListEmptyEmbed.setDescription("No tasks in the To-Do list.");
        todoListEmptyEmbed.setColor(Color.decode("#ce5253"));
        return todoListEmptyEmbed.build();
    }

    public MessageEmbed todoCreateEmbed(ToDoTask newTask) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dueDate = newTask.getDueDate().format(formatter);

        EmbedBuilder todoCreateEmbed = new EmbedBuilder();
        todoCreateEmbed.setTitle("\uD83D\uDDD2\uFE0F 〃 Task Created");
        todoCreateEmbed.addField(newTask.getName(),newTask.getDescription(),true);
        todoCreateEmbed.addField("Due Date",dueDate,true);
        todoCreateEmbed.setFooter("Task ID: " + newTask.getId());
        todoCreateEmbed.setColor(Color.decode("#45cc23"));
        return todoCreateEmbed.build();
    }

    public MessageEmbed todoCreateFailedEmbed() {
        EmbedBuilder todoCreateFailedEmbed = new EmbedBuilder();
        todoCreateFailedEmbed.setTitle("\uD83D\uDDD2\uFE0F 〃 Task Creation Failed");
        todoCreateFailedEmbed.addField("Please Try Again","Check if you filled in all the fields with the right parameters.",false);
        todoCreateFailedEmbed.setColor(Color.decode("#ce5253"));
        return todoCreateFailedEmbed.build();
    }

    public MessageEmbed todoTaskEmbed(ToDoTask task) {
        EmbedBuilder todoTaskEmbed = new EmbedBuilder();
        todoTaskEmbed.setTitle("\uD83D\uDDD2\uFE0F 〃 " + task.getName());
        todoTaskEmbed.setDescription(task.getDescription());

        ZoneId pragueZoneId = ZoneId.of("Europe/Prague");
        long timestamp = task.getDueDate().atZone(pragueZoneId).toEpochSecond();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dueDate = task.getDueDate().format(formatter);

        todoTaskEmbed.addField("Due Date:", dueDate + " (<t:"+timestamp+":R>)", false);
        todoTaskEmbed.setFooter("Task ID: " + task.getId());
        todoTaskEmbed.setColor(Color.decode("#ce5253"));
        return todoTaskEmbed.build();
    }
}
