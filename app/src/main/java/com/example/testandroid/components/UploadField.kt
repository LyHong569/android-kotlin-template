package com.example.testandroid.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testandroid.cores.utils.openExternally

enum class FileSource { Images, Files, All }

data class FileMeta(
    val uri: Uri,
    val name: String,
    val sizeBytes: Long,
    val mimeType: String?,
) {
    val isImage: Boolean get() = mimeType?.startsWith("image/") == true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadField(
    files: List<Uri>,
    onFilesChange: (List<Uri>) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    maxFiles: Int = 5,
    mimeTypes: Array<String> = arrayOf("*/*"),
    source: FileSource = FileSource.All,
) {
    val context = LocalContext.current
    val multiple = maxFiles != 1
    val remainingSlots = (maxFiles - files.size).coerceAtLeast(1)

    var showSourceSheet by remember { mutableStateOf(false) }


    fun addUris(picked: List<Uri>) {
        if (picked.isEmpty()) return
        picked.forEach { uri ->
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }


        val updatedFiles = if (maxFiles == 1) picked else files + picked
        onFilesChange(
            updatedFiles.distinct().take(maxFiles)
        )
    }

    val gallerySingle = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { addUris(listOf(it)) } }
    
    val galleryMulti = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = remainingSlots.coerceIn(2, 100)
        )
    ) { addUris(it) }

    val docsSingle = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> uri?.let { addUris(listOf(it)) } }

    val docsMulti = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { addUris(it) }

    fun launchGallery() {
        val req = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        if (multiple) galleryMulti.launch(req) else gallerySingle.launch(req)
    }

    fun launchDocs() {
        if (multiple) docsMulti.launch(mimeTypes) else docsSingle.launch(mimeTypes)
    }

    Column {

        UploadDropTarget({
            when (source) {
                FileSource.Images -> launchGallery()
                FileSource.Files -> launchDocs()
                FileSource.All -> showSourceSheet = true
            }
        })

        Spacer(Modifier.height(12.dp))

        DisplayUploadedFiles(context, files, onFilesChange = onFilesChange)

        if (showSourceSheet) {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = { showSourceSheet = false },
                sheetState = sheetState,
            ) {
                ListItem(
                    headlineContent = { Text("Choose from gallery") },
                    modifier = Modifier.clickable {
                        showSourceSheet = false
                        launchGallery()
                    },
                )
                ListItem(
                    headlineContent = { Text("Browse files") },
                    modifier = Modifier.clickable {
                        showSourceSheet = false
                        launchDocs()
                    },
                )
                Spacer(Modifier.height(16.dp))
            }
        }

    }


}

@Composable
private fun UploadDropTarget(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .drawBehind({
                drawRoundRect(
                    color = Color.Gray.copy(alpha = 0.6f),
                    style = Stroke(
                        width = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            IconButton(onClick = {}) { }
            Spacer(Modifier.padding(top = 10.dp))
            Row {
                Text("Click to Upload", fontSize = 16.sp)
                Text(" or drag and drop", fontSize = 16.sp)
            }
            Text("(Max. File size: 25 MB)", fontSize = 12.sp)
        }
    }
}

@Composable
fun DisplayUploadedFiles(context: Context, files: List<Uri>, onFilesChange: (List<Uri>) -> Unit) {
    files.forEach { uri ->
        FileCard(
            uri = uri,
            onRemove = { onFilesChange(files - uri) },
            onClick = { meta ->
                openExternally(context, uri, meta.mimeType)
            },
        )
        Spacer(Modifier.height(8.dp))
    }
}

