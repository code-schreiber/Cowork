package com.toolslab.cowork.base_repository.converter

import com.toolslab.cowork.base_repository.model.Space
import javax.inject.Inject

class SpaceModelConverter @Inject constructor() : Converter<com.toolslab.cowork.base_network.model.Space, Space> {

    @Inject
    internal lateinit var coordinateConverter: CoordinateConverter

    override fun convert(source: com.toolslab.cowork.base_network.model.Space) =
            Space(source.name,
                    source.map.address,
                    coordinateConverter.convert(source.map.lat),
                    coordinateConverter.convert(source.map.lng))
}
