class GiantChild(
    location: Location,
    heightCm: Int
) : NPC(location, heightCm) {

    override fun jump(where: Location, type: JumpType): Int {
        val baseImpact = super.jump(where, type)
        return baseImpact + when (type) {
            JumpType.LIGHT -> 2
            JumpType.HEAVY -> 10
        }
    }
}