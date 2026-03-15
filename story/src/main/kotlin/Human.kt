abstract class Human(
    override var location: Location,
    open val heightCm: Int
) : Creature(location) {
    override fun maxDistance(): Double = 67.0
}