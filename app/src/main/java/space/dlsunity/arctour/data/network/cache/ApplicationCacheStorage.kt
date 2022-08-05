package space.dlsunity.arctour.data.network.cache

import kotlinx.coroutines.flow.MutableStateFlow
import space.dlsunity.arctour.domain.model.exception.NetworkException
import java.time.LocalDateTime

interface ApplicationCacheStorage {

    var lastCheckUpdate: LocalDateTime

    val blockDevice: MutableStateFlow<NetworkException.BlockDevice?>

}