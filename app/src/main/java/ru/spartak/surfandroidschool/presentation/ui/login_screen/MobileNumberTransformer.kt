package ru.spartak.surfandroidschool.presentation.ui.login_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText


class MobileNumberTransformer {
    private val phoneNumberOffsetTranslator = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset == 0) return 0
            if (offset <= 2) return offset + 3
            if (offset <= 5) return offset + 4
            if (offset <= 7) return offset + 5
            if (offset <= 10) return offset + 6
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 3) return 0
            if (offset <= 5) return offset - 3
            if (offset <= 9) return offset - 4
            if (offset <= 12) return offset - 5
            if (offset <= 16) return offset - 6
            return 10
        }
    }

    fun transformNumber(number: AnnotatedString): TransformedText
    {
        val trimmed =
            if (number.text.length >= 10) number.text.substring(0..9) else number.text
        val annotatedString = AnnotatedString.Builder().run {
            for (i in trimmed.indices) {
                if (i == 0) append("+7 ")
                append(trimmed[i])
                if (i == 2 || i == 5 || i == 7) {
                    append(" ")
                }
            }
            pushStyle(SpanStyle(color = Color.LightGray))
            toAnnotatedString()
        }
        return TransformedText(annotatedString, phoneNumberOffsetTranslator)
    }

}