package space.dlsunity.simple_crm.data.network.cache.impl

import kotlinx.coroutines.flow.MutableStateFlow
import space.dlsunity.simple_crm.data.network.cache.ApplicationCacheStorage
import space.dlsunity.simple_crm.domain.model.exception.NetworkException
import java.time.LocalDateTime
import java.time.ZoneId

class ApplicationCacheStorageImpl : ApplicationCacheStorage {
    override var lastCheckUpdate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault())

    override val blockDevice: MutableStateFlow<NetworkException.BlockDevice?> = MutableStateFlow(null)

}