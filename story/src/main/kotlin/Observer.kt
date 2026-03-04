abstract class Observer(
    location: Location,
    heightCm: Int
) : Human(location, heightCm), IHuman {


    var mood: Mood = Mood.CALM
        protected set

    var pose: Pose = Pose.STANDING
        protected set

    var lastObservedLocation: Location? = null
        protected set

    fun sit(where: Location) {
        moveTo(where)
        pose = Pose.SITTING
    }

    open fun look(target: Location, observedNoiseLevel: Int = 0) {
        lastObservedLocation = target
        look(observedNoiseLevel)
    }

    open fun look(observedNoiseLevel: Int = 0) {
        mood = if (observedNoiseLevel >= 30) Mood.WORRIED else Mood.CALM
    }
}
