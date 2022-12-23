package com.example.keepmemo.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.width
import com.example.keepmemo.core.model.data.Keep
import com.example.keepmemo.core.model.data.Memo
import com.example.keepmemo.core.model.data.UiMessage
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun top_appbar_isShown() {
        val topAppbarTitle: String
        composeTestRule.activity.apply {
            topAppbarTitle = getString(R.string.home_memo_appbar_title)
        }
        composeTestRule.setContent {
            HomeScreenTestable()
        }

        composeTestRule
            .onNodeWithText(topAppbarTitle)
            .assertExists()
    }

    @Test
    fun keep_titleAndBody_isShown() {
        val memo = testMemoList.first()
        composeTestRule.setContent {
            HomeScreenTestable(memoList = listOf(memo))
        }

        composeTestRule
            .onNodeWithText(memo.keep.title)
            .assertExists()
        composeTestRule
            .onNodeWithText(memo.keep.body)
            .assertExists()
    }

    @Test
    fun listPane_one_image_GridView_isShown() {
        val memo = testMemoList.first()
        composeTestRule.setContent {
            HomeScreenTestable(memoList = listOf(memo))
        }

        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.GridView.name
        ).assertExists()
        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.Splitscreen.name
        ).assertDoesNotExist()
    }

    @Test
    fun listPane_two_image_Splitscreen_isShown() {
        val memo = testMemoList.first()
        composeTestRule.setContent {
            HomeScreenTestable(
                memoList = listOf(memo),
                listPane = HomeListPane.Two
            )
        }

        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.GridView.name
        ).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.Splitscreen.name
        ).assertExists()
    }

    @Test
    fun change_list_page_one_to_two_icon_image_changed() {
        val memo = testMemoList.first()

        val homeListPageButtonContentDescription =
            composeTestRule.activity.getString(R.string.modifier_semantics_home_list_pane_button)

        composeTestRule.setContent {
            HomeScreenTestable(memoList = listOf(memo))
        }

        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.GridView.name
        ).assertExists()
        composeTestRule.onNodeWithContentDescription(
            homeListPageButtonContentDescription
        ).performClick()
        composeTestRule.onNodeWithContentDescription(
            Icons.Filled.Splitscreen.name
        ).assertExists()
    }

    @Test
    fun change_list_page_one_to_two_list_columns_changed() {
        val memo = testMemoList.first()

        var screenWidth: Dp = Dp.Unspecified
        val homeListPageButtonContentDescription =
            composeTestRule.activity.getString(R.string.modifier_semantics_home_list_pane_button)

        composeTestRule.setContent {
            val density = LocalDensity.current
            screenWidth = with(density) {
                composeTestRule.activity.window.decorView.width.toDp()
            }
            HomeScreenTestable(memoList = testMemoList)
        }

        composeTestRule
            .onNodeWithText(memo.keep.title)
            .assertWidthIsAtLeast(screenWidth / 2)

        composeTestRule.onNodeWithContentDescription(
            homeListPageButtonContentDescription
        ).performClick()

        composeTestRule
            .onNodeWithText(memo.keep.title)
            .getUnclippedBoundsInRoot().let {
                Truth.assertThat(it.width).isAtMost(screenWidth / 2)
            }
    }

    @Test
    fun click_keep_card_call_navigateToEditKeep() {

        var functionCalled = false
        val keepCardContentDescription =
            composeTestRule.activity.getString(R.string.modifier_semantics_home_list_keep_card)

        composeTestRule.setContent {
            HomeScreenTestable(
                memoList = testMemoList,
                navigateToEditKeep = {
                    functionCalled = true
                }
            )
        }
        composeTestRule.onAllNodesWithContentDescription(
            keepCardContentDescription
        ).onFirst().performClick()

        Truth.assertThat(functionCalled).isTrue()
    }

    @Test
    fun click_add_keep_button_call_navigateToAddKeep() {

        var functionCalled = false
        val addKeepButtonContentDescription =
            composeTestRule.activity.getString(R.string.modifier_semantics_add_keep_button)

        composeTestRule.setContent {
            HomeScreenTestable(
                memoList = testMemoList,
                navigateToAddKeep = {
                    functionCalled = true
                }
            )
        }
        composeTestRule.onNodeWithContentDescription(
            addKeepButtonContentDescription
        ).performClick()

        Truth.assertThat(functionCalled).isTrue()
    }
}

@Composable
private fun HomeScreenTestable(
    memoList: List<Memo> = emptyList(),
    listPane: HomeListPane = HomeListPane.One,
    navigateToAddKeep: () -> Unit = {},
    navigateToEditKeep: (Long) -> Unit = {},
) {
    var homePane by remember {
        mutableStateOf(listPane)
    }
    HomeScreen(
        uiMessages = emptyList<UiMessage>(),
        listPane = homePane,
        memoList = memoList,
        selectedMemoIdList = emptySet(),
        openDrawer = {},
        onMessageDismiss = {},
        navigateToAddKeep = navigateToAddKeep,
        navigateToEditKeep = navigateToEditKeep,
        listPaneChange = {
            homePane = it
        },
        addToSelectedIdList = {},
        removeFromSelectedIdList = {},
        keepListLazyGridState = rememberLazyGridState(),
        snackbarHostState = remember { SnackbarHostState() }
    )
}

private val testMemoList = listOf(
    Memo.EMPTY.copy(
        keep = Keep(
            id = 1L,
            title = "title1",
            body = "body1"
        )
    ),
    Memo.EMPTY.copy(
        keep = Keep(
            id = 2L,
            title = "title2",
            body = "body2"
        )
    ),
    Memo.EMPTY.copy(
        keep = Keep(
            id = 3L,
            title = "title3",
            body = "body3"
        )
    )
)
