package org.ttlzmc.xmc.beans

data class IndexFile(
    val themes: String,
    val libraries: String,
    val translations: String,
    val defaults: Map<String, String>
)