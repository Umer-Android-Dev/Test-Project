package com.appozee.technologies.mytestproject.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appozee.technologies.mytestproject.R
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.viewModel.MedicineViewModel
import com.appozee.technologies.mytestproject.viewModel.SharedViewModel
import java.util.Calendar


@Composable
fun MainScreen(
    email: String,
    sharedViewModel: SharedViewModel,
    onItemClick: (MedicineModelItem) -> Unit,
    onIconButtonClick: () -> Unit
) {

    val context = LocalContext.current

    // Check if internet is available
    val isInternetAvailable = isInternetAvailable(context)

    if (!isInternetAvailable){
        return
    }

    val medicineViewModel: MedicineViewModel = hiltViewModel()

    val medicinesData = medicineViewModel.medicines.collectAsState()



    Log.i("My_STATUS", "MainScreen: ${medicinesData.value}")


    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when {
        currentHour < 12 -> "Good morning"
        currentHour < 18 -> "Good afternoon"
        else -> "Good evening"
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(
                Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFffffff),
                        Color(0xFFE3E3E3)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "$greeting, $email!", style = MaterialTheme.typography.titleMedium, fontFamily = FontFamily(
                    Font(R.font.inter_medium)))

                IconButton(onClick = { onIconButtonClick() }) {
                    Icon(imageVector =Icons.Default.List , contentDescription = "", tint = Color(0xFF64748B))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))


            if (medicinesData.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...", style = MaterialTheme.typography.displayMedium)
                }
            } else {
                LazyColumn {
                    items(medicinesData.value.size) {
                        val data = MedicineModelItem(medicinesData.value[it].dose,medicinesData.value[it].name,medicinesData.value[it].strength)
                        MedicineCard(medicine = data, onItemClick = onItemClick,sharedViewModel)
                    }
                }
            }




        }

    }


}

@Composable
fun MedicineCard(
    medicine: MedicineModelItem,
    onItemClick: (MedicineModelItem) -> Unit,
    sharedViewModel: SharedViewModel
) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation =  4.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor =  Color(0xFF64748B)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.i(
                    "MY_DATA",
                    "MedicineCard: ${medicine.dose} ${medicine.name} ${medicine.strength} "
                )
                sharedViewModel.addCurrentMedicine(medicine)
                onItemClick(medicine)
            }

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            medicineDataItem(medicine)
        }
    }
}

@Composable
fun medicineDataItem(medicine: MedicineModelItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Name: ${medicine.name}",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Dose: ${medicine.dose}", color = Color.White, style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Strength: ${medicine.strength}", color = Color.White, style = MaterialTheme.typography.bodyMedium)
}


@SuppressLint("ServiceCast")
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    }
}




