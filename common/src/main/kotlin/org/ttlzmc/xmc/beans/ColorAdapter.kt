package org.ttlzmc.xmc.beans

import com.google.gson.*
import javafx.scene.paint.Color
import java.lang.reflect.Type

class ColorAdapter : JsonDeserializer<Color>, JsonSerializer<Color> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Color {
        return Color.web(json.asString)
    }

    override fun serialize(src: Color, type: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(String.format("#%02X%02X%02X",
            (src.red * 255).toInt(),
            (src.green * 255).toInt(),
            (src.blue * 255).toInt()))
    }
}