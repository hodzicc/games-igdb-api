package com.example.spirala1

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
//import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
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
import org.mockito.Mockito.*

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
    postaje enabled nakon što je neka igrica otvorena preko recyclerview-a i da li se ta igrica prikazuje
    u gameDetails fragmentu
     */

    @Test
    fun testNavigationToGameDetailsScreen() {

        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))

        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))

        onView(withContentDescription(R.string.GameDetails)).check(matches(isDisplayed()))

        onView(withId(R.id.item_title_textview))
            .check(matches(withText(prvaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(prvaIgra.releaseDate)))

        onView(withId(R.id.bottomNavigation)).perform(click());

        onView(withContentDescription(R.string.Home)).check(matches(isDisplayed()))

        onView(withId(R.id.gameDetailsItem)).check(matches((isEnabled())))

        onView(withId(R.id.gameDetailsItem)).perform(click())

        onView(withContentDescription(R.string.GameDetails)).check(matches(isDisplayed()))

        onView(withId(R.id.item_title_textview))
            .check(matches(withText(prvaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(prvaIgra.releaseDate)))

    }

    /*
    Sljedeci test provjerava da li aplikacija kada se pokrene u landscape modu prikazuje homefragment i
    prvu igricu iz liste u gamedetails fragmentu. Nakon toga se izvrsava klik na drugu igricu u recyclerView,
    te se provjerava da li je sada ta igrica prikazana na gameDetails fragmentu. Aplikacija se vraca u portrait mode
    i provjerava se da li je ponovo prikazana zadnja igrica koja je odabrana u landscape.
    Nakon toga vracamo se na home, biramo trecu igricu, prelazimo u landscape mode i provjeravamo
    da li je ta ista igrica prikazana i u landscape.
     */

    @Test
    fun testNavigationToGameDetailsInLandscape() {

        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.home_container)).check(matches(isDisplayed()))

        var prvaIgra = GameData.getAll().get(0)

        onView(withId(R.id.details_container)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(prvaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(prvaIgra.releaseDate)))

        var drugaIgra = GameData.getAll().get(1)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(drugaIgra.title)),
            hasDescendant(withText(drugaIgra.releaseDate)),
            hasDescendant(withText(drugaIgra.rating.toString()))
        ),click()))

        onView(withId(R.id.item_title_textview))
            .check(matches(withText(drugaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(drugaIgra.releaseDate)))

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withContentDescription(R.string.GameDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(drugaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(drugaIgra.releaseDate)))

        onView(withId(R.id.homeItem)).perform(click())
        var trecaIgra = GameData.getAll().get(2)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(trecaIgra.title)),
            hasDescendant(withText(trecaIgra.releaseDate)),
            hasDescendant(withText(trecaIgra.rating.toString()))
        ),click()))

        onView(withContentDescription(R.string.GameDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(trecaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(trecaIgra.releaseDate)))

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.details_container)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(trecaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(trecaIgra.releaseDate)))
    }

    /*
    Sljedeci test provjerava da li nakon sto samo odemo u landscape mode i vratimo se na portrait
    gameDetails dugme na navigacijskoj traci ostaje disabled.
     */

    @Test
    fun portraitToLandscape(){

        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))

        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.home_container)).check(matches(isDisplayed()))

        var prvaIgra = GameData.getAll().get(0)

        onView(withId(R.id.details_container)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(prvaIgra.title)))
        onView(withId(R.id.release_date_textview))
            .check(matches(withText(prvaIgra.releaseDate)))

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))



    }

}