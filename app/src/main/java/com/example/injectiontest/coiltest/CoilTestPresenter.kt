/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.injectiontest.coiltest

import android.util.Log
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.executeBlocking
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.injectiontest.databinding.FragmentCoilBinding
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoilApi::class)
class CoilTestPresenter @Inject constructor(
    private val fragment: Fragment,
    private val imageLoader: ImageLoader,
    private val diskCache: DiskCache
) {
    private val binding = FragmentCoilBinding.bind(fragment.requireView())

    private val url = "https://fastly.picsum.photos/id/652/536/354.jpg?hmac=zWDXs5Y94rKgs39sJnp6k2PJZLCmeeUEXW3V9O1yvZQ"
    init {
        Timber.d("Singleton Loader: ${fragment.requireContext().imageLoader} Injected loader: $imageLoader")

        binding.downloadInjected.setOnClickListener {

            val request = ImageRequest.Builder(fragment.requireContext())
                .data(url)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()

            diskCache.remove("")
            imageLoader.enqueue(request).job.invokeOnCompletion {
                Timber.w("Image stored in cache!")
            }
        }

        binding.downloadSingleton.setOnClickListener {
            loadImage(fragment.requireContext().imageLoader)
        }

        binding.clearCache.setOnClickListener {
            diskCache.remove(url)
        }

        binding.clearSingleton.setOnClickListener {
            fragment.requireContext().imageLoader.memoryCache?.clear()
//            fragment.requireContext().imageLoader.diskCache?.clear()
        }
    }

    private fun loadImage(imageLoader: ImageLoader) {

        binding.image.load(
            url,
            imageLoader = imageLoader
        )
    }
}