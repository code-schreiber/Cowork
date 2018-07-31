package com.toolslab.cowork.base_repository.model

data class Token(
        internal var token: String = ""
) {
    fun isValid() = token.isNotEmpty()

    fun invalidate() {
        token = ""
    }
}

