package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.factory.HitFactory.makeHit
import cl.fabianbaez.technews.factory.HitFactory.makeLocalHit
import cl.fabianbaez.technews.factory.NewsFactory.makeRemoteNews
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryImplTest {
    private val remote = mockk<Remote>()
    private val local = mockk<Local>()
    private val hiltMapper = mockk<DataHitMapper>()
    private val repository = RepositoryImpl(remote, local, hiltMapper)

    @Test
    fun `given RemoteNews, when getHits, then return data`() = runBlocking {
        val remoteNews = makeRemoteNews()
        val localHits = listOf(makeLocalHit())
        val hits = listOf(makeHit())

        coEvery { remote.getNews() } returns remoteNews
        coEvery { local.getHits() } returns flow { emit(localHits) }
        coEvery { local.storeHits(localHits) } returns Unit

        every { with(hiltMapper) { remoteNews.hits?.toLocal() } } returns localHits
        every { with(hiltMapper) { localHits.toDomain() } } returns hits

        val flow = repository.getHits()

        flow.collect { hitList ->
            remoteNews.hits?.zip(hitList)?.map {
                assertEquals(it.first.author.orEmpty(), it.second.author)
                assertEquals(it.first.createdAt.orEmpty(), it.second.createdAt)
                assertEquals(it.first.storyTitle ?: it.first.title.orEmpty(), it.second.title)
                assertEquals(it.first.url.orEmpty(), it.second.url)
            }
            assertEquals(hitList.size, remoteNews.hits?.size)
        }
    }

    @Test
    fun `given Hit id, when hideHit, then return data`() = runBlocking {
        val hit = makeLocalHit()
        val localHits = listOf(makeLocalHit())
        val hits = listOf(makeHit())

        coEvery { local.hideHit(hit) } returns Unit
        coEvery { local.getHits() } returns flow { emit(localHits) }
        every { with(hiltMapper) { localHits.toDomain() } } returns hits

        repository.hideHit(hit).collect {}

        coVerify { local.hideHit(hit) }
        coVerify { local.getHits() }

    }
}