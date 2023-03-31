package tech.mosaleh.together.presentation.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import tech.mosaleh.together.R

//state.passwordError != null
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    label: String = "Password",
    password: String,
    isError: Boolean,
    onChanged: (String) -> Unit
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(value = password,
        onValueChange = {
            onChanged(it)
        },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                val icon =
                    if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(icon, contentDescription = "Toggle password visibility")
            }
        })
}


@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean,
    error: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChanged,
        isError = isError,
        modifier = modifier,
        placeholder = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
    )
    if (error != null) {
        Text(
            text = error, color = MaterialTheme.colors.error
        )
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(id = R.drawable.empty)
        val errorImage = "Not Found yet"
        Image(painter = image, contentDescription = errorImage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, fontSize = 16.sp, color = Color.Red)
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(Color(0x00, 0x00, 0x00, 0x51))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}


@JvmName("ImagePicker1")
@Composable
fun ImagePicker(image: ImageBitmap?, onImageSelected: (Uri?) -> Unit) {
    val imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> onImageSelected(uri) })

    val context = LocalContext.current
    val drawable = remember(R.drawable.ic_baseline_person_add_24) {
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_add_24)
    }
    val defaultImg = drawableToBitmap(drawable!!)
    val final = image ?: defaultImg.asImageBitmap()

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Gray)
                .clip(RoundedCornerShape(bottomEnd = 16.dp))
        ) {
            Image(
                bitmap = final,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomEnd = 50.dp)),
                contentScale = ContentScale.Crop
            )
            FloatingActionButton(
                onClick = { launcher.launch("image/*") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                content = {
                    Icon(Icons.Default.Add, contentDescription = null)
                },
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(8.dp)
            )
        }

    }
}


fun drawableToBitmap(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}


@Composable
fun DropdownOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Case Type",
                modifier = Modifier.clickable { expanded = true },
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
    )

    DropdownMenu(
        modifier = Modifier,
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(onClick = {
                onValueChange(item)
                expanded = false
            }) {
                Text(text = item)
            }
        }
    }
}

@Composable
fun EnumDropdownRow(
    modifier: Modifier = Modifier,
    label: String,
    enumValues: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
) {
    Row(modifier = modifier) {
        DropdownOutlinedTextField(
            label = label,
            value = selectedValue,
            onValueChange = onValueChange,
            items = enumValues
        )
    }
}







