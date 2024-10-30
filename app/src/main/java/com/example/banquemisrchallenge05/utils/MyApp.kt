package com.example.banquemisrchallenge05.utils

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.example.banquemisrchallenge05.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), ImageLoaderFactory {

    //caching for coil
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizeBytes(1024 * 1024 * 400) // 400megabytes cache
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .build()
    }
}