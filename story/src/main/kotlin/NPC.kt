abstract class NPC(
    location: Location,
    heightCm: Int
) : Human(location, heightCm) {

    open fun jump(where: Location, type: JumpType): Int {
        moveTo(where)
        return when (type) {
            JumpType.LIGHT -> 5
            JumpType.HEAVY -> 25
        }
    }
}