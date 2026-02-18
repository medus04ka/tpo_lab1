//arcsin(x) через ряд тейлорсвифт
class Arcsin {
    fun approximateArcsine(x: Double, iterations: Int): Double {
        require(iterations >= 1);
        require(x >= -1.0 && x <= 1.0);

        var res = 0.0;
        var term = x; // t0
        val x2 = x * x;

        for (n in 0 until iterations) {
            res += term;

            // t_{n+1} = t_n * x^2 * (2n+1)^2 / ((2n+2)(2n+3))
            val a = (2 * n + 1).toDouble();
            val b = (2 * n + 2).toDouble();
            val c = (2 * n + 3).toDouble();
            term *= x2 * (a * a) / (b * c);
        }

        return res;
    }
}
