package com.example.database.ui.theme.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_table")
data class Project(
    @PrimaryKey(autoGenerate = true) val projectId: Int = 0,
    @ColumnInfo(name = "projectName") val projectName: String ="",
    @ColumnInfo(name = "projectStage") val projectStage: String = "",
    @ColumnInfo(name = "projectFavorite") val projectFavorite: Int = 0,
    @ColumnInfo(name = "projectNotes") val projectNotes: String = "",
)
