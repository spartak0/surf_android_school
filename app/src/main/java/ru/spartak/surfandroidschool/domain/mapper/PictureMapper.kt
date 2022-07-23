package ru.spartak.surfandroidschool.domain.mapper

import ru.spartak.surfandroidschool.data.database.picture_database.entity.PictureEntity
import ru.spartak.surfandroidschool.data.network.dto.Picture
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.utils.Mapper

class PictureMapper : Mapper<PictureData, Picture, PictureEntity> {
    override fun dtoToDomain(dto: Picture): PictureData {
        return PictureData(dto.id, dto.title, dto.content, dto.photoUrl, dto.publicationDate, false)
    }

    override fun entityToDomain(entity: PictureEntity): PictureData {
        return PictureData(entity.id, entity.title, entity.content, entity.photoUrl, entity.publicationDate, entity.isFavorite)
    }

    override fun domainToEntity(domain: PictureData): PictureEntity {
        TODO("Not yet implemented")
    }

}