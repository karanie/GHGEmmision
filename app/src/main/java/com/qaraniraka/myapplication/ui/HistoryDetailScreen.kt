package com.qaraniraka.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.model.ActivityBerkendaraDetail
import com.qaraniraka.myapplication.model.ActivityHistoryData
import com.qaraniraka.myapplication.model.ActivityMakanDetail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BerkendaraDetail(
    engineSize: Double,
    cylinders: Int,
    fuelConsumption: Double,
    fuelType: String,
    travelDistance: Double
) {
    Text(
        text = "Berkendara",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Text(
        text = "Ukuran Mesin: ${String.format("%.2f", engineSize)}",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Jumlah Cylinder: $cylinders",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Fuel Consumption per 100km: ${String.format("%.2f", fuelConsumption)}",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Jenis Bahan Bakar: $fuelType",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Jarak Berkendara: ${String.format("%.2f", travelDistance)}",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun MakanDetail(
    foodProduct: String,
    foodWeight: Double
) {
    Text(
        text = "Makan",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Text(
        text = "Bahan Dasar: $foodProduct",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Berat Makanan: ${String.format("%.2f", foodWeight)}",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HistoryDetailScreen(historyData: ActivityHistoryData? = null) {
    val context = LocalContext.current
    val gson = Gson()
    val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (historyData != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                        ) {
                            if (historyData.activityType == "berkendara") {
                                val detailData = gson.fromJson(
                                    historyData.activityDetail,
                                    ActivityBerkendaraDetail::class.java
                                )
                                BerkendaraDetail(
                                    engineSize = detailData.engineSize,
                                    cylinders = detailData.cylinders,
                                    fuelConsumption = detailData.fuelConsumption,
                                    fuelType = detailData.fuelType,
                                    travelDistance = detailData.travelDistance
                                )
                            } else if (historyData.activityType == "makan") {
                                val detailData = gson.fromJson(
                                    historyData.activityDetail,
                                    ActivityMakanDetail::class.java
                                )
                                MakanDetail(
                                    foodProduct = detailData.foodProduct,
                                    foodWeight = detailData.foodWeight
                                )
                            }

                            Text(
                                text = "Emisi: ${String.format("%.2f", historyData.emission)}g CO2",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            val ctime = LocalDateTime.parse(historyData.ctime, dateFormatter)
                            Text(
                                text = "${ctime.month.name} ${ctime.dayOfMonth}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "${String.format("%02d", ctime.hour)}.${
                                    String.format(
                                        "%02d",
                                        ctime.minute
                                    )
                                }",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Bagikan", style = MaterialTheme.typography.titleMedium)
                }
                Button(
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Kembali", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}