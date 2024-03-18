package space.dlsunity.arctour.di

import org.koin.dsl.module
import space.dlsunity.arctour.data.network.cache.ApplicationCacheStorage
import space.dlsunity.arctour.data.network.cache.impl.ApplicationCacheStorageImpl
import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.dao.BusinessDao
import space.dlsunity.arctour.data.room.dao.ConstructorDao
import space.dlsunity.arctour.data.room.dao.ConstructorTypeDao
import space.dlsunity.arctour.data.room.dao.DocDao
import space.dlsunity.arctour.data.room.dao.SectionDao
import space.dlsunity.arctour.data.room.dao.StatusDao
import space.dlsunity.arctour.data.room.dao.UserDao
import space.dlsunity.arctour.data.room.db.AppDatabase
import space.dlsunity.arctour.data.room.repositories.BdDataRepository
import space.dlsunity.arctour.data.room.repositories.UserRepository
import space.dlsunity.arctour.data.room.repositories.impl.BdDataRepositoryImpl
import space.dlsunity.arctour.data.room.repositories.impl.UserRepositoryImpl

val dataModule = module {

    single<BdDataRepository> {
        BdDataRepositoryImpl(bdDao = get<BdDao>())
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
    single<BusinessDao> { get<AppDatabase>().businessDao() }
    single<ConstructorDao> { get<AppDatabase>().constructorDao() }
    single<DocDao> { get<AppDatabase>().docDao() }
    single<StatusDao> { get<AppDatabase>().statusDao() }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<ConstructorTypeDao> { get<AppDatabase>().constructorTypeDao() }
    single<SectionDao> { get<AppDatabase>().sectionDao() }
}
