package com.appozee.technologies.mytestproject.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appozee.technologies.mytestproject.R
import com.appozee.technologies.mytestproject.model.MedicineModelItem
import com.appozee.technologies.mytestproject.viewModel.MedicineDetailViewModel
import com.appozee.technologies.mytestproject.viewModel.SharedViewModel

@Composable
fun DatabaseList() {
    val medicineDetailViewModel: MedicineDetailViewModel = hiltViewModel()
    val medicinesData = medicineDetailViewModel.medicinesDataList.collectAsState()

    Box(
        contentAlignment = Alignment.TopCenter,
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

            if (medicinesData.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No record found.", style = MaterialTheme.typography.displayMedium)
                }
            } else {
                LazyColumn {
                    items(medicinesData.value.size) {
                        val data = MedicineModelItem(medicinesData.value[it].dose,medicinesData.value[it].name,medicinesData.value[it].strength)
                        MedicineCard(medicine = data)
                    }
                }
            }




        }

    }
}

@Composable
fun MedicineCard(
    medicine: MedicineModelItem
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

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            medicineDataItem(medicine)
        }
    }
}
