package com.aryven.aryskovompb.objects;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds {
    public MessageEmbed help() {
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("❓ 〃 Help");
        help.setDescription("All commands are slash commands. They can be invoked with /<command name>");
        help.addField("Currently available commands:", """
                        /help - Shows this message
                        /serverInfo - Shows server info about the server
        """,false);
        help.setColor(Color.decode("#ce5253"));
        return help.build();
    }

    public MessageEmbed serverInfo(SlashCommandEvent event) {
        EmbedBuilder serverInfo = new EmbedBuilder();

        String LINESTART = "\u00B7";
        String NO_REGION = "\u2754";

        Guild guild = event.getGuild();
        Member owner = guild.getOwner();
        long onlineCount = guild.getMembers().stream().filter(u -> u.getOnlineStatus() != OnlineStatus.OFFLINE).count();
        long botCount = guild.getMembers().stream().filter(m -> m.getUser().isBot()).count();

        /*
        String str = LINESTART + "ID: **" + guild.getId() + "**\n"
                + LINESTART + "Owner: **" + owner.getUser().getAsTag() + "**\n";
        if (!guild.getVoiceChannels().isEmpty()) {
            Region reg = guild.getVoiceChannels().get(0).getRegion();
            str += LINESTART + "Location: " + (reg.getEmoji() == null || reg.getEmoji().isEmpty() ? NO_REGION : reg.getEmoji()) + " **" + reg.getName() + "**\n";
        }
        str +=    LINESTART + "Creation: **" + guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "**\n"
                + LINESTART + "Users: **" + guild.getMemberCache().size() + "** (" + onlineCount + " online, " + botCount + " bots)\n"
                + LINESTART + "Channels: **" + guild.getTextChannelCache().size() + "** Text, **" + guild.getVoiceChannelCache().size() + "** Voice, **" + guild.getCategoryCache().size() + "** Categories\n"
                + LINESTART + "Verification: **" + guild.getVerificationLevel().name() + "**";
        if(!guild.getFeatures().isEmpty())
            str += "\n" + LINESTART + "Features: **" + String.join("**, **", guild.getFeatures()) + "**";
        if(guild.getSplashId() != null)
        {
            serverInfo.setImage(guild.getSplashUrl() + "?size=1024");
            str += "\n" + LINESTART + "Splash: ";
        }
        if(guild.getIconUrl()!=null)
            serverInfo.setThumbnail(guild.getIconUrl());

         */

        long onlineMembers = guild.getMemberCache().stream()
                .filter(member -> {
                    OnlineStatus status = member.getOnlineStatus();
                    return status == OnlineStatus.ONLINE || status == OnlineStatus.DO_NOT_DISTURB || status == OnlineStatus.IDLE;
                })
                .count();

        OffsetDateTime creationTime = event.getGuild().getTimeCreated();
        long creationTimestamp = creationTime.toEpochSecond();
        String formattedTimestamp = "<t:" + creationTimestamp + ":F>";


        serverInfo.setTitle("❓ 〃" + guild.getName() + " Info");
        //serverInfo.setDescription(str);
        serverInfo.addField("Owner", owner.getUser().getAsMention(), false);
        serverInfo.addField("Text Channels", String.valueOf(guild.getTextChannelCache().size()), true);
        serverInfo.addField("Voice Channels", String.valueOf(guild.getVoiceChannelCache().size()), true);
        serverInfo.addField("Categories", String.valueOf(guild.getCategoryCache().size()) ,true);
        serverInfo.addField("Online Members", String.valueOf(onlineMembers), true);
        serverInfo.addField("Members", String.valueOf(guild.getMembers().size()), true);
        serverInfo.addField("Roles", String.valueOf(guild.getRoleCache().size()), true);
        serverInfo.addField("Created at", formattedTimestamp, false);
        serverInfo.setFooter("Server ID: " + guild.getId());
        serverInfo.setThumbnail(guild.getIconUrl());
        serverInfo.setColor(Color.decode("#add8e6"));
        return serverInfo.build();
    }
}
