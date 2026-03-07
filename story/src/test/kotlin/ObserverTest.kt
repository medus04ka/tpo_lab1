import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
    fun sit_ChangesPoseToSitting() {
        observer.sit(pavement)

        assertEquals(Pose.SITTING, observer.pose)
    }

    @Test
    fun sit_ChangesLocation() {
        observer.sit(beach)

        assertEquals(beach, observer.location)
    }

    @Test
    fun look_WithLowNoise_SetsCalmMood() {
        observer.look(beach, 3)

        assertEquals(Mood.CALM, observer.mood)
    }

    @Test
    fun look_WithHighNoise_SetsWorriedMood() {
        observer.look(beach, 9)

        assertEquals(Mood.WORRIED, observer.mood)
    }

    @Test
    fun look_WithThresholdNoise_SetsWorriedMood() {
        observer.look(beach, 7)

        assertEquals(Mood.WORRIED, observer.mood)
    }

    @Test
    fun look_MultipleCalls_ChangesMood() {
        observer.look(beach, 2)
        assertEquals(Mood.CALM, observer.mood)

        observer.look(beach, 10)
        assertEquals(Mood.WORRIED, observer.mood)
    }
}