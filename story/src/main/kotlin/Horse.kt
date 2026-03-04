class Horse(
    location: Location,
    val maxCarryWeight: Int,
    val horseType: HorseType = HorseType.DOMESTIC
) : Animal(location) {

    private val baggage: MutableList<Cargo> = mutableListOf()

    var currentNoiseLevel: Int = baseNoise()
        private set

    init {
        require(maxCarryWeight > 0)
        if (!location.isReachableBy(this)) {
            throw LocationRuleViolation("Лошадь типа $horseType не может находиться в '${location.name}' (${location.type})")
        }
        recalcNoise()
    }

    override fun canReach(target: Location): Boolean =
        !target.isAbstract || horseType == HorseType.WILD

    fun getBaggageSnapshot(): List<Cargo> = baggage.toList()

    fun currentLoadWeight(): Int = baggage.sumOf {
        it.weight
    }

    fun canAdd(cargo: Cargo): Boolean = currentLoadWeight() + cargo.weight <= maxCarryWeight

    fun addCargo(cargo: Cargo) {
        if (!canAdd(cargo)) {
            throw CargoRuleViolation("Перегруз: ${currentLoadWeight()} +++++++ ${cargo.weight} > $maxCarryWeight")
        }
        baggage += cargo
        recalcNoise()
    }

    /**
     * Перевезти груз в локацию:
     * 1\ проверяет ограничение по весу
     * 2\ проверяет достижимость локации
     * 3перемещает лошадь
     * 4\выгружает груз
     * 5\обновляет шум (грохот от действия carry)
     */
    fun carry(cargo: Cargo, destination: Location) {
        if (!canAdd(cargo)) {
            throw CargoRuleViolation("Перегруз при carrry: ${currentLoadWeight()} + ${cargo.weight} > $maxCarryWeight")
        }
        if (!destination.isReachableBy(this)) {
            throw LocationRuleViolation("Лошадь типа $horseType не может везти в '${destination.name}' (${destination.type})")
        }

        baggage += cargo
        moveTo(destination)
        baggage.remove(cargo)

        recalcNoise(10 + cargo.noiseContribution)
    }

    private fun baseNoise(): Int = when (horseType) {
        HorseType.WILD -> 15
        HorseType.DOMESTIC -> 8
    }

    private fun recalcNoise(actionNoise: Int = 0) {
        val cargoNoise = baggage.sumOf { it.noiseContribution }
        currentNoiseLevel = baseNoise() + cargoNoise + actionNoise
    }
}