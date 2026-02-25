class CoordinateLocation (
    override val name: String,
    override val type: LocationType,
    val x: Int,
    val y: Int
) : Location {
    init {
        require(type == LocationType.PAVEMENT || type == LocationType.BEACH)
    }
    override val isAbstract: Boolean = false
}