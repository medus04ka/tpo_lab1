import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NPCTest {

    private lateinit var npc: TestNpc
    private lateinit var beach: CoordinateLocation

    private class TestNpc(
        override var location: Location,
        override val heightCm: Int
    ) : NPC(location, heightCm) {
        override fun jump(where: Location, type: JumpType): Int {
            moveTo(where)
            return if (type == JumpType.HEAVY) 10 else 1
        }
    }

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        npc = TestNpc(beach, 220)
    }

    @Test
    fun constructor_SetsLocation() {
        assertEquals(beach, npc.location)
    }

    @Test
    fun constructor_SetsHeight() {
        assertEquals(220, npc.heightCm)
    }

    @Test
    fun jump_ReturnsNoise() {
        val noise = npc.jump(beach, JumpType.HEAVY)

        assertEquals(10, noise)
    }
}