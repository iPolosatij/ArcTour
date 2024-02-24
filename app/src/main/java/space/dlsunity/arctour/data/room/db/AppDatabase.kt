package space.dlsunity.arctour.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.dao.BusinessDao
import space.dlsunity.arctour.data.room.dao.ConstructorDao
import space.dlsunity.arctour.data.room.dao.ConstructorTypeDao
import space.dlsunity.arctour.data.room.dao.DocDao
import space.dlsunity.arctour.data.room.dao.SectionDao
import space.dlsunity.arctour.data.room.dao.StatusDao
import space.dlsunity.arctour.data.room.dao.UserDao
import space.dlsunity.arctour.data.room.data.Business
import space.dlsunity.arctour.data.room.data.Constructor
import space.dlsunity.arctour.data.room.data.ConstructorType
import space.dlsunity.arctour.data.room.data.Doc
import space.dlsunity.arctour.data.room.data.Section
import space.dlsunity.arctour.data.room.data.Status
import space.dlsunity.arctour.data.room.data.UserEntity
import space.dlsunity.arctour.data.room.data.sketch.BdEntity
import space.dlsunity.arctour.data.room.db.AppDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [
        BdEntity::class,
        Business::class,
        Constructor::class,
        Doc::class, Status::class,
        UserEntity::class,
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