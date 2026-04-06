class GiantChild(
    override var location: Location,
    override val heightCm: Int
) : Human(location, heightCm) {

    fun jump(where: Location, isLight: Boolean): Int {
        if (where.type != LocationType.BEACH) {
            throw LocationRuleViolation("Огромные дети могут тяжело или легко прыгать только по песку")
        }

        if (location is CoordinateLocation && where is CoordinateLocation) {
            val distance = (location as CoordinateLocation).distanceTo(where)

            val maxJumpDistance = if (isLight) 5.0 else 10.0

            if (distance > maxJumpDistance) {
                throw LocationRuleViolation("Слишком ужасно для ребенка так прыгать???? лол")
            }
        }

        moveTo(where)
        return if (isLight) 3 else 9
    }
}