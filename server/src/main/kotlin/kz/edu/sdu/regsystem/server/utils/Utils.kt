package kz.edu.sdu.regsystem.server.utils

import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

object Utils {
    fun encrypt(password: String) : String{
        val bytes = password.toByteArray()

        val md5 = MessageDigest.getInstance("MD5")
        md5.update(bytes)
        val data = md5.digest()

        val sb = StringBuffer()
        for (i in 0 until data.size) {
            sb.append(Integer.toString((data[i] and 0xff.toByte()) + 0x100, 16).substring(1))
        }

        return sb.toString()
    }

    fun getNextDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        return calendar.time
    }
}