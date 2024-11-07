package com.aryven.aryskovompb.objects;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.mayuna.mayuslibrary.logging.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

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
}
