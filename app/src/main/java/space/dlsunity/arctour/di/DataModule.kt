package space.dlsunity.arctour.di

import org.koin.dsl.module
import space.dlsunity.arctour.data.network.cache.ApplicationCacheStorage
import space.dlsunity.arctour.data.network.cache.impl.ApplicationCacheStorageImpl
import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.db.AppDatabase
import space.dlsunity.arctour.data.room.repositories.BdDataRepository
import space.dlsunity.arctour.data.room.repositories.impl.BdDataRepositoryImpl

val dataModule = module {

    single<BdDataRepository> {
        BdDataRepositoryImpl(bdDao = get<BdDao>())
    }

    single<ApplicationCacheStorage> {
        ApplicationCacheStorageImpl()
    }

    // room data base
    single<AppDatabase> {
        AppDatabase.getInstance(get())
    }

    single<BdDao> { get<AppDatabase>().bdDao() }
}
