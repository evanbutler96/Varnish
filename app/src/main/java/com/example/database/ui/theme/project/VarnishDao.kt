package com.example.database.ui.theme.project

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface VarnishDao {
    @Query("SELECT * FROM project_table")
    fun getAllProjects(): Flow<List<Project>>

    @Query("SELECT * FROM PROJECT_TABLE WHERE projectId = :projectId")
    fun getProject(projectId: Int): Flow<List<Project>>


    @Query("SELECT * From project_table where projectName = :projectName")
    fun getProjectName(projectName: String?): Flow<List<Project>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: Project)

    // UPDATE
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(project: Project)

    // DELETE
    @Delete
    suspend fun delete(item: Project)

}