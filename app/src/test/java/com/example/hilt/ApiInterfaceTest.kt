package com.example.hilt

import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class ApiInterfaceTest {

    private lateinit var apiInterface: ApiInterface

    @Before
    fun activityRule(): ActivityScenarioRule<MayActivity> {
        return ActivityScenarioRule(MayActivity::class.java)
    }

    @Test
    fun getHeroes_success() {
        val expectedHeroes = listOf(
            Hero(
                id = 1,
                name = "A-Bomb",
                slug = "1-a-bomb",
                powerstats = Powerstats(
                    intelligence = 38,
                    strength = 100,
                    speed = 17,
                    durability = 80,
                    power = 24,
                    combat = 64
                ),
                appearance = Appearance(
                    gender = "Male",
                    race = "Human",
                    height = listOf("6'8", "203 cm"),
                    weight = listOf("980 lb", "441 kg"),
                    eyeColor = "Yellow",
                    hairColor = "No Hair"
                ),
                biography = Biography(
                    fullName = "Richard Milhouse Jones",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Rick Jones"),
                    placeOfBirth = "Scarsdale, Arizona",
                    firstAppearance = "Hulk Vol 2 #2 (April, 2008) (as A-Bomb)",
                    publisher = "Marvel Comics",
                    alignment = "good"
                ),
                work = Work(
                    occupation = "Musician, adventurer, author; formerly talk show host",
                    base = "-"
                ),
                connections = Connections(
                    groupAffiliation = "Hulk Family; Excelsior (sponsor), Avengers (honorary member); formerly partner of the Hulk, Captain America and Captain Marvel; Teen Brigade; ally of Rom",
                    relatives = "Marlo Chandler-Jones (wife); Polly (aunt); Mrs. Chandler (mother-in-law); Keith Chandler, Ray Chandler, three unidentified others (brothers-in-law); unidentified father (deceased); Jackie Shorr (alleged mother; unconfirmed)"
                ),
                images = Images(
                    sm = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/sm/1-a-bomb.jpg"
                )
            )
        )
        `when`(apiInterface.getHeroes()).thenReturn(Single.just(expectedHeroes))
        val testObserver = apiInterface.getHeroes().test()
        testObserver.assertComplete()
        testObserver.assertValue(expectedHeroes)
    }

    @Test
    fun getHeroes_error() {
        val errorMessage = "Error message"
        `when`(apiInterface.getHeroes()).thenReturn(Single.error(Throwable(errorMessage)))
        val testObserver = apiInterface.getHeroes().test()
        testObserver.assertError { it.message == errorMessage }
    }
}
