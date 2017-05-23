package net.ilkinulas.adventofcode

import java.nio.charset.Charset
import java.security.MessageDigest

var HEX_CHARS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

fun String.md5(): String {
    val digester = MessageDigest.getInstance("MD5")
    digester.update(this.toByteArray(Charset.defaultCharset()))
    return digester.digest().toHexString()
}

fun Byte.toHexString(): String {
    val i = this.toInt()
    val char2 = HEX_CHARS[i and 0x0f]
    val char1 = HEX_CHARS[i shr 4 and 0x0f]
    return "$char1$char2"
}

fun ByteArray.toHexString(): String {
    val builder = StringBuilder()
    for (b in this) {
        builder.append(b.toHexString())
    }
    return builder.toString()
}


fun main(args: Array<String>) {

    val hashSequence = generateSequence(0) { it + 1 }
            .map { "abbhdwsy$it".md5() }
            .filter { it.substring(0, 5) == "00000" }

    var password1 = hashSequence
            .take(8)
            .map { it[5] }
            .joinToString("")
    println(password1)

    var password2 = charArrayOf('*', '*', '*', '*', '*', '*', '*', '*')
    hashSequence
            .map { it[5] to it[6] }
            .takeWhile {
                if (it.first.isDigit()) {
                    val position = Integer.parseInt(it.first.toString())
                    if (position < 8 && password2[position] == '*') {
                        password2[position] = it.second
                    }
                }
                password2.contains('*')
            }.toList()
    println(password2.joinToString(""))
}