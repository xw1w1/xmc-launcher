package org.ttlzmc.xmc.beans

data class IndexFile(
    val themes: String,
    val images: String,
    val translations: String,
    val defaults: Map<String, String>
)