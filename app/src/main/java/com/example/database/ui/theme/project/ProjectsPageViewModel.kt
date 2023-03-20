package com.example.database.ui.theme.project

/*
class ProjectsPageViewModel (
    savedStateHandle: SavedStateHandle,
    private val projectsRepository: ProjectsRepository
        ) : ViewModel() {
            private val projectId = Int

        val uiState: StateFlow<ProjectState> =
            projectsRepository.getProjectStream(projectId)
                .filterNotNull()
                .map {
                    it.toProjectState(actionEnabled = it.projectStage)
                }.stateIn (
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = ProjectState()
                        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
*/
