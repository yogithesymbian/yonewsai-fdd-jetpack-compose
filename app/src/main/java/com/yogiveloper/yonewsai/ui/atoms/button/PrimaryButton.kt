package com.yogiveloper.yonewsai.ui.atoms.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: AppButtonShape = AppButtonShape.Rounded,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    AppButton(
        text,
        onClick,
        modifier,
        variant = AppButtonVariant.Primary,
        shape,
        enabled,
        isLoading,
        icon,
    )
}