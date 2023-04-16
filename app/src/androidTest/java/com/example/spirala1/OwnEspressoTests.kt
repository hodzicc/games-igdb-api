package com.example.spirala1

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertEquals
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    @get:Rule
    var homeRule:ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)
    /*
    Test za provjeru rasporeda elemenata na fragment_game_details
    Prvo se izvršava akcija na prvu igru, te se nakon toga provjerava da li su elementi
    raspoređeni kako treba (description ispod genre, slika ispod naslova, itd...)
     */
     @Test
    fun elementsTest(){
         var prvaIgra = GameData.getAll().get(0)
         onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
             hasDescendant(withText(prvaIgra.title)),
             hasDescendant(withText(prvaIgra.releaseDate)),
             hasDescendant(withText(prvaIgra.rating.toString()))
         ),click()))
         onView(withId(R.id.genret)).check(isCompletelyLeftOf(withId(R.id.genre_textview)))
         onView(withId(R.id.genret)).check(isTopAlignedWith(withChild(withId(R.id.genre_textview))))
         onView(withId(R.id.publishert)).check(isCompletelyLeftOf(withId(R.id.publisher_textview)))
         onView(withId(R.id.publishert)).check(isTopAlignedWith(withChild(withId(R.id.publisher_textview))))
        onView(withId(R.id.cover_imageview)).check(isCompletelyBelow(withId(R.id.item_title_textview)))
         onView(withId(R.id.reviews_list)).check(isCompletelyBelow(withId(R.id.description_textview)))
         onView(withId(R.id.description_textview)).check(isCompletelyBelow(withId(R.id.genre_textview)))
    }
    /*
    Test za testiranje da li je gameDetailsItem u navigation bar-u na početku disabled, i da li
    postaje enabled nakon što je neka igrica otvorena preko recyclerview-a
     */

    @Test
    fun testNavigationToGameDetailsScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the HomeFragment
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.bottom_nav)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that the gameDetailsItem menu item is initially disabled
        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))

        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))

        // Verify that the NavController’s state is changed and the gameDetailsItem menu item is now enabled
        assertEquals(navController.currentDestination?.id, R.id.gameDetailsItem)
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))


    }
    /*

    @Test
    fun testNavigationToGameDetailsInLandscape() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create a graphical FragmentScenario for the HomeFragment
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.bottom_nav)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Click on the first item in the RecyclerView to navigate to the GameDetailsFragment
        onView(withId(R.id.game_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // Rotate the device to landscape mode
        val context = ApplicationProvider.getApplicationContext<Context>()
        val activity = (homeScenario.activity as AppCompatActivity)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Verify that the correct destination is displayed
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.gameDetailsItem)

        // Rotate the device back to portrait mode
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }



     */


}