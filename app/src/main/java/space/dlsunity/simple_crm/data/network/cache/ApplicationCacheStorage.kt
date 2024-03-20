package space.dlsunity.simple_crm.data.network.cache

import kotlinx.coroutines.flow.MutableStateFlow
import space.dlsunity.simple_crm.domain.model.exception.NetworkException
import java.time.LocalDateTime

interface ApplicationCacheStorage {

    var lastCheckUpdate: LocalDateTime

    val blockDevice: MutableStateFlow<NetworkException.BlockDevice?>

}