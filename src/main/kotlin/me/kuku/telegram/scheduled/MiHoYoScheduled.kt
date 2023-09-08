package me.kuku.telegram.scheduled

import kotlinx.coroutines.delay
import me.kuku.telegram.entity.*
import me.kuku.telegram.logic.MiHoYoLogic
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MiHoYoScheduled(
    private val miHoYoService: MiHoYoService, private val miHoYoLogic: MiHoYoLogic,
    private val logService: LogService
) {


    @Scheduled(cron = "0 13 5 * * ?")
    suspend fun genShinSign() {
        val list = miHoYoService.findBySign(Status.ON)
        for (miHoYoEntity in list) {
            logService.log(miHoYoEntity.tgId, LogType.GenShin) {
                miHoYoLogic.sign(miHoYoEntity)
            }
            delay(3000)
        }
    }


}
