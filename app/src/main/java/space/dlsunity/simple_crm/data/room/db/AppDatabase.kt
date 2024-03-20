package space.dlsunity.simple_crm.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import space.dlsunity.simple_crm.data.room.dao.BdDao
import space.dlsunity.simple_crm.data.room.dao.BusinessDao
import space.dlsunity.simple_crm.data.room.dao.ConstructorDao
import space.dlsunity.simple_crm.data.room.dao.ConstructorTypeDao
import space.dlsunity.simple_crm.data.room.dao.DocDao
import space.dlsunity.simple_crm.data.room.dao.SectionDao
import space.dlsunity.simple_crm.data.room.dao.StatusDao
import space.dlsunity.simple_crm.data.room.dao.UserDao
import space.dlsunity.simple_crm.data.room.data.Business
import space.dlsunity.simple_crm.data.room.data.Constructor
import space.dlsunity.simple_crm.data.room.data.ConstructorType
import space.dlsunity.simple_crm.data.room.data.Doc
import space.dlsunity.simple_crm.data.room.data.Section
import space.dlsunity.simple_crm.data.room.data.Status
import space.dlsunity.simple_crm.data.room.data.UserEntity
import space.dlsunity.simple_crm.data.room.data.sketch.BdEntity
import space.dlsunity.simple_crm.data.room.db.AppDatabase.Companion.DATABASE_VERSION

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