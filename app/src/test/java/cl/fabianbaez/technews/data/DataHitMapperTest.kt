package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.factory.HitFactory.makeHit
import cl.fabianbaez.technews.factory.HitFactory.makeLocalHit
import cl.fabianbaez.technews.factory.HitFactory.makeRemoteHit
import cl.fabianbaez.technews.factory.HitFactory.makeRemoteHitWithOnlyStoryTitle
import cl.fabianbaez.technews.factory.HitFactory.makeRemoteHitWithOnlyTitle
import cl.fabianbaez.technews.factory.HitFactory.makeRemoteHitWithoutData
import cl.fabianbaez.technews.factory.NewsFactory.makeRemoteNews
import junit.framework.TestCase.assertEquals
import org.junit.Test


class DataHitMapperTest {
    private val mapper = DataHitMapper()

    @Test
    fun `given RemoteNews, when toLocal, then LocalHits`() {
        val remoteNews = makeRemoteNews()
        val localHits = with(mapper) { remoteNews.hits?.toLocal() }

        remoteNews.hits?.zip(localHits.orEmpty())?.map {
            assertEquals(it.first.objectID.orEmpty(), it.second.id)
            assertEquals(it.first.author.orEmpty(), it.second.author)
            assertEquals(it.first.createdAt.orEmpty(), it.second.createdAt)
            assertEquals(it.first.storyTitle ?: it.first.title.orEmpty(), it.second.title)
            assertEquals(it.first.url.orEmpty(), it.second.url)
        }
        assertEquals(remoteNews.hits?.size, localHits?.size)
    }

    @Test
    fun `given RemoteHits, when toLocal, then LocalHits`() {
        val remoteHits = listOf(
            makeRemoteHit(),
            makeRemoteHitWithOnlyStoryTitle(),
            makeRemoteHitWithOnlyTitle(),
            makeRemoteHitWithoutData()
        )
        val localHits = with(mapper) { remoteHits.toLocal() }

        remoteHits.zip(localHits).map {
            assertEquals(it.first.objectID.orEmpty(), it.second.id)
            assertEquals(it.first.author.orEmpty(), it.second.author)
            assertEquals(it.first.createdAt.orEmpty(), it.second.createdAt)
            assertEquals(it.first.storyTitle ?: it.first.title.orEmpty(), it.second.title)
            assertEquals(it.first.url.orEmpty(), it.second.url)
        }
        assertEquals(remoteHits.size, localHits.size)
    }

    @Test
    fun `given LocalHits, when toDomain, then Hits`() {
        val localHits = listOf(makeLocalHit())
        val hits = with(mapper) { localHits.toDomain() }

        localHits.zip(hits).map {
            assertEquals(it.first.id, it.second.id)
            assertEquals(it.first.author, it.second.author)
            assertEquals(it.first.createdAt, it.second.createdAt)
            assertEquals(it.first.title, it.second.title)
            assertEquals(it.first.url, it.second.url)
        }
        assertEquals(localHits.size, hits.size)
    }

    @Test
    fun `given Hit, when toLocal, then LocalHit`() {
        val hit = makeHit()
        val localHit = with(mapper) { hit.toLocal() }

        assertEquals(hit.id, localHit.id)
        assertEquals(hit.author, localHit.author)
        assertEquals(hit.createdAt, localHit.createdAt)
        assertEquals(hit.title, localHit.title)
        assertEquals(hit.url, localHit.url)
    }
}