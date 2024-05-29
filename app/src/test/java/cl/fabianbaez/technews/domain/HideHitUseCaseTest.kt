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

class HideHitUseCaseTest {
    private val repository = mockk<Repository>()
    private val useCase = HideHitUseCase(repository)

    @Test
    fun `when calls 'execute', the returns hits`() = runBlocking {
        val hit = makeHit()
        val hits = listOf(makeHit())
        stubRepository(hit, hits)

        val flow = useCase.execute(hit)

        flow.collect { result ->
            assertEquals(hits, result)
        }
        coVerify { repository.hideHit(hit) }
    }

    private fun stubRepository(hit: Hit, hits: List<Hit>) {
        coEvery { repository.hideHit(hit) } returns flow { emit(hits) }
    }
}