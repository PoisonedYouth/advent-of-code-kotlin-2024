package day11

import println
import readInput

fun main() {

    fun MutableMap<Long, Long>.updateCount(key: Long, count: Long){
        val add = if(count == 0L) 1L else count
        put(key, getOrDefault(key, 0) + add)
    }

    fun calculateStones(stones: List<Long>, n: Int): Long {
        var currentStones = stones.associateWith { 0L }
        repeat(n) {
            currentStones = buildMap {
                for((number, count) in currentStones){
                    if (number == 0L) {
                            updateCount(1, count)
                    } else {
                        val string = number.toString()
                        if (string.length % 2 == 0) {
                            val key1 = string.take(string.length / 2).toLong()
                            updateCount(key1, count)
                            val key2 = string.drop(string.length / 2).toLong()
                           updateCount(key2, count)
                        } else {
                            val key = number * 2024
                            updateCount(key, count)
                        }
                    }
                }
            }
        }
        return currentStones.map { it.value }.sum()
    }

    fun part1(input: List<String>): Long {
        val stones = buildList {
            for (word in input.joinToString(" ").split(" ")) {
                add(word.toLong())
            }
        }
        return calculateStones(stones, 25)

    }

    fun part2(input: List<String>): Long {
        val stones = buildList {
            for (word in input.joinToString(" ").split(" ")) {
                add(word.toLong())
            }
        }
        return calculateStones(stones, 75)
    }

    val input = readInput("day11/Day11")
    part1(input).println()
    part2(input).println()
}
