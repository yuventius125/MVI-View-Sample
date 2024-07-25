package com.yuventius.data.remote.model.space_x

import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.model.Mapper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryEventRemote(
    val title: String,
    @SerialName("event_date_utc")
    val eventDateUtc: String,
    @SerialName("event_date_unix")
    val eventDateUnix: Long,
    val details: String,
    val links: LinksRemote
)

object HistoryMapperRemote: Mapper<HistoryEventRemote, HistoryEvent> {
    override fun HistoryEventRemote.toDomain(): HistoryEvent = HistoryEvent(
        title = title,
        eventDateUtc = LocalDateTime.parse(eventDateUtc),
        eventDateUnix = eventDateUnix,
        details = details,
        links = arrayListOf(links.article)
    )

    override fun HistoryEvent.toData(): HistoryEventRemote = HistoryEventRemote(
        title = title,
        eventDateUtc = eventDateUtc.toString(),
        eventDateUnix = eventDateUnix,
        details = details,
        links = LinksRemote(links[0])
    )
}
