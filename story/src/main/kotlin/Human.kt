abstract class Human(
    location: Location,
    final override val heightCm: Int
) : Creature(location), IHuman {
    init {
        require(heightCm in 30..300)
    }
}