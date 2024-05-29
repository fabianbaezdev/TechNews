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

class GetHitByIdUseCaseTest {
    private val repository = mockk<Repository>()
    private val useCase = GetHitByIdUseCase(repository)

    @Test
    fun `when calls 'execute', the returns hit`() = runBlocking {
        val hit = makeHit()
        stubRepository(hit)

        val flow = useCase.execute("id")

        flow.collect { result ->
            assertEquals(hit, result)
        }
        coVerify { repository.getHitById("id") }
    }

    private fun stubRepository(hit: Hit) {
        coEvery { repository.getHitById("id") } returns flow { emit(hit) }
    }
}