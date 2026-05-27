package com.example.coffeevibe.ui.ui.adminPanel

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.coffeevibe.ui.ui.customUi.BoxDropdownMenuItem
import com.example.coffeevibe.ui.ui.customUi.DropdownMenuWithName
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
    var isSaving by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var discountError by remember { mutableStateOf(false) }

    val goods by menuViewModel.dataList.collectAsState()
    val categories = remember(goods) {
        goods.map { it.category }.distinct().sorted()
    }

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

    fun validate(): Boolean {
        nameError = product.name.isBlank()
        priceError = product.price <= 0
        discountError = product.discountPrice > 0 && product.discountPrice >= product.price
        return !nameError && !priceError && !discountError
    }

    fun handleSave() {
        if (!validate()) return
        if (uploadedImageUrl != null) {
            product.image = uploadedImageUrl!!
        }
        isSaving = true
        saveError = false
        menuViewModel.updateProductById(product.id, product) { success ->
            isSaving = false
            saveError = !success
        }
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
                            text = if (id == null) "Создание товара" else "Редактирование товара",
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(innerPadding)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    Modifier.fillMaxWidth(),
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
                            CircularProgressIndicator(color = colorScheme.primary)
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
                    click = { launcher.launch("image/*") },
                    color = ButtonDefaults.buttonColors(colorScheme.primary),
                    enabled = !isUploading && !isSaving
                )

                TextFieldWithName(
                    title = "Название",
                    value = product.name,
                    exitValue = {
                        product.name = it
                        nameError = false
                    },
                    isInCorrect = nameError,
                    placeholder = "Название продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading && !isSaving
                )
                if (nameError) {
                    Text(
                        text = "Название не может быть пустым",
                        color = colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                val categoryBoxes = categories.map { cat ->
                    BoxDropdownMenuItem(
                        title = cat,
                        icon = Icons.Filled.GridView,
                        action = { product.category = cat }
                    )
                }
                if (categoryBoxes.isNotEmpty()) {
                    DropdownMenuWithName(
                        title = "Категория",
                        current = product.category.ifEmpty { categories.firstOrNull() ?: "" },
                        items = categoryBoxes,
                        isExpanded = false
                    )
                } else {
                    TextFieldWithName(
                        title = "Категория",
                        value = product.category,
                        exitValue = { product.category = it },
                        isInCorrect = false,
                        placeholder = "Категория продукта",
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isUploading && !isSaving
                    )
                }

                TextAreaWithName(
                    title = "Описание",
                    value = product.description,
                    exitValue = { product.description = it },
                    isInCorrect = false,
                    placeholder = "Описание продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading && !isSaving
                )

                Row(Modifier.fillMaxWidth()) {
                    TextFieldWithName(
                        title = "Цена",
                        value = product.price.toString(),
                        exitValue = {
                            product.price = it.toIntOrNull() ?: 0
                            priceError = false
                        },
                        keyboardType = KeyboardType.Number,
                        isInCorrect = priceError,
                        placeholder = "Цена продукта",
                        modifier = Modifier.weight(1f),
                        enabled = !isUploading && !isSaving
                    )

                    TextFieldWithName(
                        title = "Цена по скидке",
                        value = product.discountPrice.toString(),
                        exitValue = {
                            product.discountPrice = it.toIntOrNull() ?: 0
                            discountError = false
                        },
                        keyboardType = KeyboardType.Number,
                        isInCorrect = discountError,
                        placeholder = "Цена по скидке",
                        modifier = Modifier.weight(1f),
                        enabled = !isUploading && !isSaving
                    )
                }
                if (priceError) {
                    Text(
                        text = "Цена должна быть больше 0",
                        color = colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                if (discountError) {
                    Text(
                        text = "Цена по скидке не может быть больше или равна обычной цене",
                        color = colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(Modifier.size(8.dp))

                TextFieldWithName(
                    title = "Состав (через ; )",
                    value = product.composition,
                    exitValue = { product.composition = it },
                    isInCorrect = false,
                    placeholder = "Состав продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading && !isSaving
                )

                TextFieldWithName(
                    title = "КБЖУ (через ; )",
                    value = product.kbju,
                    exitValue = { product.kbju = it },
                    isInCorrect = false,
                    placeholder = "КБЖУ продукта",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading && !isSaving
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Доступен",
                        color = colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                    )
                    Switch(
                        checked = product.status != "Недоступен",
                        onCheckedChange = {
                            product.status = if (it) "Доступен" else "Недоступен"
                        },
                        enabled = !isUploading && !isSaving
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "Предпросмотр",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorScheme.background),
                    shape = Shapes.large,
                    border = BorderStroke(1.dp, colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(shape = Shapes.medium),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))

                        Column(Modifier.weight(1f)) {
                            Text(
                                text = product.name.ifEmpty { "Название товара" },
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            if (product.discountPrice > 0) {
                                Text(
                                    text = "${product.price}₽",
                                    color = colorScheme.onBackground.copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                )
                                Text(
                                    text = "${product.discountPrice}₽",
                                    color = Color(0xFFFDD835),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                Text(
                                    text = if (product.price > 0) "${product.price}₽" else "Цена не указана",
                                    color = colorScheme.onBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = product.status.ifEmpty { "Доступен" },
                            color = if (product.status == "Недоступен") colorScheme.error else Color(0xFF4CAF50),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                        )
                    }
                }

                Spacer(Modifier.size(16.dp))

                BaseButton(
                    title = if (isSaving) "Сохранение..." else "Сохранить",
                    click = { handleSave() },
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (saveError) colorScheme.error else colorScheme.primary
                    ),
                    enabled = !isUploading && !isSaving
                )

                if (saveError) {
                    Text(
                        text = "Ошибка при сохранении. Попробуйте снова.",
                        color = colorScheme.error,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    })
}
