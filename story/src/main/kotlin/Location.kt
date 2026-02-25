interface Location {
    val name: String
    val type: LocationType
    val isAbstract: Boolean

    fun isReachableBy(creature: Creature): Boolean =
        !isAbstract || (creature is Horse && creature.horseType == HorseType.WILD)
}