package com.nicolasrf.home_presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.nicolasrf.core_presentation.HabitButton
import com.nicolasrf.home_presentation.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeAskPermission(
    permission: String,
    modifier: Modifier = Modifier
) {
    val permissionState =
        rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = { },
            modifier = modifier,
            confirmButton = {
                HabitButton(
                    text = stringResource(id = R.string.accept),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    permissionState.launchPermissionRequest()
                }
            },
            title = {
                Text(text = stringResource(id = R.string.permission_required))
            },
            text = {
                Text(text = stringResource(id = R.string.permission_required_description))
            }
        )
    }
}