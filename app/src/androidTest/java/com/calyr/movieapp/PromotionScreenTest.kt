package com.calyr.movieapp


import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calyr.movieapp.screen.PromotionScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PromotionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun promotionScreen_dispalyCorrectTitle() {
        val testTitle = "Promo"
        composeTestRule.setContent {
            PromotionScreen(title = testTitle)
        }
        composeTestRule
            .onNodeWithTag("PROMOTION_TEST_TAG")
            .assertTextEquals(testTitle)
    }

}

