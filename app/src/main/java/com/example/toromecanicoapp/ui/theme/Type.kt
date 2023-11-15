package com.example.toromecanicoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.toromecanicoapp.R

val lato = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_bold),
)


val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    labelSmall = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)