package day02

import println
import readInput

fun main() {

    fun List<Int>.withoutElementAt(listIndex: Int): List<Int> {
        return this.let { it.toMutableList().also { it.removeAt(listIndex) }.toList() }
    }

    fun List<Int>.isMatching() = this.windowed(2).all {
        it[1] - it[0] in (1..3)
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val report = line.split(" ").map { it.toInt() }
            val strictlyIncreasing = report.isMatching()
            val strictlyDecreasing = report.reversed().isMatching()
            strictlyIncreasing || strictlyDecreasing
        }
    }


    fun part2(input: List<String>): Int {
        return input.count { line ->
            val report = line.split(" ").map { it.toInt() }
            val strictlyIncreasingFaultTolerant = List(report.size) { listIndex ->
                report.withoutElementAt(listIndex).isMatching()
            }.any { it }
            val strictlyDecreasingFaultTolerant = List(report.size) { listIndex ->
              report.withoutElementAt(listIndex).reversed().isMatching()
            }.any { it }
            strictlyIncreasingFaultTolerant || strictlyDecreasingFaultTolerant
        }
    }

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
