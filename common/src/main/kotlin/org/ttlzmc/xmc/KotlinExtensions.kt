package org.ttlzmc.xmc

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File

fun File.parseJson(defaults: String = "{}"): JsonObject {
    val text = this.readText().ifBlank { defaults }
    return JsonParser.parseString(text).asJsonObject
}