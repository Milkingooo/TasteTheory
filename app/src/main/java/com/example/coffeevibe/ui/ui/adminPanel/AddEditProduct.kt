package com.example.coffeevibe.ui.ui.adminPanel

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import coil.imageLoader
import com.example.coffeevibe.R
import com.example.coffeevibe.model.ProductAdmin
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.other.BaseButton
import com.example.coffeevibe.ui.ui.other.TextAreaWithName
import com.example.coffeevibe.ui.ui.other.TextFieldWithName
import com.example.coffeevibe.utils.CashApplication
import com.example.coffeevibe.utils.VkCloudStorage
import com.example.coffeevibe.viewmodel.MenuViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddEditProductScreen(
    menuViewModel: MenuViewModel,
    id: Int?
) {
    val context = LocalContext.current

    var product by remember { mutableStateOf(ProductAdmin()) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id != null) {
            menuViewModel.getProductInfoById(id) { loadedProduct ->
                product = loadedProduct
                uploadedImageUrl = loadedProduct.image
            }
        }
    }
    val scope = rememberCoroutineScope()

    fun handleImageSelection(uri: Uri) {
        selectedUri = uri
        isUploading = true

        scope.launch {
            val url = VkCloudStorage.uploadImage(context, uri)
            if (url != null) {
                uploadedImageUrl = url
                product.image = url
            }
            isUploading = false
        }
    }

    fun handleSave() {
        if (uploadedImageUrl != null) {
            product.image = uploadedImageUrl!!
        }
        menuViewModel.updateProductById(product.id, product)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            handleImageSelection(uri)
        }
    }


    CoffeeVibeTheme(context2 = LocalContext.current, content = {
//        val imageLoader = (LocalContext.current.applicationContext as CashApplication).imageLoader
//
//        val painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(LocalContext.current)
//                .data(data = uploadedImageUrl)
//                .crossfade(true)
//                .memoryCacheKey(uploadedImageUrl)
//                .diskCacheKey(uploadedImageUrl)
//                .transformations(RoundedCornersTransformation(10f))
//                .error(R.drawable.error_load)
//                .build(),
//            imageLoader = imageLoader as ImageLoader
//        )
        val imageLoader = (LocalContext.current.applicationContext as CashApplication).imageLoader

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = product.image)
                .crossfade(true)
                .memoryCacheKey(product.image)
                .diskCacheKey(product.image)
                .transformations(RoundedCornersTransformation(10f))
                .error(R.drawable.error_load)
                .build(),
            imageLoader = imageLoader
        )

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Управление позицией",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left
                        )
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
                    scrollBehavior = scrollBehavior
                )
            }
        )
        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(innerPadding)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    selectedUri?.let { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(shape = Shapes.medium),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                    } ?: Box(modifier = Modifier.size(130.dp))

                    if (isUploading) {
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(shape = Shapes.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = colorScheme.primary
                            )
                        }
                    } else {
                        Image(
                            painter = painter,
                            contentDescription = "Current Image",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(shape = Shapes.medium),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isUploading) {
                    Text(
                        text = "Загрузка изображения...",
                        color = colorScheme.primary,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                BaseButton(
                    title = "Выбрать изображение",
                    click = {
                        launcher.launch("image/*")
                    },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                    enabled = !isUploading
                )

                TextFieldWithName(
                    title = "Название",
                    value = product.name,
                    exitValue = {
                        product.name = it
                    },
                    isInCorrect = false,
                    placeholder = "Название продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                TextFieldWithName(
                    title = "Категория",
                    value = product.category,
                    exitValue = {
                        product.category = it
                    },
                    isInCorrect = false,
                    placeholder = "Категория продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                TextAreaWithName(
                    title = "Описание",
                    value = product.description,
                    exitValue = {
                        product.description = it
                    },
                    isInCorrect = false,
                    placeholder = "Описание продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                Row(
                    Modifier.fillMaxWidth()
                ) {
                    TextFieldWithName(
                        title = "Цена",
                        value = product.price.toString(),
                        exitValue = {
                            product.price = it.toInt()
                        },
                        keyboardType = KeyboardType.Number,
                        isInCorrect = false,
                        placeholder = "Цена продукта",
                        modifier = Modifier.weight(1f),
                        enabled = !isUploading
                    )

                    TextFieldWithName(
                        title = "Цена по скидке",
                        value = product.discountPrice.toString(),
                        exitValue = {
                            product.discountPrice = it.toInt()
                        },
                        keyboardType = KeyboardType.Number,
                        isInCorrect = false,
                        placeholder = "Цена п��од��кта по скидке",
                        modifier = Modifier.weight(1f),
                        enabled = !isUploading
                    )
                }

                Spacer(Modifier.size(8.dp))

                TextFieldWithName(
                    title = "Состав (через ; )",
                    value = product.composition,
                    exitValue = {
                        product.composition = it
                    },
                    isInCorrect = false,
                    placeholder = "Состав продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                TextFieldWithName(
                    title = "КБЖУ (через ; )",
                    value = product.kbju,
                    exitValue = {
                        product.kbju = it
                    },
                    isInCorrect = false,
                    placeholder = "КБЖУ продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                TextFieldWithName(
                    title = "Статус (Доступен/Недоступен)",
                    value = product.status,
                    exitValue = {
                        product.status = it
                    },
                    isInCorrect = false,
                    placeholder = "Статус продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                )

                BaseButton(
                    title = "Сохранить",
                    click = {
                        handleSave()
                    },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                    enabled = !isUploading
                )
            }
        }
    })
}