package com.example.database

import android.app.Application
import android.content.Context
import com.example.database.ui.theme.project.OfflineProjectsRepository
import com.example.database.ui.theme.project.ProjectsRepository


interface AppContainer {
    val projectsRepository: ProjectsRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val projectsRepository: ProjectsRepository by lazy {
        OfflineProjectsRepository(VarnishDatabase.getDatabase(context).varnishDao())
    }
}

class VarnishApplication: Application() {
    lateinit var container: AppContainer
    val database: VarnishDatabase by lazy { VarnishDatabase.getDatabase(this) }
    override fun onCreate(){
        super.onCreate()
        container=AppDataContainer(this)
    }
}