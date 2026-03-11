import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ObserverTest {

    private lateinit var observer: NarratorObserver
    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)
        observer = NarratorObserver(pavement, 170)
    }

    @Test
    fun sit() {
        observer.sit(pavement)

        assertEquals(Pose.SITTING, observer.pose)
    }

    @Test
    fun sitChangesLocation() {
        observer.sit(beach)

        assertEquals(beach, observer.location)
    }

    @ParameterizedTest
    @CsvSource("0, CALM", "3, CALM", "6, CALM", "7, WORRIED", "10, WORRIED")
    fun look(noise: Int, expectedMood: Mood) {
        observer.look(beach,noise)

        assertEquals(expectedMood, observer.mood)
    }

    @Test
    fun lookCALM() {
        observer.look(beach,2)
        assertEquals(Mood.CALM, observer.mood)

        observer.look(beach,9)
        assertEquals(Mood.WORRIED, observer.mood)
    }
}