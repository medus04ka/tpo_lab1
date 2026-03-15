abstract class Observer(
    override var location: Location,
    override val heightCm: Int
) : Human(location, heightCm) {

    var isSitting: Boolean = false

    var isWorried: Boolean = false

    fun sit(where: Location) {
        moveTo(where)
        isSitting = true
    }

    fun look(observedNoiseLevel: Int = 0) {
        isWorried = observedNoiseLevel >= 7
    }
}