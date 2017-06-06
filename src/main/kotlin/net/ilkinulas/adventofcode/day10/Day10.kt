package net.ilkinulas.adventofcode.day10

import java.io.File

val valueGoesToRegex = """value (\d+) goes to bot (\d+)""".toRegex()
val botGivesRegex = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()

enum class DestinationType {OUTPUT, BOT }

data class Destination(val id: Int, val type: DestinationType)

data class Bot(val botId: Int, var firstVal: Int = 0, var secondVal: Int = 0,
               var lowDest: Destination? = null, var highDest: Destination? = null, var processed: Boolean = false) {

    fun setMicrochip(microchip: Int) {
        if (firstVal == 0) {
            firstVal = microchip
        } else {
            secondVal = microchip
        }
    }

    fun isFull() = firstVal != 0 && secondVal != 0

    fun low() = Math.min(firstVal, secondVal)
    fun high() = Math.max(firstVal, secondVal)
}

val bots = mutableMapOf<Int, Bot>()
val outputs = mutableMapOf<Int, Int>()

fun main(args: Array<String>) {
    val result = processInput(File("src/main/resources/input10.txt").readLines(), bots)
    println(result)
}


fun processInput(input: List<String>, bots: MutableMap<Int, Bot>) {
    input.forEach {
        processLine(it, bots)
    }

    val bot = bots.filterValues { (it.firstVal == 61 && it.secondVal == 17) || (it.firstVal == 17 && it.secondVal == 61) }.toList().first().second
    println(bot.botId)

    val result = outputs[0]!! * outputs[1]!! * outputs[2]!!
    println(result)
}

fun processLine(line: String, bots: MutableMap<Int, Bot>) {
    if (line.startsWith("value")) {
        val matchResult = valueGoesToRegex.find(line)!!
        val values = matchResult.groupValues
        val value = values[1].toInt()
        val botId = values[2].toInt()
        var bot = bots[botId]
        if (bot == null) {
            bot = Bot(botId)
            bots[botId] = bot
        }
        bot.setMicrochip(value)
        if (bot.isFull()) {
            processBot(bot, bots)
        }
    } else {
        val matchResult = botGivesRegex.find(line)!!
        val values = matchResult.groupValues
        val botId = values[1].toInt()
        val lowDest = if (values[2] == "bot") DestinationType.BOT else DestinationType.OUTPUT
        val lowDestId = values[3].toInt()
        val highDest = if (values[4] == "bot") DestinationType.BOT else DestinationType.OUTPUT
        val highDestId = values[5].toInt()
        var bot = bots[botId]
        if (bot == null) {
            bot = Bot(botId)
            bots[botId] = bot
        }
        bot.lowDest = Destination(lowDestId, lowDest)
        bot.highDest = Destination(highDestId, highDest)
        if (bot.isFull()) {
            processBot(bot, bots)
        }
    }
}

private fun processBot(bot: Bot, bots: MutableMap<Int, Bot>) {
    if (bot.processed) {
        return
    }
    if (bot.lowDest == null || bot.highDest == null) {
        return
    }
    bot.processed = true
    val lowDest = bot.lowDest!!
    val highDest = bot.highDest!!
    var lowBot = bots[lowDest.id]
    if (lowBot == null) {
        lowBot = Bot(lowDest.id)
        bots[lowDest.id] = lowBot
    }
    if (lowDest.type == DestinationType.BOT) {
        lowBot.setMicrochip(bot.low())
        if (lowBot.isFull()) {
            processBot(lowBot, bots)
        }
    } else {
        outputs[lowDest.id] = bot.low()
    }
    var highBot = bots[highDest.id]
    if (highBot == null) {
        highBot = Bot(highDest.id)
        bots[highDest.id] = highBot
    }
    if (highDest.type == DestinationType.BOT) {
        highBot.setMicrochip(bot.high())
        if (highBot.isFull()) {
            processBot(highBot, bots)
        }
    } else {
        outputs[highDest.id] = bot.high()
    }
}