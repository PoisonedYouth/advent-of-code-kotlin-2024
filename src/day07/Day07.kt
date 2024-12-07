package day07

import println
import readInput
import kotlin.time.measureTimedValue

data class Equation(
    val testResult: Long,
    val testNumbers: List<Long>
)

fun main() {

    fun concatenate(a: Long, b: Long): Long {
        return "$a$b".toLong()
    }

    fun calculateResult(testNumber: Long, acc: Long, numbers: List<Long>, ops: List<(Long, Long) -> Long>): Boolean {
        if (numbers.isEmpty()) {
            return testNumber == acc
        }
        if (acc > testNumber) {
            return false
        }
        return ops.map {
            if (acc == 0L) {
                calculateResult(testNumber, it(numbers[0], numbers[1]), numbers.drop(2), ops)
            } else {
                calculateResult(testNumber, it(acc, numbers[0]), numbers.drop(1), ops)
            }
        }.any{it}
    }

    fun parse(input: List<String>) = input.map { line ->
        val result = line.substringBefore(":").toLong()
        val numbers = line.substringAfter(": ").split(" ").map(String::toLong)
        Equation(result, numbers)
    }

    fun part1(input: List<String>): Long {
        val equations = parse(input)

        val (result, duration)= measureTimedValue {
            equations
                .filter { equation ->
                    calculateResult(equation.testResult, 0, equation.testNumbers, listOf(Long::plus, Long::times))
                }.sumOf { it.testResult }
        }
        println(duration)
        return result
    }

    fun part2(input: List<String>): Long {
        val equations = parse(input)

        val (result, duration)= measureTimedValue {
            equations
                .filter { equation ->
                    calculateResult(equation.testResult, 0, equation.testNumbers, listOf(Long::plus, Long::times, ::concatenate))
                }.sumOf { it.testResult }
        }
        println(duration)
        return result
    }

    val input = readInput("day07/Day07")
    part1(input).println()
    part2(input).println()
}
