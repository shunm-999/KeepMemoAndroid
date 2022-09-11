package com.example.keepmemo.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.keepmemo.ui.theme.KeepMemoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeepCard(
    title: String,
    body: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val titleRef = "title"
    val bodyRef = "body"
    Card(
        border = if (isSelected) {
            BorderStroke(
                3.dp,
                MaterialTheme.colors.primary
            )
        } else {
            BorderStroke(
                1.dp,
                MaterialTheme.colors.onSurface
            )
        },
        shape = RoundedCornerShape(8.dp),
        elevation = if (isSelected) 8.dp else 1.dp,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        val constraints = decoupledConstraintsKeepCard(
            titleRef = titleRef,
            bodyRef = bodyRef
        )
        ConstraintLayout(
            constraintSet = constraints,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .layoutId(titleRef)
                    .fillMaxWidth()
            )
            Text(
                text = body,
                modifier = Modifier
                    .layoutId(bodyRef)
                    .fillMaxWidth()
            )
        }
    }
}

private fun decoupledConstraintsKeepCard(
    titleRef: String,
    bodyRef: String
): ConstraintSet {
    return ConstraintSet {
        val title = createRefFor(titleRef)
        val body = createRefFor(bodyRef)

        constrain(title) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(body) {
            top.linkTo(title.bottom, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }
}

@Preview("KeepCard")
@Preview("KeepCard (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun KeepCardPreview() {
    KeepMemoTheme {
        val title = "タイトル"
        val body = """
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
        """.trimIndent()
        Column {
            KeepCard(
                title = title,
                body = body,
                onClick = {},
                onLongClick = {},
                isSelected = false
            )
            Spacer(modifier = Modifier.height(8.dp))
            KeepCard(
                title = title,
                body = body,
                onClick = {},
                onLongClick = {},
                isSelected = true
            )
        }
    }
}
