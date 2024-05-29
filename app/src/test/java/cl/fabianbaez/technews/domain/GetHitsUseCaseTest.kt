package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.factory.HitFactory.makeHit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetHitsUseCaseTest {
    private val repository = mockk<Repository>()
    private val useCase = GetHitsUseCase(repository)

    @Test
    fun `when calls 'execute', the returns Hits`() = runBlocking {
        val hits = listOf(makeHit())
        stubRepository(hits)

        val flow = useCase.execute()

        flow.collect { result ->
            assertEquals(hits, result)
        }
        coVerify { repository.getHits() }
    }

    private fun stubRepository(hits: List<Hit>) {
        coEvery { repository.getHits() } returns flow { emit(hits) }
    }
}