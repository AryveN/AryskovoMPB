package com.aryven.aryskovompb.listeners;

import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MusicController extends ListenerAdapter {

    public MusicController(AudioPlayerManager playerManager) {

        playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setResamplingQuality(AudioConfiguration.ResamplingQuality.HIGH);
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        playerManager.registerSourceManager(SoundCloudAudioSourceManager.createDefault());
    }
}
