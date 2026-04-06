class Horse(
    override var location: Location,
    val maxCarryWeight: Int,
    val isWild : Boolean
) : Creature(location) {

    override fun maxDistance(): Double = 52.0

    private val baggage: MutableList<Cargo> = mutableListOf()

    var currentNoiseLevel: Int = 0
        private set

    fun getBaggageList(): List<Cargo> = baggage.toList()

    fun getCurrentLoadWeight(): Int = baggage.sumOf {
        it.weight
    }

    fun canAdd(cargo: Cargo): Boolean {
        return getCurrentLoadWeight() + cargo.weight <= maxCarryWeight
    }

    fun addCargo(cargo: Cargo) {
        if (!canAdd(cargo)) {
            throw CargoRuleViolation("Груз тяжкий для лошади")
        }
        baggage.add(cargo)
    }

    fun carry(cargo: Cargo, destination: Location) {
        if (!isWild) {
            throw CargoRuleViolation("Разрешено только диким лошадкам перевозить грузы, тут рабство.")
        }

        if (destination.type != LocationType.UNKNOWN_LANDS) {
            throw CargoRuleViolation("Груз можно доставлять ТОЛЬКО в Неизведанные Области, потому что принеси то НЕ знаю что")
        }

        if (!cargo.isFresh) {
            throw CargoRuleViolation("Испорченный груз нельзя перевозить, иначе зачем его везли???")
        }

        if (!canAdd(cargo)) {
            throw CargoRuleViolation("Лошади хоть и дикие, но все равно, совесть имейте")
        }

        addCargo(cargo)

        currentNoiseLevel = recalcNoise(cargo.noiseContribution)

        cargo.moveTo(location)
        cargo.moveTo(destination)
    }

    fun baseNoise(): Int {
        return if (isWild) 5 else 2
}

    fun recalcNoise(actionNoise: Int = 0): Int {
        return baseNoise() + actionNoise + getCurrentLoadWeight() / 10
    }
}