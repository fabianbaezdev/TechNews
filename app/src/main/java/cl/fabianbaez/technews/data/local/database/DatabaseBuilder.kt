package cl.fabianbaez.technews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.fabianbaez.technews.data.local.database.dao.HitDao
import cl.fabianbaez.technews.data.local.model.LocalHit

@Database(version = 1, entities = [LocalHit::class], exportSchema = false)
abstract class DatabaseBuilder : RoomDatabase() {
    abstract fun hitDao(): HitDao
}
