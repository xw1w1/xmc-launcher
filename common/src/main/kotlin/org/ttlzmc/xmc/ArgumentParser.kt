package org.ttlzmc.xmc

object ArgumentParser {

    @Suppress("unused")
    fun parse(args: Array<String>): ParsedRunArgs {
        val arguments = mutableMapOf<String, String>()
        val flags = mutableListOf<String>()

        var i = 0
        while (i < args.size) {
            when {
                args[i].startsWith("--") -> {
                    val key = args[i].substring(2)
                    if (i + 1 < args.size && !args[i + 1].startsWith("--")) {
                        arguments[key] = args[++i]
                    } else {
                        flags.add(key)
                    }
                }
                args[i].startsWith("-") -> {
                    flags.add(args[i].substring(1))
                }
            }
            i++
        }
        return ParsedRunArgs(arguments, flags)
    }

    class ParsedRunArgs(
        private val arguments: MutableMap<String, String>,
        private val flags: MutableList<String>
    ) {
        fun arguments(): Map<String, String> = arguments
        fun flags(): List<String> = flags

        fun getArgument(key: String): String? = arguments[key]
        fun hasFlag(flag: String): Boolean = flags.contains(flag)
    }
}