package com.example.android_14_compose_app.screens

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android_14_compose_app.utils.PermissionAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    context: Context,
    permission: String,
    permissionAction: (PermissionAction) -> Unit
) {
   val isPermissionGranted = checkIfPermissionGranted(context,permission)
   if(isPermissionGranted) {
       permissionAction(PermissionAction.PermissionGranted)
   }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            permissionAction(PermissionAction.PermissionGranted)
        } else {
            permissionAction(PermissionAction.PermissionDenied)
        }
    }

    val showPermissionRationale = shouldShowPermissionRationale(context,permission)
    var isDialogDismissed by remember { mutableStateOf(true) }
    var isPristine by remember { mutableStateOf(true) }

    if((showPermissionRationale && !isDialogDismissed) || (!isDialogDismissed && !isPristine)) {
        isPristine = false
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                isDialogDismissed = true
                permissionAction(PermissionAction.PermissionDenied)
            },
            title = { Text(text = "Permission Required") },
            text = { Text(text = "This app requires the location permission to be granted.") },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionLauncher.launch(permission)
                    }
                ) {
                    Text(text = "Grant Access")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionAction(PermissionAction.PermissionDenied)
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    } else {
        if(!isDialogDismissed) {
            SideEffect {
                permissionLauncher.launch(permission)
            }
        }
    }

}


fun checkIfPermissionGranted(context: Context,permission: String): Boolean {
    return (
            ContextCompat.checkSelfPermission(context,permission) ==
                    PackageManager.PERMISSION_GRANTED
            )
}

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
    val activity = context as Activity?
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity!!,
        permission
    )
}