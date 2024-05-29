package cl.fabianbaez.technews.data.local

import cl.fabianbaez.technews.data.local.database.DatabaseBuilder
import cl.fabianbaez.technews.factory.HitFactory.makeLocalHit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalImplTest {
    private val databaseBuilder = mockk<DatabaseBuilder>()
    private val local = LocalImpl(databaseBuilder)

    @Test
    fun `given LocalHits, when storeHits, verify function`() = runBlocking {
        val localHits = listOf(makeLocalHit())
        coEvery { databaseBuilder.hitDao().insertAll(localHits) } returns Unit
        local.storeHits(localHits)
        coVerify { databaseBuilder.hitDao().insertAll(localHits) }

    }

    @Test
    fun `given LocalHit, when getHitById, the return data`() = runBlocking {
        val localHit = makeLocalHit()
        coEvery { databaseBuilder.hitDao().getById("id") } returns localHit

        val flow = local.getHitById("id")
        flow.collect { result ->
            assertEquals(localHit, result)
        }
    }
    @Test
    fun `given LocalHit, when getHits, the return data`() = runBlocking {
        val localHits = listOf(makeLocalHit())
        coEvery { databaseBuilder.hitDao().getAll() } returns localHits

        val flow = local.getHits()
        flow.collect { result ->
            assertEquals(localHits, result)
        }
    }

    @Test
    fun `given LocalHit, when hideHit, verify function`() = runBlocking {
        val localHit = makeLocalHit()
        coEvery { databaseBuilder.hitDao().updateHit(localHit.copy(hide = true)) } returns Unit
        local.hideHit(localHit)
        coVerify { databaseBuilder.hitDao().updateHit(localHit.copy(hide = true)) }

    }

}