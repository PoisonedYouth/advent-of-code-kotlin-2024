package day05

import println
import readInput

private data class OrderingRule(
    val first: Int,
    val second: Int,
)

private data class PageNumbers(
    val numbers: List<Int>,
)

fun main() {

    fun getPageOrderingRules(input: List<String>): List<OrderingRule> = input.takeWhile {
        it.isNotBlank()
    }.map {
        OrderingRule(
            first = it.substringBefore("|").toInt(),
            second = it.substringAfter("|").toInt()
        )
    }

    fun getPageNumbersList(input: List<String>): List<PageNumbers> = input.takeLastWhile {
        it.isNotBlank()
    }.map { pageNumbersString ->
        PageNumbers(numbers = pageNumbersString.split(",").map { it.toInt() })
    }

    fun part1(input: List<String>): Int {
        val pageOrderingRules = getPageOrderingRules(input)
        val pageNumbersList = getPageNumbersList(input)

        return pageNumbersList.sumOf { pageNumbers ->
            val isMatching = pageNumbers.numbers.windowed(2).all { (a, b) ->
                pageOrderingRules.any { it.first == a && it.second == b }
            }
            if (isMatching) {
                pageNumbers.numbers[pageNumbers.numbers.size / 2]
            } else {
                0
            }
        }
    }

    fun checkAndSwitchPages(orderedList: MutableList<Int>, i: Int, j: Int, pageOrderingRules: List<OrderingRule>) {
        val a = orderedList[i]
        val b = orderedList[j]
        if (pageOrderingRules.any { it.first == a && it.second == b }) {
            orderedList[i] = b
            orderedList[j] = a
        }
    }

    fun part2(input: List<String>): Int {
        val pageOrderingRules = getPageOrderingRules(input)
        val pageNumbersList = getPageNumbersList(input)

        return pageNumbersList.sumOf { pageNumbers ->
            val isMatching = pageNumbers.numbers.windowed(2).all { (first, second) ->
                pageOrderingRules.any { it.first == first && it.second == second }
            }
            if (!isMatching) {
                val orderedList = pageNumbers.numbers.toMutableList()
                for (i in orderedList.indices) {
                    for (j in i..orderedList.lastIndex) {
                        checkAndSwitchPages(orderedList, i, j, pageOrderingRules)
                    }
                }
                orderedList[orderedList.size / 2]
            } else {
                0
            }
        }
    }

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}
