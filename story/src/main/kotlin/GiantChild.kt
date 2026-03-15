class GiantChild(
    location: Location,
    heightCm: Int
) : NPC(location, heightCm) {

    override fun jump(where: Location, type: JumpType): Int {
        if (where.type != LocationType.BEACH) {
            throw LocationRuleViolation("Огромные дети могут тяжело или легко прыгать только по песку")
        }

        if (location is CoordinateLocation && where is CoordinateLocation) {
            val distance = (location as CoordinateLocation).distanceTo(where)

            val maxJumpDistance = when (type) {
                JumpType.LIGHT -> 5.0
                JumpType.HEAVY -> 10.0
            }

            if (distance > maxJumpDistance) {
                throw LocationRuleViolation("Слишком ужасно для ребенка так прыгать???? лол")
            }
        }

        moveTo(where)
        return when (type) {
            JumpType.LIGHT -> 3
            JumpType.HEAVY -> 9
        }
    }
}