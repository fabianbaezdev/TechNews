package cl.fabianbaez.technews.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import cl.fabianbaez.technews.data.local.model.HIT_TABLE
import cl.fabianbaez.technews.data.local.model.LocalHit

@Dao
interface HitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(localHits: List<LocalHit>)

    @Transaction
    @Query("SELECT * FROM $HIT_TABLE WHERE hide=0")
    suspend fun getAll(): List<LocalHit>

    @Transaction
    @Query("SELECT * FROM $HIT_TABLE WHERE id=:id")
    suspend fun getById(id: String): LocalHit

    @Update
    fun updateHit(hit: LocalHit)

}
