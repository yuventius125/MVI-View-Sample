package com.yuventius.qr_generator

import com.yuventius.qr_generator.core.decrypt
import com.yuventius.qr_generator.core.encrypt
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EncryptionTest {
    @Test
    fun makeIntToQRString() {
        val count = 10
        val encryptedString = count.toString().encrypt()
        println("[makeIntToQRString] encryptedString: $encryptedString")

        val decryption = encryptedString.decrypt().toInt()
        println("[makeIntToQRString] decryption: $decryption")

        assertEquals(count, decryption)
    }

    @Test
    fun checkDecryption() {
        val encryptStr = "05z3AU+yxbWxf4zwis1zTw=="
        val decryption = encryptStr.decrypt().toInt()
        println("[makeIntToQRString] decryption: $decryption")

        assertEquals(10, decryption)
    }
}