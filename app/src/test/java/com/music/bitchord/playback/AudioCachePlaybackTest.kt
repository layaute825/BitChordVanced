package com.music.bitchord.playback

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AudioCachePlaybackTest {

    @Test
    fun livePlaylistBypassesDiskCache() {
        assertTrue(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "bitchord",
                authority = "watch",
                liveAudioOnly = true,
            ),
        )
    }

    @Test
    fun ordinaryYouTubeTracksRemainCacheable() {
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "bitchord",
                authority = "watch",
                liveAudioOnly = false,
            ),
        )
    }

    @Test
    fun sourceTracksAndFiniteSegmentsRemainCacheable() {
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "bitchord",
                authority = "source",
                liveAudioOnly = true,
            ),
        )
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "https",
                authority = "rr1.googlevideo.com",
                liveAudioOnly = false,
            ),
        )
    }

    @Test
    fun localFilesContinueToBypassDiskCache() {
        assertTrue(AudioCache.shouldBypassPlaybackCache("file", null, false))
        assertTrue(AudioCache.shouldBypassPlaybackCache("content", null, false))
    }
}
