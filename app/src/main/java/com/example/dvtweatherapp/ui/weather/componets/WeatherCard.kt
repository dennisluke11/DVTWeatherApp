package com.example.dvtweatherapp.ui.weather.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.dvtweatherapp.utils.Dimens

@Composable
fun WeatherCard(
    dayOfWeek: String,
    temperature: String,
    iconResId: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(Dimens.CardHeight)
            .clip(RoundedCornerShape(Dimens.CardCornerRadius)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dayOfWeek,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.SpacerHeightExtraSmall))
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(Dimens.ImageSizeMedium ),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = temperature,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
                    color = Color.Black
                ),
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
