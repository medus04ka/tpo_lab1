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
}