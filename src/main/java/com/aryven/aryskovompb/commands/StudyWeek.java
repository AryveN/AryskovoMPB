package com.aryven.aryskovompb.commands;

import com.aryven.aryskovompb.objects.Embeds;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StudyWeek extends SlashCommand {
    private static final LocalDate START_DATE = LocalDate.of(2024, 9, 30);

    public StudyWeek() {
        this.name = "studyweek";
        this.help = "Tells you the current study week";
        this.guildOnly = false;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        LocalDate today = LocalDate.now();
        long weeksElapsed = ChronoUnit.WEEKS.between(START_DATE, today) + 1;
        event.replyEmbeds(new Embeds().studyweek(weeksElapsed)).setEphemeral(true).queue();
    }
}
