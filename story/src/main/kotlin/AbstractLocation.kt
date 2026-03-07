class AbstractLocation(
    override val name: String,
    override val type: LocationType
) : Location {
    override val isAbstract: Boolean = true
    override fun isReachableBy(creature: Creature): Boolean {
        return type != LocationType.SKY
    }
}