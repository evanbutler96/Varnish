package com.example.database

import com.example.database.ui.theme.project.Project

data class ProjectUiState(
    val projectId: Int = 0,
    val projectName: String = "",
    val projectStage: String = "",
    val projectFavorite: Int = 0
)

fun ProjectUiState.toProject(): Project = Project(
    projectId = projectId,
    projectName = projectName,
    projectStage = projectStage,
    projectFavorite = projectFavorite
)

fun Project.toProjectUiState(actionEnabled: Boolean = false): ProjectUiState = ProjectUiState(
    projectId = projectId,
    projectName = projectName,
    projectStage = projectStage,
    projectFavorite = projectFavorite
)

