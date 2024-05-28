package cl.fabianbaez.technews.data.remote

import cl.fabianbaez.technews.data.remote.retrofit.Webservice
import cl.fabianbaez.technews.factory.NewsFactory.makeRemoteNews
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoteImplTest {
    private val webService = mockk<Webservice>()
    private val remote = RemoteImpl(webService)

    @Test
    fun `given RemoteNews, when getNews, the return data`() = runBlocking {
        val remoteNews = makeRemoteNews()
        coEvery { webService.getNews() } returns remoteNews

        val result = remote.getNews()

        assertEquals(remoteNews, result)
    }
}