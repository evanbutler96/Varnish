package com.example.database.ui.theme.project

import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllProjectsStream(): Flow<List<Project>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getProjectStream(name: String): Flow<List<Project?>>

    /**
     * Insert item in the data source
     */
    //suspend fun insertProject(projectName: String, projectStage: String, projectFavorite: Int)
    suspend fun insertProject(item: Project)
    /**
     * Delete item from the data source
     */
    suspend fun deleteProject(item: Project)

    /**
     * Update item in the data source
     */
    suspend fun updateProject(item: Project)
}