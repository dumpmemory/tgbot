package me.kuku.telegram.entity

import kotlinx.coroutines.flow.toList
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

open class NetEaseBaseEntity: BaseEntity() {
    var musicU: String = ""
    var csrf: String = ""

    fun cookie() = "channel=netease; __remember_me=true; MUSIC_U=$musicU; __csrf=$csrf; "

    fun pcCookie() = "os=pc; ${cookie()}"

    fun androidCookie() = "os=android; ${cookie()}"
}

@Document("net_ease")
class NetEaseEntity: NetEaseBaseEntity() {
    @Id
    var id: String? = null
    var sign: Status = Status.OFF
    var musicianSign: Status = Status.OFF
    var vipSign: Status = Status.OFF
    var listen: Status = Status.OFF


}

interface NetEaseRepository: CoroutineCrudRepository<NetEaseEntity, String> {

    suspend fun findByTgIdAndTgName(tgId: Long, tgName: String?): NetEaseEntity?

    suspend fun findBySign(sign: Status): List<NetEaseEntity>

    suspend fun findByMusicianSign(musicianSign: Status): List<NetEaseEntity>

    suspend fun deleteByTgIdAndTgName(tgId: Long, tgName: String?)

    suspend fun findByVipSign(status: Status): List<NetEaseEntity>

    suspend fun findByListen(listen: Status): List<NetEaseEntity>

}

@Service
class NetEaseService(
    private val netEaseRepository: NetEaseRepository
) {

    suspend fun findByTgId(tgId: Long) = netEaseRepository.findEnableEntityByTgId(tgId) as? NetEaseEntity

    suspend fun findBySign(sign: Status): List<NetEaseEntity> = netEaseRepository.findBySign(sign)

    suspend fun findByMusicianSign(musicianSign: Status): List<NetEaseEntity> = netEaseRepository.findByMusicianSign(musicianSign)

    suspend fun save(netEaseEntity: NetEaseEntity): NetEaseEntity = netEaseRepository.save(netEaseEntity)

    suspend fun findAll(netEaseEntity: NetEaseEntity): List<NetEaseEntity> = netEaseRepository.findAll().toList()

    @Transactional
    suspend fun deleteByTgId(tgId: Long) = netEaseRepository.deleteEnableEntityByTgId(tgId)

    suspend fun findByVipSign(status: Status) = netEaseRepository.findByVipSign(status)

    suspend fun findByListen(listen: Status) = netEaseRepository.findByListen(listen)

}
