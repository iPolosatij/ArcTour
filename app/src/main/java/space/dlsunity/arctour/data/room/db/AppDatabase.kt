package space.dlsunity.arctour.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import space.dlsunity.arctour.data.room.dao.*
import space.dlsunity.arctour.data.room.data.*
import space.dlsunity.arctour.data.room.data.sketch.BdEntity
import space.dlsunity.arctour.data.room.db.AppDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [
        BdEntity::class,
        Business::class,
        Constructor::class,
        Doc::class, Status::class,
        User::class,
        ConstructorType::class,
        Section::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun bdDao(): BdDao
    abstract fun businessDao(): BusinessDao
    abstract fun constructorDao(): ConstructorDao
    abstract fun docDao(): DocDao
    abstract fun statusDao(): StatusDao
    abstract fun userDao(): UserDao
    abstract fun constructorTypeDao(): ConstructorTypeDao
    abstract fun sectionDao(): SectionDao

    companion object {
        internal const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "database-business-line"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
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