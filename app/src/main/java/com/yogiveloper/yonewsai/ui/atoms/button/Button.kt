package com.yogiveloper.yonewsai.ui.atoms.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class AppButtonVariant {
    Primary,
    Secondary,
    Text
}

enum class AppButtonShape {
    Rounded,
    Pill,
    Full
}

/**
 * Button Component
 *
 * @param text Text preview.
 * @param onClick Action when a button Clicked.
 * @param modifier Modifier for customize on outside.
 * @param variant Type Visual Button (Primary, Secondary, Text).
 * @param shape Shape Visual Button (Rounded, Pill, Full).
 * @param enabled Set button enable or disable.
 * @param isLoading Show indicator loading when true and disable a button.
 * @param icon Vector icon optional to show on left side of text.
 */

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AppButtonVariant = AppButtonVariant.Primary,
    shape: AppButtonShape = AppButtonShape.Rounded,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    val buttonColors: ButtonColors = when (variant) {
        AppButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        AppButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        AppButtonVariant.Text -> ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    val buttonShape: Shape = when (shape) {
        AppButtonShape.Rounded -> RoundedCornerShape(12.dp)
        AppButtonShape.Pill -> androidx.compose.foundation.shape.RoundedCornerShape(50) // atau CircleShape
        AppButtonShape.Full -> androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
    }

    if (variant == AppButtonVariant.Text) {
        TextButton(
            onClick = onClick,
            enabled = enabled && !isLoading,
            modifier = modifier,
            shape = buttonShape,
            colors = buttonColors,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp) // Padding yang sesuai untuk TextButton
        ) {
            AppButtonContent(isLoading, icon, text)
        }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled && !isLoading,
            modifier = modifier
        ) {
            AppButtonContent(isLoading, icon, text)
        }
    }
}

@Composable
private fun AppButtonContent(
    isLoading: Boolean,
    icon: ImageVector?,
    text: String
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp
        )
    } else {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text)
    }
}
