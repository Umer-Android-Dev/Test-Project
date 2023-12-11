package com.appozee.technologies.mytestproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.appozee.technologies.mytestproject.ui.theme.MyTestProjectTheme


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appozee.technologies.mytestproject.screens.DatabaseList
import com.appozee.technologies.mytestproject.screens.LoginScreen
import com.appozee.technologies.mytestproject.screens.MainScreen
import com.appozee.technologies.mytestproject.screens.MedicineDetails
import com.appozee.technologies.mytestproject.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestProjectTheme {

                App()

            }
        }
    }
}


@Composable
fun App(){
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login"){

        composable(route = "login"){
            LoginScreen {
                navController.navigate("main/${it}"){
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = "main/{email}", arguments = listOf(
            navArgument("email"){
                type = NavType.StringType
            }
        )){
            val email = it.arguments!!.getString("email")
            MainScreen(email!!,sharedViewModel, onItemClick =  {
                navController.navigate("medicineDetails")
            }, onIconButtonClick = {
                navController.navigate("databaseList")
            })
        }

        composable(route = "medicineDetails"){
            MedicineDetails(sharedViewModel)
        }


        composable(route = "databaseList"){
            DatabaseList()
        }

    }
}



