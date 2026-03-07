class GiantChild(
    location: Location,
    heightCm: Int
) : NPC(location, heightCm) {

    override fun jump(where: Location, type: JumpType): Int {
        if (where.type != LocationType.BEACH) {
            throw LocationRuleViolation("Огромные дети могут тяжело или легко прыгать только по песку")
        }
        moveTo(where)
        return when (type) {
            JumpType.LIGHT -> 3
            JumpType.HEAVY -> 9
        }
    }
}