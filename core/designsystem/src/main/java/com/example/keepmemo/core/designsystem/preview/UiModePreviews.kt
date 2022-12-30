package com.example.keepmemo.core.designsystem.preview

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Mode",
    group = "ui modes",
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Night Mode",
    group = "ui modes",
    uiMode = UI_MODE_NIGHT_YES
)
annotation class UiModePreviews
