import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HumanTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val beach = CoordinateLocation("Песок", LocationType.BEACH, 1, 1)
    private val sky = AbstractLocation("Небо", LocationType.SKY)

    @Test
    fun observerSits() {
        val observer = NarratorObserver(pavement, 170)

        observer.sit(pavement)
        observer.look(beach,29)
        assertEquals(Pose.SITTING, observer.pose)
        assertEquals(Mood.CALM, observer.mood)
        assertEquals(beach, observer.lastObservedLocation)

        observer.look(beach,30)
        assertEquals(Mood.WORRIED, observer.mood)
    }

    @Test
    fun humansCannotReach() {
        val observer = NarratorObserver(pavement, 170)
        val kid = GiantChild(beach, 210)

        assertThrows<LocationRuleViolation> {
            observer.moveTo(sky)
        }
        assertThrows<LocationRuleViolation> {
            kid.moveTo(sky)
        }
    }

    @Test
    fun npcJump() {
        val npc = object : NPC(pavement, 180) {}

        val light = npc.jump(beach, JumpType.LIGHT)
        assertEquals(5, light)
        assertEquals(beach, npc.location)

        val heavy = npc.jump(beach, JumpType.HEAVY)
        assertEquals(25, heavy)
        assertEquals(beach, npc.location)
    }

    @Test
    fun heightIsValid() {
        assertThrows<IllegalArgumentException> {
            NarratorObserver(pavement, 29)
        }
        assertDoesNotThrow {
            NarratorObserver(pavement, 30)
        }
        assertDoesNotThrow {
            NarratorObserver(pavement, 300)
        }
        assertThrows<IllegalArgumentException> {
            NarratorObserver(pavement, 301)
        }
    }
}