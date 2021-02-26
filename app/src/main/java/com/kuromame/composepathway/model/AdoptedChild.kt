package com.kuromame.composepathway.model

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.painter.Painter

data class AdoptedChild(
    val fosterParent: FosterParent,
    val name: String,
    val image: Int,
    val old: String,
    val description: String,
    val descriptionDetail: String
)