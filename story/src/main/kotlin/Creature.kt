abstract class Creature(
    open var location: Location
) {
    open fun moveTo(target: Location) {
        if (!target.isReachableBy(this)) {
            throw LocationRuleViolation("${this::class.simpleName} не может попасть в локацию '${target.name}' (${target.type})")
        }
        location = target
    }
}