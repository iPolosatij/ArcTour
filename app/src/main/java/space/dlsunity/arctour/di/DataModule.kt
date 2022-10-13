package space.dlsunity.arctour.di

import org.koin.dsl.module
import space.dlsunity.arctour.data.network.cache.ApplicationCacheStorage
import space.dlsunity.arctour.data.network.cache.impl.ApplicationCacheStorageImpl
import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.dao.TournamentDao
import space.dlsunity.arctour.data.room.dao.UserDao
import space.dlsunity.arctour.data.room.db.AppDatabase
import space.dlsunity.arctour.data.room.repositories.BdDataRepository
import space.dlsunity.arctour.data.room.repositories.TournamentRepository
import space.dlsunity.arctour.data.room.repositories.UserRepository
import space.dlsunity.arctour.data.room.repositories.impl.BdDataRepositoryImpl
import space.dlsunity.arctour.data.room.repositories.impl.TournamentRepositoryImpl
import space.dlsunity.arctour.data.room.repositories.impl.UserRepositoryImpl

val dataModule = module {

    single<BdDataRepository> {
        BdDataRepositoryImpl(bdDao = get<BdDao>())
    }

    single<TournamentRepository> {
        TournamentRepositoryImpl(tournamentDao = get<TournamentDao>())
    }

    single<UserRepository> {
        UserRepositoryImpl(userDao = get<UserDao>())
    }

    single<ApplicationCacheStorage> {
        ApplicationCacheStorageImpl()
    }

    // room data base
    single<AppDatabase> {
        AppDatabase.getInstance(get())
    }

    single<BdDao> { get<AppDatabase>().bdDao() }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<TournamentDao> { get<AppDatabase>().tournamentDao() }
}
