package com.example.keepmemo.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.example.keepmemo.core.designsystem.component.NavigationIcon
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme
import com.example.keepmemo.feature.home.navigation.HomeDestination
import com.example.keepmemo.feature.openlicense.navigation.OpenLicenseDestination
import com.example.keepmemo.navigation.DrawerDestination

@Composable
fun KeepMemoAppDrawer(
    currentRoute: String?,
    destinationList: List<DrawerDestination>,
    navigateToDestination: (DrawerDestination) -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        KeepMemoLogo(closeDrawer = closeDrawer)
        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = .2f))
        destinationList.forEach { destination ->
            DrawerButton(
                icon = destination.icon,
                label = stringResource(id = destination.labelId),
                isSelected = TextUtils.equals(currentRoute, destination.route),
                action = {
                    navigateToDestination(destination)
                    closeDrawer()
                }
            )
        }
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
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleSmall
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
    val colors = MaterialTheme.colorScheme
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
                    style = MaterialTheme.typography.bodyMedium,
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
            KeepMemoAppDrawer(
                currentRoute = HomeDestination.route,
                destinationList = listOf(
                    DrawerDestination(
                        route = HomeDestination.route,
                        destination = HomeDestination.destination,
                        icon = Icons.Filled.Home,
                        labelId = R.string.title_home
                    ),
                    DrawerDestination(
                        route = OpenLicenseDestination.route,
                        destination = OpenLicenseDestination.destination,
                        icon = Icons.Filled.Description,
                        labelId = R.string.title_license
                    )
                ),
                navigateToDestination = { },
                closeDrawer = { }
            )
        }
    }
}
