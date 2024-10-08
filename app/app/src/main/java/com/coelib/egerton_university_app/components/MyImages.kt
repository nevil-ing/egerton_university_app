package com.coelib.egerton_university_app.components

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache

class MyApp : Application(), ImageLoaderFactory {
    @OptIn(ExperimentalCoilApi::class)
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true) // Optional: Adds a crossfade animation
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache")) // Define the cache directory
                    .maxSizePercent(0.02) // 2% of the available disk space for cache
                    .build()
            }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // 25% of app memory for image caching
                    .build()
            }
            .build()
    }
}
