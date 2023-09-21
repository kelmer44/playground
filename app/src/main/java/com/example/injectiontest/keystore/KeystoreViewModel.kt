package com.example.injectiontest.keystore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.injectiontest.archselector.AutoDisposeViewModel
import com.example.injectiontest.keystore.encrypt.Decryptor
import com.example.injectiontest.keystore.encrypt.Encryptor
import javax.inject.Inject

class KeystoreViewModel @Inject constructor(
    private val encryptor: Encryptor, private val decryptor: Decryptor
) : AutoDisposeViewModel() {
    @RequiresApi(Build.VERSION_CODES.M)
    fun encrypt(text: String): Pair<ByteArray, ByteArray> {
        return encryptor.encrypt(text)
    }

    fun decrypt(encryptionPair : Pair<ByteArray, ByteArray>): String {
        return decryptor.decrypt(encryptionPair.second, encryptionPair.first)
    }
}