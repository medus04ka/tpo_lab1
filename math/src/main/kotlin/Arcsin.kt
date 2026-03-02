class Arcsin {

    private val PI = 3.1415926535897932384626433832795

    fun approximate(x: Double, iterations: Int): Double {
        require(iterations >= 1)

        val ax = abs(x)
        if (ax > 1.0) return Double.NaN
        if (x == -0.0 || x.isNaN()) return x
        if (x == 1.0) return PI / 2.0
        if (x == -1.0) return -PI / 2.0

        if (ax >= 0.9) {
            val v = (1.0 - ax) * (1.0 + ax)
            val t = sqrt(v)
            val r = approximate(t, iterations)
            return if (x > 0) PI / 2.0 - r else -PI / 2.0 + r
        }

        var res = 0.0
        var term = x
        val x2 = x * x

        for (n in 0 until iterations) {
            res += term
            val a = (2 * n + 1).toDouble()
            val b = (2 * n + 2).toDouble()
            val c = (2 * n + 3).toDouble()
            term *= x2 * (a * a) / (b * c)
        }

        return res
    }

    private fun abs(v: Double): Double = if (v < 0.0) -v else v

    private fun sqrt(v: Double): Double {
        if (v.isNaN()) return Double.NaN
        if (v < 0.0) return Double.NaN
        if (v == 0.0) return 0.0
        var g = if (v >= 1.0) v else 1.0
        repeat(50) { g = 0.5 * (g + v / g) }
        return g
    }
}