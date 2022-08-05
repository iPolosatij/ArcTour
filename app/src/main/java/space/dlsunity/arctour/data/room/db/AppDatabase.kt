package space.dlsunity.arctour.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.data.BdEntity
import space.dlsunity.arctour.data.room.db.AppDatabase.Companion.DATABASE_VERSION

@Database(entities = [ BdEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bdDao(): BdDao

    companion object {
        internal const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "database-arctour"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    //when changing the database version destroys the previous database,
                    // data migration must be performed to save the database.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
}