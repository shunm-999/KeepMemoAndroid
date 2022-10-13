package com.example.keepmemo.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

enum class DialogType {
    APPLICATION_PRIVACY_POLICY,
    STORAGE_PERMISSIONS
}

@Composable
fun CustomAlertDialog(
    dialogType: DialogType,
    onDialogDismiss: (Boolean) -> Unit
) {
    when (dialogType) {
        DialogType.APPLICATION_PRIVACY_POLICY -> {
            BaseAlertDialog(
                titleRes = R.string.dialog_title_application_privacy_policy,
                textRes = R.string.dialog_message_application_privacy_policy,
                confirmButtonRes = R.string.dialog_button_yes,
                dismissButtonRes = R.string.dialog_button_close,
                dismissEnabled = false,
                onDialogDismiss = onDialogDismiss
            )
        }
        DialogType.STORAGE_PERMISSIONS -> {
            BaseAlertDialog(
                titleRes = R.string.dialog_title_storage_permission,
                textRes = R.string.dialog_message_storage_permission,
                confirmButtonRes = R.string.dialog_button_yes,
                dismissEnabled = false,
                onDialogDismiss = onDialogDismiss
            )
        }
    }
}

@Composable
private fun BaseAlertDialog(
    @StringRes titleRes: Int,
    @StringRes textRes: Int,
    @StringRes confirmButtonRes: Int,
    @StringRes dismissButtonRes: Int? = null,
    dismissEnabled: Boolean,
    onDialogDismiss: (Boolean) -> Unit
) {
    AlertDialog(
        modifier = Modifier.padding(20.dp),
        onDismissRequest = {
            if (dismissEnabled) {
                onDialogDismiss(false)
            }
        },
        title = {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Text(
                text = stringResource(id = textRes),
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            Text(
                text = stringResource(id = confirmButtonRes),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(15.dp)
                    .clickable { onDialogDismiss(true) }
            )
        },
        dismissButton = if (dismissButtonRes == null) {
            null
        } else {
            {
                Text(
                    text = stringResource(id = dismissButtonRes),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable { onDialogDismiss(false) }
                )
            }
        }
    )
}
