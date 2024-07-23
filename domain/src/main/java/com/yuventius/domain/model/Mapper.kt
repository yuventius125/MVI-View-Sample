package com.yuventius.domain.model

interface Mapper<Data, Domain> {
    fun Data.toDomain(): Domain
    fun Domain.toData(): Data
}