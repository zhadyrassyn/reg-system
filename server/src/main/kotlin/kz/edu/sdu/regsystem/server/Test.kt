package kz.edu.sdu.regsystem.server

import kz.edu.sdu.regsystem.server.utils.Utils
import java.io.File
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.experimental.and

class Test

fun main(args: Array<String>) {
//    val bytes = "password".toByteArray()
//
//    val md5 = MessageDigest.getInstance("MD5")
//    md5.update(bytes)
//    val data = md5.digest()
//
//    val sb = StringBuffer()
//    for (i in 0 until data.size) {
//        sb.append(Integer.toString((data[i] and 0xff.toByte()) + 0x100, 16).substring(1))
//    }
//    print(sb.toString())


    println(Utils.encrypt("123123123"))
}
