package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Lorem = """
    ثم الخاطفة التبرعات لمّ، لان لغزو جزيرتي إذ. بحث ووصف يرتبط المشترك أي، أن لان صفحة تاريخ قائمة.
    ٣٠ أضف قامت الجنوبي والإتحاد، مما عل عقبت الجنوب، لان إذ مشروط حاملات الأوروبية.
    بشكل فبعد فشكّل غير مع. ما عدد دارت فسقط الطرفين، ونتج إعمار انه من.
    من بحث مايو التي، على و خطّة الساحة، أي مدن ضمنها الأوروبية.
""".trimIndent()


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)