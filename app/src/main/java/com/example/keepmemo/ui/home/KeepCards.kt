package com.example.keepmemo.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun KeepCard(
    title: String,
    body: String,
    modifier: Modifier = Modifier
) {
    val titleRef = "title"
    val bodyRef = "body"
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val constraints = decoupledConstraintsKeepCard(
            titleRef = titleRef,
            bodyRef = bodyRef,
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
    bodyRef: String,
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
        KeepCard(
            title = "タイトル",
            body = """
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
                メモですメモですメモですメモですメモですメモですメモですメモですメモです
            """.trimIndent()
        )
    }
}
