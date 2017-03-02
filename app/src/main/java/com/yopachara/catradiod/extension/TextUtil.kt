package com.yopachara.catradiod.extension

/**
 * Created by dw03 on 3/2/2017 AD.
 */

object TextUtil {

    fun formatString(text: String): String {

        val json = StringBuilder()
        var indentString = ""

        for (i in 0..text.length - 1) {
            val letter = text[i]
            when (letter) {
                '{', '[' -> {
                    json.append("\n" + indentString + letter + "\n")
                    indentString = indentString + "\t"
                    json.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    json.append("\n" + indentString + letter)
                }
                ',' -> json.append(letter + "\n" + indentString)

                else -> json.append(letter)
            }
        }

        return json.toString()
    }
}
