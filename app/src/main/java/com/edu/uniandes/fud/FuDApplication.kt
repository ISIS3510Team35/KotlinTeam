package com.edu.uniandes.fud

import android.app.Application
import com.edu.uniandes.fud.database.DatabaseRoom
import com.edu.uniandes.fud.repository.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FuDApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { DatabaseRoom.getDataBase(this, applicationScope) }
    val repository by lazy { DBRepository(database) }



}