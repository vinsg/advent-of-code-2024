import kotlin.math.abs

object Day3 {

    fun part1(): Long {
        val fileAsList = readFile("day3").also { println("input: $it") }

        // parse input
        val regex = "(mul)\\(\\d+,\\d+\\)".toRegex()
        val l = fileAsList.map { s ->
            regex.findAll(s).map { num ->
                num.value.substringAfter("mul(").substringBefore(",").toLong() to num.value.substringAfter(",")
                    .substringBefore(")").toLong()
            }.toList()
        }

        println(l)

        // mul
        val sum = l.sumOf { line -> line.sumOf { pair -> pair.first * pair.second } }
        return sum.also { println("result is $it") }
    }

    fun part2(): Long {
        val fileAsList = readFile("day3").also { println("input: $it") }

        // parse input
        val regex = "(mul)\\(\\d+,\\d+\\)|(do\\(\\))|(don't\\(\\))".toRegex()
        val l = fileAsList.map { s ->
            regex.findAll(s).map { matchResult -> matchResult.value }.toList()
        }

        println(l)

        // process list
        var summing = true
        val sum = l.sumOf { line ->
            line.fold(0L) { acc, v ->
                when (v) {
                    "do()" -> {
                        summing = true
                        acc
                    }
                    "don't()" -> {
                        summing = false
                        acc
                    }

                    else -> {
                        if (summing){
                            acc + getNum(v)
                        } else acc
                    }
                }
            }
        }
        return sum.also { println("result is $it") }
    }

    fun getNum(s: String): Long {
        return (s.substringAfter("mul(").substringBefore(",").toLong() to s.substringAfter(",").substringBefore(")")
            .toLong()).let { pair ->
            pair.first * pair.second
        }
    }
}