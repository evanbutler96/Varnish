package com.example.database.ui.theme

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.database.*
import com.example.database.ui.theme.project.Project
import com.example.database.ui.theme.project.ProjectsRepository
import com.example.database.ui.theme.project.VarnishDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VarnishViewModel(private val varnishDao: VarnishDao,
                       savedStateHandle: SavedStateHandle,
                        private val projectsRepository: ProjectsRepository
            ):

    ViewModel() {

        var projectUiState = ProjectUiState()
            private set



        fun getAllProjects(): Flow<List<Project>> = varnishDao.getAllProjects()

        fun getProject(projectId: Int): Flow<List<Project>> = varnishDao.getProject(projectId)

        fun getProjectName(projectName: String?): Flow<List<Project>> = varnishDao.getProjectName(projectName)

        fun updateUiState(newProjectUiState: ProjectUiState){
            projectUiState = newProjectUiState.copy()
        }

            fun updateProject(project: Project) {
               // varnishDao.update(projectUiState.toProject())
                viewModelScope.launch { projectsRepository.updateProject(project) }
            }

            fun addProject(project: Project){
               viewModelScope.launch{ projectsRepository.insertProject(project)}
            }

        fun delete(project: Project){
            viewModelScope.launch { projectsRepository.deleteProject(project) }
        }


    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VarnishApplication)
                VarnishViewModel(application.database.varnishDao(), this.createSavedStateHandle(),
                    application.container.projectsRepository)
            }
        }
    }
    }