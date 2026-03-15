import kotlin.math.pow
import kotlin.math.sqrt

class CoordinateLocation (
    override val name: String,
    override val type: LocationType,
    val x: Int,
    val y: Int
) : Location {
    override val isAbstract: Boolean = false

    override fun isReachableBy(creature: Creature): Boolean {
        return when (type) {
            LocationType.SKY -> false
            LocationType.PAVEMENT -> true
            LocationType.BEACH -> true
            LocationType.UNKNOWN_LANDS -> true
        }
    }

    fun distanceTo(other: CoordinateLocation): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        return sqrt(dx.pow(2) + dy.pow(2))
    }
}