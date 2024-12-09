package day09

import println
import readInput


fun main() {

    fun createInput(input: List<String>): MutableList<Pair<String, Long>> {
        val numbers = input[0].chunked(1).mapIndexed { index, s -> s.toLong() to index }

        var numberIndex = 0L
        val resultList = mutableListOf<Pair<String, Long>>()
        numbers.forEach { (number, index) ->
            if (index % 2 == 0) {
                repeat(number.toInt()) {
                    resultList.add(numberIndex.toString() to index.toLong())
                }
                numberIndex++
            } else {
                repeat(number.toInt()) {
                    resultList.add("." to index.toLong())
                }
            }
        }
        return resultList
    }

    fun part1(input: List<String>): Long {
        val resultList = createInput(input)

        for (i in 0..resultList.lastIndex) {
            val pair = resultList[i]
            if (pair.first == ".") {
                val lastNotFreeBlockIndex = resultList.indexOfLast { it.first != "." }
                if (lastNotFreeBlockIndex < i) {
                    continue
                }
                val lastNotFreeBlock = resultList[lastNotFreeBlockIndex]
                resultList[i] = lastNotFreeBlock
                resultList[lastNotFreeBlockIndex] = pair
            }

        }

        return resultList.mapIndexed { index, pair ->
            if (pair.first == ".") {
                0L
            } else {
                index * pair.first.toLong()
            }
        }.sum()
    }


    fun part2(input: List<String>): Long {
        val resultList = createInput(input)

        val highestFileId = resultList.filter { it.first != "." }.maxBy { it.second }.second
        outer@ for (i in highestFileId downTo 0) {
            val allFileBlocks = resultList.withIndex().filter { it.value.second == i && it.value.first != "." }
            if (allFileBlocks.isEmpty()) {
                continue@outer
            }

            val filteredList = resultList.withIndex().filter {it.index in 0..allFileBlocks.first().index}
            inner@for (j in 0..filteredList.lastIndex) {
                if(filteredList[j].value.first != ".") continue@inner

                val freeBlocksSegment = filteredList.drop(j).takeWhile { it.value.first == "." }
                val freeBlocksLength = freeBlocksSegment.size

                if (allFileBlocks.size in 1..freeBlocksLength) {
                    for (k in 0..allFileBlocks.lastIndex) {
                        resultList[freeBlocksSegment[k].index] = allFileBlocks[k].value
                        resultList[allFileBlocks[k].index] = freeBlocksSegment[k].value
                    }
                    continue@outer
                }
            }
        }

        return resultList.mapIndexed { index, pair ->
            if (pair.first == ".") {
                0L
            } else {
                index * pair.first.toLong()
            }
        }.sum()
    }

    val input = readInput("day09/Day09")
    part1(input).println()
    part2(input).println() //6362722604045
}
