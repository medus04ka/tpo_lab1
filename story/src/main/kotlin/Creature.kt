abstract class Creature(
    open var location: Location
) {
    open fun maxDistance() = Double.MAX_VALUE
    open fun moveTo(target: Location) {
        if (!target.isReachableBy(this)) {
            throw IllegalStateException("Существо не может добраться до локации ${target.name}")
        }

        if (location is CoordinateLocation && target is CoordinateLocation) {
            val distance = (location as CoordinateLocation).distanceTo(target)

            if (distance > maxDistance()) {
                throw IllegalStateException("Слишком далеко до локации ${location.name}")
            }
        }
        location = target
    }
}