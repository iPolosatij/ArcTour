package space.dlsunity.simple_crm.di

import org.koin.dsl.module
import space.dlsunity.simple_crm.data.network.cache.ApplicationCacheStorage
import space.dlsunity.simple_crm.data.network.cache.impl.ApplicationCacheStorageImpl
import space.dlsunity.simple_crm.data.room.dao.BdDao
import space.dlsunity.simple_crm.data.room.dao.BusinessDao
import space.dlsunity.simple_crm.data.room.dao.ConstructorDao
import space.dlsunity.simple_crm.data.room.dao.ConstructorTypeDao
import space.dlsunity.simple_crm.data.room.dao.DocDao
import space.dlsunity.simple_crm.data.room.dao.SectionDao
import space.dlsunity.simple_crm.data.room.dao.StatusDao
import space.dlsunity.simple_crm.data.room.dao.UserDao
import space.dlsunity.simple_crm.data.room.db.AppDatabase
import space.dlsunity.simple_crm.data.room.repositories.BdDataRepository
import space.dlsunity.simple_crm.data.room.repositories.UserRepository
import space.dlsunity.simple_crm.data.room.repositories.impl.BdDataRepositoryImpl
import space.dlsunity.simple_crm.data.room.repositories.impl.UserRepositoryImpl

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
