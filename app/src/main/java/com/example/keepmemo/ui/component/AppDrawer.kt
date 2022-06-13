package com.example.keepmemo.ui.component

import android.content.res.Configuration
import android.text.TextUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepmemo.KeepMemoNavigation
import com.example.keepmemo.R
import com.example.keepmemo.ui.theme.KeepMemoTheme

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToLicense: () -> Unit,
    navigateToAlarm: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        KeepMemoLogo(closeDrawer = closeDrawer)
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = Icons.Filled.Home,
            label = stringResource(id = R.string.title_home),
            isSelected = TextUtils.equals(currentRoute, KeepMemoNavigation.Home.route),
            action = {
                navigateToHome()
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Filled.Home,
            label = stringResource(id = R.string.title_license),
            isSelected = TextUtils.equals(currentRoute, KeepMemoNavigation.OpenLicense.route),
            action = {
                navigateToLicense()
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Filled.Alarm,
            label = stringResource(id = R.string.title_alarm),
            isSelected = TextUtils.equals(currentRoute, KeepMemoNavigation.Alarm.route),
            action = {
                navigateToAlarm()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun KeepMemoLogo(
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = closeDrawer) {
            Icon(
                imageVector = Icons.Filled.NoteAlt,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    Surface(
        modifier = modifier
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {

        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null, // decorative
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    KeepMemoTheme {
        Surface {
            AppDrawer(
                currentRoute = KeepMemoNavigation.Home.route,
                navigateToHome = {},
                navigateToLicense = {},
                navigateToAlarm = {},
                closeDrawer = { }
            )
        }
    }
}
