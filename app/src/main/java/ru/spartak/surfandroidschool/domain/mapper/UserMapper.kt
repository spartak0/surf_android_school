package ru.spartak.surfandroidschool.domain.mapper

import ru.spartak.surfandroidschool.data.database.user_database.entity.UserEntity
import ru.spartak.surfandroidschool.data.network.dto.User
import ru.spartak.surfandroidschool.domain.model.UserData
import ru.spartak.surfandroidschool.utils.Mapper

class UserMapper : Mapper<UserData, User, UserEntity> {

    override fun dtoToDomain(dto: User): UserData {
        return UserData(
            dto.id,
            dto.phone,
            dto.email,
            dto.firstName,
            dto.lastName,
            dto.avatar,
            dto.city,
            dto.about
        )
    }

    override fun entityToDomain(entity: UserEntity): UserData {
        return UserData(
            entity.id,
            entity.phone,
            entity.email,
            entity.firstName,
            entity.lastName,
            entity.avatar,
            entity.city,
            entity.about
        )
    }

    override fun domainToEntity(domain: UserData): UserEntity {
        return UserEntity(
            domain.id,
            domain.phone,
            domain.email,
            domain.firstName,
            domain.lastName,
            domain.avatar,
            domain.city,
            domain.about
        )
    }

}