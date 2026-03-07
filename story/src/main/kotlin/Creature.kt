abstract class Creature(
    open var location: Location
) {
    open fun moveTo(target: Location) {
        if (!target.isReachableBy(this)) {
            throw IllegalStateException("Существо не может добраться до локации ${target.name}")
        }
        location = target
    }
}