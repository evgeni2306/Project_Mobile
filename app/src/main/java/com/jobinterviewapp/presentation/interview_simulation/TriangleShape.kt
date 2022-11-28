package com.jobinterviewapp.presentation.interview_simulation

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            // Moves to top center position
            moveTo(size.maxDimension / 2f, 0f)
            // Add line to bottom right corner
            lineTo(size.maxDimension, size.maxDimension)
            // Add line to bottom left corner
            lineTo(0f, size.maxDimension)
        }
        return Outline.Generic(path = trianglePath)
    }
}