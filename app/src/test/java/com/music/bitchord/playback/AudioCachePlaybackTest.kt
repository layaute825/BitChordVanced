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
                host = "watch",
                path = "/",
                liveAudioOnly = true,
            ),
        )
    }

    @Test
    fun ordinaryYouTubeTracksRemainCacheable() {
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "bitchord",
                host = "watch",
                path = "/",
                liveAudioOnly = false,
            ),
        )
    }

    @Test
    fun sourceTracksAndFiniteSegmentsRemainCacheable() {
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "bitchord",
                host = "source",
                path = "/",
                liveAudioOnly = true,
            ),
        )
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "https",
                host = "rr1.googlevideo.com",
                path = "/videoplayback",
                liveAudioOnly = false,
            ),
        )
    }

    @Test
    fun refreshedYouTubeHlsManifestsBypassCacheWithoutTheVirtualMarker() {
        assertTrue(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "https",
                host = "manifest.googlevideo.com",
                path = "/api/manifest/hls_playlist/123",
                liveAudioOnly = false,
            ),
        )
        assertFalse(
            AudioCache.shouldBypassPlaybackCache(
                scheme = "https",
                host = "rr1.googlevideo.com",
                path = "/videoplayback/segment",
                liveAudioOnly = false,
            ),
        )
    }

    @Test
    fun localFilesContinueToBypassDiskCache() {
        assertTrue(AudioCache.shouldBypassPlaybackCache("file", null, null, false))
        assertTrue(AudioCache.shouldBypassPlaybackCache("content", null, null, false))
    }
}
