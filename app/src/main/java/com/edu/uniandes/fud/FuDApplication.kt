package com.edu.uniandes.fud

import android.app.Application
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        private val _connected = MutableLiveData<Boolean>()
        val connected: LiveData<Boolean> = _connected

        private var idUser = -1
        private var timeStart: Long = 0
        private var reportSent: Boolean = false
        const val MY_CHANNEL_ID = "MyChannel"

        fun setConnectivity(boolean: Boolean){
            Log.d("NETWORK_FUD_VAL", boolean.toString())
            _connected.postValue(boolean)
        }


        fun getTimeStart(): Long {
            return timeStart;
        }

        fun setIdUser(idUser:Int){
            this.idUser = idUser
        }



        fun getIdUser(): Int{
            return idUser
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


    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }


        return false
    }
    
    
    
    override fun onCreate() {
        setTimeStart(System.currentTimeMillis())
        super.onCreate()
        
        val alarmNotification = AlarmNotification()
        alarmNotification.createChannel(this)
        alarmNotification.scheduleNotification(this)
        val TAG = "NETWORK_FUD"

        setConnectivity(isNetworkAvailable())

        val connectivityManager: ConnectivityManager = getSystemService(ConnectivityManager::class.java)



        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {
                Log.e(TAG, "The default network is now: " + network)
                setConnectivity(true)
            }

            override fun onLost(network : Network) {
                Log.e(TAG, "The application no longer has a default network. The last default network was " + network)
                setConnectivity(false)
            }

            override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                Log.e(TAG, "The default network changed capabilities: " + networkCapabilities)
            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                Log.e(TAG, "The default network changed link properties: " + linkProperties)
            }
        })
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