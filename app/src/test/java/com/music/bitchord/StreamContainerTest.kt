package com.music.bitchord

import com.music.bitchord.playback.StreamContainer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class StreamContainerTest {

    @Test
    fun `dash reopen changes virtual uri while preserving playback identity`() {
        val original = "bitchord://watch?v=iQnRCdtECl8&n=Wait&a=M83&d=344"

        val reopened = StreamContainer.markedForManifestReopen(
            original,
            "application/dash+xml",
        )

        assertNotEquals(original, reopened)
        assertEquals("$original&manifest_reopen=dash", reopened)
    }

    @Test
    fun `hls source-track reopen preserves source parameters and fragment`() {
        val original = "bitchord://source?s=addon-1&t=track-2&n=Wait#part"

        assertEquals(
            "bitchord://source?s=addon-1&t=track-2&n=Wait&manifest_reopen=hls#part",
            StreamContainer.markedForManifestReopen(
                original,
                "application/x-mpegURL",
            ),
        )
    }

    @Test
    fun `declared hls mime identifies extensionless playlist urls`() {
        val playlist = "https://manifest.googlevideo.com/api/manifest/hls_playlist?id=live"
        StreamContainer.declare(playlist, "hls")

        assertEquals(
            "application/x-mpegURL",
            StreamContainer.manifestMimeOf(playlist),
        )
    }

    @Test
    fun `non-manifest mime does not force a source rebuild`() {
        val original = "bitchord://watch?v=iQnRCdtECl8"

        assertEquals(
            original,
            StreamContainer.markedForManifestReopen(original, "audio/flac"),
        )
    }
}
