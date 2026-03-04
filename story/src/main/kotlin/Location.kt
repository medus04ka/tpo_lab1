interface Location {
    val name: String
    val type: LocationType
    val isAbstract: Boolean

    fun isReachableBy(creature: Creature): Boolean =
        creature.canReach(this)
}