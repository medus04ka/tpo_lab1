import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HumanTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val beach = CoordinateLocation("Песок", LocationType.BEACH, 1, 1)
    private val sky = AbstractLocation("Небо", LocationType.SKY)

    @Test
    fun hum30() {
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

    @Test
    fun sit() {
        val o = NarratorObserver(pavement, 170)

        assertEquals(Pose.STANDING, o.pose)
        assertEquals(pavement, o.location)

        o.sit(pavement)
        assertEquals(Pose.SITTING, o.pose)
        assertEquals(pavement, o.location)
    }

    @Test
    fun lookingAt() {
        val o = NarratorObserver(pavement, 170)

        o.look(beach, observedNoiseLevel = 0)
        assertEquals(Mood.CALM, o.mood)

        o.look(beach, observedNoiseLevel = 29)
        assertEquals(Mood.CALM, o.mood)

        o.look(beach, observedNoiseLevel = 30)
        assertEquals(Mood.WORRIED, o.mood)
    }

    @Test
    fun notAbs() {
        val o = NarratorObserver(pavement, 170)
        assertThrows<LocationRuleViolation> { o.moveTo(sky) }

        val kid = GiantChild(beach, 210)
        assertThrows<LocationRuleViolation> { kid.moveTo(sky) }
    }

    @Test
    fun jump() {
        val npc = object : NPC(pavement, 180) {}

        val light = npc.jump(beach, JumpType.LIGHT)
        assertEquals(5, light)
        assertEquals(beach, npc.location)

        val heavy = npc.jump(beach, JumpType.HEAVY)
        assertEquals(25, heavy)
        assertEquals(beach, npc.location)
    }
}