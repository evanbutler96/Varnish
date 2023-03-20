package com.example.database

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.database.ui.theme.VarnishViewModel
import com.example.database.ui.theme.project.Project
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.compose.material.Card

enum class VarnishScreens(){
    MainPage,
    ProjectEdit,
    ProjectNewPage,
    ProjectViewPage
}

interface NavigationDestination {
    /**
     * Unique name to define the path for a composable
     */
    val route: String

    /**
     * String resource id to that contains title to be displayed for the screen.
     */
    val titleRes: Int
}

@Composable
fun VarnishMainApp(
    modifier: Modifier = Modifier,
    viewModel: VarnishViewModel = viewModel(factory = VarnishViewModel.factory)
){
    val title = stringResource(R.string.home)
    val navController = rememberNavController()
    val allProjects by viewModel.getAllProjects().collectAsState(emptyList())
    val mainPage = VarnishScreens.MainPage.name
    
    Scaffold(
        topBar = {
            TopOfPage()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(VarnishScreens.ProjectNewPage.name) }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.project)
                )

            }
        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = mainPage,
            modifier = Modifier.padding(innerPadding),

        ) {
            composable(mainPage){
                MainPage(
                    projectList = allProjects,
                    onProjectClick = {projectName ->
                        navController.navigate(
                            "${VarnishScreens.ProjectViewPage.name}/${projectName}"
                        )
                    }
                )
            }

            val proj = "projectName"
            composable(
                route = VarnishScreens.ProjectViewPage.name + "/{$proj}",
                arguments = listOf(navArgument(proj) {})
            ){backStackEntry ->
                val projectName = backStackEntry.arguments?.getString(proj)
                val project by viewModel.getProjectName(projectName).collectAsState(emptyList())
                ProjectViewPage(
                    project = project,
                    editProject = {projectName ->
                        navController.navigate(
                            "${VarnishScreens.ProjectEdit.name}/${projectName}")
                    }
                )
            }

            val project = "projectName"
            composable(
                route = VarnishScreens.ProjectEdit.name + "/{$project}",
                arguments = listOf(navArgument(project) {})
            ) { backStackEntry ->
                val projectName = backStackEntry.arguments?.getString(project)
                val project by viewModel.getProjectName(projectName).collectAsState(emptyList())
                ProjectEditPage(
                    project = project,
                    updateProject = {
                        viewModel.updateProject(it)
                        navController.navigate(mainPage)
                    },
                    deleteProject = {
                        viewModel.delete(it)
                        navController.navigate(mainPage)
                    },
                    newProject = false
                )
            }

            composable(
                VarnishScreens.ProjectNewPage.name
            ) {
                val newProject = Project()
                // viewModel.addProject(newProject)
                val project = listOf<Project>(newProject)

                ProjectEditPage(
                    project = project,
                    updateProject = {
                        viewModel.addProject(it)
                        navController.navigate(mainPage)
                    },
                    deleteProject = {
                        viewModel.delete(it)
                        navController.navigate(mainPage)
                    },
                    newProject = true
                )
            }

    }
        
    }
}


@Composable
fun MainPage(
    projectList: List<Project>,
    modifier: Modifier = Modifier,
    onProjectClick: (String) -> Unit,       // may need to change String to int for projectId
) {
    HomeScreen(
        projectList = projectList,
        modifier = modifier,
        onProjectClick = onProjectClick)
    
}

@Composable
fun TopOfPage() {
    
}

@Composable
fun HomeScreen(
    projectList: List<Project>,
    modifier: Modifier = Modifier,
    onProjectClick: (String) -> Unit,
    ) {

    LazyColumn(modifier = modifier.padding(vertical = 4.dp)
    ) {
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.ProjectPage),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(300)
                    )
                )
                Text(text = stringResource(R.string.stage),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(300)
                    )
                )
            }
            Divider()
        }

        items(items = projectList) {projectList ->

        ProjectList(
            project = projectList,
            onProjectClick = onProjectClick
        )
        }
    }
}

@Composable
fun ProjectList(
    project: Project,
    onProjectClick: (String) -> Unit,
    ){
Card(
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onProjectClick(project.projectName)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = project.projectName,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(300)
                ))
                Text(text = project.projectStage,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 25.sp,
                        fontWeight = FontWeight(300))
                )
            }
        }
}



/*
* Project View page
*/
@Composable
fun ProjectViewPage(
    modifier: Modifier = Modifier,
    project: List<Project>,
    editProject: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = project,
            key = {project -> project.projectId}
        ) {singleProject->

            // PROJECT NAME
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(text = singleProject.projectName,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 50.sp,
                        fontWeight = FontWeight(300)))

            }

            // PROJECT STAGE
            Row(  modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text(text = stringResource(R.string.stage) + " " + singleProject.projectStage,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(300)))
            }

            // PROJECT STAGE
            Row(  modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text(text = stringResource(R.string.notes),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(300)))
            }

            // PROJECT STAGE
            Row(  modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text(text = singleProject.projectNotes,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(300)))
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(onClick = {editProject(singleProject.projectName)}
                )
                { Text(text = "Edit")

                }
            }

        }
    }
}

/*
* Edit screen functionality below
*/

@Composable
fun ProjectEditPage(
    project: List<Project>,
    updateProject: (Project) -> Unit,
    deleteProject: (Project) -> Unit,
    newProject: Boolean
){
    var newName = ""
    var newStage = ""
    var newNotes = ""
    LazyColumn(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(
            items = project,
            key = {project -> project.projectId}
        ){project ->
            var name by remember {
                mutableStateOf(project.projectName + newName)
            }

            var stage by remember {
                mutableStateOf(project.projectStage + newStage)
            }

            var favorite by remember{
                mutableStateOf(project.projectFavorite)
            }
            var notes by remember{
                mutableStateOf(project.projectNotes)
            }

            Text(
            text = stringResource(R.string.ProjectPage),
            fontSize = 24.sp,
            )

            Spacer(Modifier.height(16.dp))

            // Project Name Field
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)) {
                TextField(
                    value = name,
                    onValueChange = {
                        newName = it
                        name = newName
                    },
                    label = { Text(stringResource(R.string.project)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }


            // Project stage field
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)) {
                TextField(
                    value = stage,
                    onValueChange = {
                        newStage = it
                        stage = newStage
                                    },
                    label = { Text(stringResource(R.string.EnterStage)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)) {
                TextField(
                    value = notes,
                    onValueChange = {
                        newNotes = it
                        notes = newNotes
                    },
                    label = { Text(stringResource(R.string.new_notes)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(IntrinsicSize.Min),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }

        // Button
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(onClick = {
                favorite = if (favorite == 0) {
                    1
                } else {
                    0
                }
            }
            ) {
                Text(stringResource(R.string.favorite))
            }

            // Save Button
            Button(
                onClick = {
                updateProject(Project(
                    projectId = project.projectId,
                    projectName = name,
                    projectStage = stage,
                    projectFavorite = favorite,
                    projectNotes = notes))
            }
            ){
                Text(stringResource(R.string.save))
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = { deleteProject(Project(
                projectId = project.projectId,
                projectName = name,
                projectStage = stage,
                projectFavorite = favorite)) }) { Text(text = "Delete")
                
            }
        }
        
        }
    }
}