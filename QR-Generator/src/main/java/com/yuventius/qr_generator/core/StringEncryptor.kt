package com.yuventius.qr_generator.core

import com.yuventius.qr_generator.QRLib
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun String.encrypt(): String {
    val key = SecretKeySpec(QRLib.SECRET_KEY.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16))
    cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
    return Base64.encode(cipher.doFinal(this.toByteArray()))
}

@OptIn(ExperimentalEncodingApi::class)
fun String.decrypt(): String {
    val key = SecretKeySpec(QRLib.SECRET_KEY.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16))
    cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
    val decodedByte = Base64.decode(this)
    return String(cipher.doFinal(decodedByte))
}