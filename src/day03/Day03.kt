package day03

import println
import readInput

fun main() {
    val regex1 = """
        mul\([0-9]{1,3},[0-9]{1,3}\)
    """.trimIndent().toRegex()
    val regex2 = """
        (mul\([0-9]{1,3},[0-9]{1,3}\)|(do\(\))|(don't\(\)))
    """.trimIndent().toRegex()

    fun MatchResult.extractNumbers(): Pair<Int, Int> = this.value.substringAfter("mul(").substringBefore(")").split(",").map { it.toInt() }.let {
        it[0] to it[1]
    }

    fun Pair<Int, Int>.multiply(): Int = this.first * this.second

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            regex1.findAll(line).sumOf { it.extractNumbers().multiply() }
        }
    }


    fun part2(input: List<String>): Int {
        var isEnabled = true
        return input.sumOf { line ->
            regex2.findAll(line).mapNotNull {
                when {
                    it.value == "don't()" -> {
                        isEnabled = false
                        null
                    }

                    it.value == "do()" -> {
                        isEnabled = true
                        null
                    }

                    it.value.contains("mul") && isEnabled -> {
                        it.extractNumbers().multiply()
                    }

                    else -> null
                }
            }.sum()

        }
    }

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
