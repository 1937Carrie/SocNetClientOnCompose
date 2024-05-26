package com.stanislavdumchykov.socialnetworkclient.presentation.ui.util

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.ErrorColor
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Gray
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Orange
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.White

@Composable
fun getTextFieldCustomColors(): TextFieldColors {
    val contentAlphaDisabled = 0.38f
    val contentAlphaSelected = 0.4f
    val contentAlphaUnfocused = 0.6f

    return TextFieldColors(
        focusedTextColor = White,
        unfocusedTextColor = White,
        disabledTextColor = White.copy(contentAlphaDisabled),
        errorTextColor = White,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = White,
        errorCursorColor = White,
        textSelectionColors = TextSelectionColors(
            handleColor = Orange,
            backgroundColor = Orange.copy(contentAlphaSelected)
        ),
        focusedIndicatorColor = Gray,
        unfocusedIndicatorColor = Gray,
        disabledIndicatorColor = Gray,
        errorIndicatorColor = Gray,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(contentAlphaDisabled),
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(contentAlphaDisabled),
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = Gray,
        unfocusedLabelColor = Gray,
        disabledLabelColor = Gray,
        errorLabelColor = Gray,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
            contentAlphaUnfocused
        ),
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(contentAlphaDisabled),
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
            contentAlphaUnfocused
        ),
        disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface.copy(contentAlphaDisabled),
        errorSupportingTextColor = ErrorColor,
        focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
        unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(contentAlphaDisabled),
        errorPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(contentAlphaDisabled),
        errorSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}