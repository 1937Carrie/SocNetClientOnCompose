package com.stanislavdumchykov.socialnetworkclient.presentation.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.stanislavdumchykov.socialnetworkclient.R

enum class Fonts(val fontFamily: FontFamily) {
    FONT_OPEN_SANS(FontFamily(Font(R.font.opensans_regular))),
    FONT_OPEN_SANS_SEMI_BOLD(FontFamily(Font(R.font.opensans_semi_bold)))
}