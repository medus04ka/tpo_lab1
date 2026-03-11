class Horse(
    override var location: Location,
    val maxCarryWeight: Int,
    val horseType: HorseType
) : Animal(location) {

    private val baggage: MutableList<Cargo> = mutableListOf()

    var currentNoiseLevel: Int = 0
        private set

    fun getBaggageSnapshot(): List<Cargo> = baggage.toList()

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
        if (horseType != HorseType.WILD) {
            throw CargoRuleViolation("Разрешено только диким лошадкам перевозить грузы, тут рабство.")
        }

        if (destination.type != LocationType.UNKNOWN_LANDS) {
            throw CargoRuleViolation("Груз можно доставлять ТОЛЬКО в Неизведанные Области, потому что принеси то НЕ знаю что")
        }

        if (cargo.freshness == Freshness.SPOILED) {
            throw CargoRuleViolation("Испорченный груз нельзя перевозить, иначе зачем его везли???")
        }

        if (!canAdd(cargo)) {
            throw CargoRuleViolation("Лошади хоть и дикие, но все равно, совесть имейте")
        }

        addCargo(cargo)

        currentNoiseLevel = baseNoise() + recalcNoise(cargo.noiseContribution)

        cargo.moveTo(location)
        cargo.moveTo(destination)
    }

    fun baseNoise(): Int {
        return when (horseType) {
            HorseType.WILD -> 5
            HorseType.DOMESTIC -> 2
        }
    }

    fun recalcNoise(actionNoise: Int = 0): Int {
        return baseNoise() + actionNoise + getCurrentLoadWeight() / 10
    }
}