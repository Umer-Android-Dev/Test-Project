package com.appozee.technologies.mytestproject.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.viewModel.MedicineDetailViewModel
import com.appozee.technologies.mytestproject.viewModel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MedicineDetails(sharedViewModel: SharedViewModel) {
    val medicineDetailViewModel: MedicineDetailViewModel = hiltViewModel()

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
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation =  4.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor =  Color(0xFF64748B)
            ),
            modifier = Modifier.padding(14.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                val updatedMedicine = sharedViewModel.medicine?.let { medicine ->
                    MedicineModelItem(
                        dose = medicine.dose,
                        name = medicine.name,
                        strength = medicine.strength
                    )
                } ?: MedicineModelItem("", "", "")

                medicineDataDetailItem(updatedMedicine,medicineDetailViewModel)

            }

        }

    }
}

@Composable
fun medicineDataDetailItem(
    medicine: MedicineModelItem,
    medicineDetailViewModel: MedicineDetailViewModel
) {

    var isMedicineAdded by remember { mutableStateOf(false) }

    LaunchedEffect(medicine) {
        // Call the suspend function within a coroutine
        isMedicineAdded = withContext(Dispatchers.IO) {
            medicineDetailViewModel.isMedicineAdded(medicine)
        }
    }

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
        Image(
            imageVector = Icons.Filled.Favorite,
            colorFilter = if (isMedicineAdded) {
                ColorFilter.tint(Color.Red)
            } else {
                ColorFilter.tint(Color.White)
            },
            alignment = Alignment.TopEnd,
            contentDescription = "Name",
            modifier = Modifier
                .clickable {
                    if (isMedicineAdded) {
                        // todo: Remove medicine from database
                        medicineDetailViewModel.removeMedicine(medicine)
                    } else {
                        // todo: Add medicine to database
                        medicineDetailViewModel.addMedicine(medicine)
                    }
                    isMedicineAdded = !isMedicineAdded
                }
                .size(30.dp)

        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Dose: ${medicine.dose}", color = Color.White, style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Strength: ${medicine.strength}", color = Color.White, style = MaterialTheme.typography.bodyMedium)
}
