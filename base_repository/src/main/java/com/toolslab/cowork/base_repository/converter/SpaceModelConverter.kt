package com.toolslab.cowork.base_repository.converter

import com.toolslab.cowork.base_repository.model.Space
import javax.inject.Inject

class SpaceModelConverter @Inject constructor() : ModelConverter<com.toolslab.cowork.base_network.model.Space, Space> {

    override fun convert(source: com.toolslab.cowork.base_network.model.Space) = Space(source.name)
}
