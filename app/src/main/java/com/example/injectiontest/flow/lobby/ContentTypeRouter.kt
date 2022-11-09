package com.example.injectiontest.flow.lobby

import androidx.fragment.app.Fragment
import com.example.injectiontest.hexCode
import timber.log.Timber

class ContentTypeRouter(val name: String) {
    fun doStuff() {
        Timber.i("ONJECT - ContentTyperRouter [${this.hexCode()}] printing [$name]")
    }

    companion object {
        @JvmStatic
        fun findRouter(fragment: Fragment): ContentTypeRouter? {
            return ContentTypeRouter(fragment.toString())
        }

        @JvmStatic
        fun requireRouter(fragment: Fragment) = requireNotNull(findRouter(fragment))
    }
}
