package com.music.bitchord.data.innertube

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StreamResolverLiveTest {
    @Test
    fun recognizesLiveGoogleVideoWindows() {
        assertTrue(
            StreamResolver.isLiveUrl(
                "https://rr1---sn-example.googlevideo.com/videoplayback?expire=1900000000&live=1",
            ),
        )
        assertTrue(
            StreamResolver.isLiveUrl(
                "https://rr1---sn-example.googlevideo.com/videoplayback?source=yt_live_broadcast",
            ),
        )
    }

    @Test
    fun doesNotClassifyOrdinaryVideoUrlsAsLive() {
        assertFalse(
            StreamResolver.isLiveUrl(
                "https://rr1---sn-example.googlevideo.com/videoplayback?expire=1900000000&itag=251",
            ),
        )
        assertFalse(StreamResolver.isLiveUrl("not a url"))
    }
}
