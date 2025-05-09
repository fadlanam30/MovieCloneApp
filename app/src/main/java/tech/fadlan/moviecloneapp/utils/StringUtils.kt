package tech.fadlan.moviecloneapp.utils

import android.text.Html
import android.text.Spanned

fun String.ellipsize(maxLength: Int = 20): String {
    return if (this.length > maxLength) this.take(maxLength) + "..." else this
}

fun String.formatHtml(): Spanned {
    val formatted = this
        .replace(Regex("""\*\*(.*?)\*\*"""), "<b>$1</b>") // Markdown-style bold
        .replace("\r\n", "<br>")
        .replace("\n", "<br>")
        .replace("\t", "&emsp;")

    return Html.fromHtml(formatted, Html.FROM_HTML_MODE_LEGACY)
}