package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val first = input.map{ it.substringBefore("   ").toInt()}.sorted()
        val second = input.map{ it.substringAfter("   ").toInt()}.sorted()
        return first.mapIndexed { index: Int, value: Int ->
            abs(value - second[index])
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val first = input.map{ it.substringBefore("   ").toInt()}.sorted()
        val second = input.map{ it.substringAfter("   ").toInt()}.sorted()
        val occurrences = second.groupBy { it }.mapValues{ it.value.size }
        return first.sumOf { value: Int ->
            (occurrences[value] ?: 0) * value
        }
    }

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
