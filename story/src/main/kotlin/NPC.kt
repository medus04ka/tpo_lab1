abstract class NPC(
    override var location: Location,
    override val heightCm: Int
) : Human(location, heightCm) {

    abstract fun jump(where: Location, type: JumpType): Int
}