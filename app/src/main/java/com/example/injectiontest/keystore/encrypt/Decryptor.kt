package com.example.injectiontest.keystore.encrypt

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.nio.charset.Charset
import java.security.KeyPair
import java.security.KeyStore
import java.security.KeyStore.SecretKeyEntry
import java.util.Enumeration
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Named

class Decryptor @Inject constructor(
    @Named("alias") val alias: String
) {

    fun aliases(): List<String> {
        return KeyStore.getInstance("AndroidKeyStore").aliases().toList()
    }


    fun decrypt(encryptedText: ByteArray, encryptionIV: ByteArray): String {

        val keystore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val aliases = keystore.aliases()


        val secretKeyEntry : SecretKeyEntry = keystore.getEntry(alias, null) as SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey

        val cipher: Cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, encryptionIV)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decodedData = cipher.doFinal(encryptedText)

        return String(decodedData, Charset.forName("UTF-8"))
    }

}