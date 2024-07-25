package com.yuventius.data.local.model.space_x

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.model.Mapper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "history_event")
data class HistoryEventLocal(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val eventDateUtc: LocalDateTime,
    val eventDateUnix: Long,
    val details: String,
    val links: ArrayList<String?>
)

object HistoryEventMapper: Mapper<HistoryEventLocal, HistoryEvent> {
    override fun HistoryEventLocal.toDomain(): HistoryEvent = HistoryEvent(
        title = title,
        eventDateUtc = eventDateUtc,
        eventDateUnix = eventDateUnix,
        details = details,
        links = links
    )

    override fun HistoryEvent.toData(): HistoryEventLocal = HistoryEventLocal(
        title = title,
        eventDateUtc = eventDateUtc,
        eventDateUnix = eventDateUnix,
        details = details,
        links = links
    )
}
