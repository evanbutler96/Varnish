package com.example.database.ui.theme.project

import kotlinx.coroutines.flow.Flow



class OfflineProjectsRepository(private val varnishDao: VarnishDao) : ProjectsRepository {
    override fun getAllProjectsStream(): Flow<List<Project>> = varnishDao.getAllProjects()

    override fun getProjectStream(projectName: String): Flow<List<Project?>> = varnishDao.getProjectName(projectName)

    //override suspend fun insertProject(projectName: String, projectStage: String, projectFavorite: Int) = varnishDao.insertProject(projectName, projectStage, projectFavorite)

    override suspend fun insertProject(item: Project) = varnishDao.insertProject(item)

    override suspend fun deleteProject(item: Project) = varnishDao.delete(item)

    override suspend fun updateProject(item: Project) = varnishDao.update(item)
}
