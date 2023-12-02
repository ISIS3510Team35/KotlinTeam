package com.edu.uniandes.fud

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FuDApplication : Application(), ImageLoaderFactory {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { DatabaseRoom.getDataBase(this, applicationScope) }
    val repository by lazy { DBRepository(database) }

    companion object {
        private var timeStart: Long = 0
        private var reportSent: Boolean = false
        const val MY_CHANNEL_ID = "MyChannel"
        fun getTimeStart(): Long {
            return timeStart;
        }

        fun setTimeStart(millis: Long) {
            timeStart = millis
        }

        fun reportSent() {
            reportSent = true
        }

        fun getReportStatus() : Boolean{
            return reportSent
        }
    }
    
    
    
    override fun onCreate() {
        setTimeStart(System.currentTimeMillis())
        super.onCreate()
        
        val alarmNotification = AlarmNotification()
        alarmNotification.createChannel(this)
        alarmNotification.scheduleNotification(this)
    }



    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .weakReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }

}