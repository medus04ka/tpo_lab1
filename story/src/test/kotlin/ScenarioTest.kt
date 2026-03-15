import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScenarioTest {

    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation

    private lateinit var observer: NarratorObserver
    private lateinit var giantChild: GiantChild
    private lateinit var horse: Horse
    private lateinit var cargo: Cargo

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

        observer = NarratorObserver(pavement,170)
        giantChild = GiantChild(beach,260)
        horse = Horse(sky,200,HorseType.WILD)

        cargo = Cargo("Свежие запасы армированных изгородей", 80, true, 6)
    }

    @Test
    fun story() {
        observer.sit(pavement)
        val jumpNoise = giantChild.jump(beach, JumpType.HEAVY)
        observer.look(jumpNoise)
        horse.carry(cargo, unknownLands)

        assertTrue(observer.isSitting)
        assertTrue(observer.isWorried)
        assertTrue(jumpNoise >= 7)
        assertEquals(unknownLands, cargo.location)
        assertTrue(horse.currentNoiseLevel > 0)
    }
}