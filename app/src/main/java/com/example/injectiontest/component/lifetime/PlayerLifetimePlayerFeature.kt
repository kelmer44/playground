package com.example.injectiontest.component.lifetime

import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.annotation.PlayerManaged
import com.example.injectiontest.component.feature.PlayerFeature
import javax.inject.Inject

class PlayerLifetimePlayerFeature @Inject constructor(
    @PlayerManaged viewModelStoreOwner: ViewModelStoreOwner,
    lifetime: PlayerLifetimeImpl
) : PlayerFeature {

    init {
        lifetime.setup(viewModelStoreOwner)
    }
}
