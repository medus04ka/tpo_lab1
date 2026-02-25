class AbstractLocation(
    override val name: String,
    override val type: LocationType
) : Location {
    init {
        require(type == LocationType.SKY || type == LocationType.UNKNOWN_LANDS)
    }
    override val isAbstract: Boolean = true
}