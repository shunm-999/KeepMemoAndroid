package com.example.keepmemo.ui.screens.alarm

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.keepmemo.ui.theme.KeepMemoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmCard(
    hour: Int,
    minute: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onCheckedChange(isChecked.not())
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (timeText, switch) = createRefs()

                CompositionLocalProvider(
                    LocalContentAlpha provides if (isChecked) {
                        LocalContentAlpha.current
                    } else {
                        ContentAlpha.disabled
                    }
                ) {
                    Text(
                        text = "$hour:" + "%02d".format(minute),
                        style = MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                            .constrainAs(timeText) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }

                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier
                        .constrainAs(switch) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}

@Preview("KeepCard")
@Preview("KeepCard (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AlarmCardPreview() {
    KeepMemoTheme {
        Column {
            AlarmCard(
                hour = 6,
                minute = 0,
                isChecked = false,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlarmCard(
                hour = 13,
                minute = 40,
                isChecked = true,
                onCheckedChange = {}
            )
        }
    }
}
