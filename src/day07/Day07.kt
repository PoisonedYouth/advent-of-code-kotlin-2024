package day07

import println
import readInput

data class Equation(
    val testResult: Long,
    val testNumbers: List<Long>
)

fun main() {

    fun concatenate(a: Long, b: Long): Long {
        return "$a$b".toLong()
    }

    fun calculateResult(acc: Long, numbers: List<Long>, ops: List<(Long, Long) -> Long>): List<Long> {
        if (numbers.isEmpty()) {
            return listOf(acc)
        }
        return ops.map {
            if (acc == 0L) {
                calculateResult(it(numbers[0], numbers[1]), numbers.drop(2), ops)
            } else {
                calculateResult(it(acc, numbers[0]), numbers.drop(1), ops)
            }
        }.flatten()
    }

    fun parse(input: List<String>) = input.map { line ->
        val result = line.substringBefore(":").toLong()
        val numbers = line.substringAfter(": ").split(" ").map(String::toLong)
        Equation(result, numbers)
    }

    fun part1(input: List<String>): Long {
        val equations = parse(input)

        return equations
            .filter { equation ->
                calculateResult(0, equation.testNumbers, listOf(Long::plus, Long::times)).any { it == equation.testResult }
            }.sumOf { it.testResult }
    }

    fun part2(input: List<String>): Long {
        val equations = parse(input)

        return equations
            .filter { equation ->
                calculateResult(0, equation.testNumbers, listOf(Long::plus, Long::times, ::concatenate)).any { it == equation.testResult }
            }.sumOf { it.testResult }
    }

    val input = readInput("day07/Day07")
    part1(input).println()
    part2(input).println()
}
