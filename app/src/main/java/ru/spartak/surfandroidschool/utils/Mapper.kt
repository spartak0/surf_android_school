package ru.spartak.surfandroidschool.utils

interface Mapper<Domain, Dto, Entity> {
    fun dtoToDomain(dto: Dto): Domain
    fun entityToDomain(entity: Entity):Domain
    fun domainToEntity(domain: Domain):Entity
}