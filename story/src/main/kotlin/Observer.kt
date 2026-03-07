abstract class Observer(
    override var location: Location,
    override val heightCm: Int
) : Human(location, heightCm) {

    var mood: Mood = Mood.CALM
        protected set

    var pose: Pose = Pose.STANDING
        protected set

    fun sit(where: Location) {
        moveTo(where)
        pose = Pose.SITTING
    }

    fun look(target: Location, observedNoiseLevel: Int = 0) {
        mood = if (observedNoiseLevel >= 7) Mood.WORRIED else Mood.CALM
    }
}
