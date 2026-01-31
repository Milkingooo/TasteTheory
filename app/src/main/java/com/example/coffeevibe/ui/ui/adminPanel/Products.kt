package com.example.coffeevibe.ui.ui.adminPanel

import androidx.activity.contextaware.OnContextAvailableListener
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.ui.theme.Shapes
import com.example.coffeevibe.ui.ui.customUi.MaterialList
import com.example.coffeevibe.ui.ui.customUi.MaterialListItem
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsAdmin(
    menuVm: MenuViewModel? = null,
    inProductActivity: (Int?) -> Unit
){

    val menuViewModel: MenuViewModel = menuVm ?: MenuViewModel(LocalContext.current)

    val listState2 = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val products by menuViewModel.dataList.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var nameDecreasing  by rememberSaveable { mutableStateOf(false) }
    var nameIncreasing by rememberSaveable { mutableStateOf(false) }

    var selectedProductId by remember { mutableIntStateOf(0) }

    val newProducts = mutableListOf<MaterialListItem>()

    products.forEach {
        newProducts.add(
            MaterialListItem(
                title = "${it.name} (ID ${it.id})",
                subtitle = it.status,
                iconTint = colorScheme.onBackground,
                iconBackground = colorScheme.secondaryContainer,
                action = {
                    selectedProductId = it.id
                    inProductActivity(it.id)
                }
            )
        )
    }

    val filteredProducts by remember (
        newProducts, //products
        searchQuery,
        nameIncreasing,
        nameDecreasing
    ) {
        derivedStateOf {
            var result = newProducts //products

            if (searchQuery.isNotBlank()) {
                result = result.filter {
                    it.title.contains(searchQuery, true) || //it.name
                            it.subtitle.contains(searchQuery, true) //it.status
                } as MutableList<MaterialListItem> //delete
            }

            result = when {
                nameIncreasing -> result.sortedBy { it.title } //it.name
                nameDecreasing -> result.sortedByDescending { it.title } //it.name
                else -> result
            } as MutableList<MaterialListItem> //delete

            result
        }
    }


    CoffeeVibeTheme(context2 = LocalContext.current, content = {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            inProductActivity(null)
                        }
                    },
                    containerColor = colorScheme.tertiary,
                    contentColor = colorScheme.onBackground,
                ) {
                    Icon(
                        Icons.Filled.Add,
                        "Добавить",
                        tint = colorScheme.background
                    )
                }
            }
            )
        { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(paddingValues)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    state = listState2,
//                ) {
//                    item {
//                        ProductsFilters(
//                            selectedId =
//                                if (nameDecreasing) 2
//                                else if (nameIncreasing) 1
//                                else 0
//                            ,
//                            filterId = { id ->
//                               when (id) {
//                                   0 -> {
//                                       nameIncreasing = false
//                                       nameDecreasing = false
//                                       searchQuery = ""
//                                   }
//                                   1 -> {
//                                       nameIncreasing = true
//                                       nameDecreasing = false
//                                       searchQuery = ""
//                                   }
//                                   2 -> {
//                                       nameIncreasing = false
//                                       nameDecreasing = true
//                                       searchQuery = ""
//                                   }
//                               }
//                            },
//                            addPosition = {},
//                        )
//                    }
//
//                   items(filteredProducts, key = { it.id }) {
//                        ProductsItem(
//                            name = it.name,
//                            available = it.status
//                        )
//                       Spacer(modifier = Modifier.height(4.dp))
//                    }
//                }
//                ProductsFilters(
//                            selectedId =
//                                if (nameDecreasing) 2
//                                else if (nameIncreasing) 1
//                                else 0
//                            ,
//                            filterId = { id ->
//                               when (id) {
//                                   0 -> {
//                                       nameIncreasing = false
//                                       nameDecreasing = false
//                                       searchQuery = ""
//                                   }
//                                   1 -> {
//                                       nameIncreasing = true
//                                       nameDecreasing = false
//                                       searchQuery = ""
//                                   }
//                                   2 -> {
//                                       nameIncreasing = false
//                                       nameDecreasing = true
//                                       searchQuery = ""
//                                   }
//                               }
//                            },
//                    searchQueryInput = searchQuery,
//                    searchQueryOutput = {
//                        searchQuery = it
//                    }
//                )
                Spacer(Modifier.height(16.dp))
                MaterialList(
                    title = "Products",
                    items = filteredProducts,
                    backgroundColor = colorScheme.secondaryContainer,
                    textColor = colorScheme.onBackground
                )
            }
        }
    })
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProductsItem(
    name: String,
    available: String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = colorScheme.background,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                maxLines = 2,
                modifier = Modifier.width(200.dp),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = available,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                color = colorScheme.background,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductsFilters(
    selectedId: Int,
    filterId: (Int) -> Unit,
    searchQueryInput: String,
    searchQueryOutput: (String) -> Unit,
) {
    val searchBarState = rememberSearchBarState()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FilterChip(
            selected = selectedId == 0,
            onClick = {
                filterId(0)
            },
            modifier = Modifier
                .padding(end = 8.dp),
            label = {
                Text(
                    text = "Все",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 14.sp,
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .animateContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        )

        FilterChip(
            selected = selectedId == 1,
            onClick = {
                filterId(1)
            },
            label = {
                Text(
                    text = "Az",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorScheme.onBackground,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .animateContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Outlined.ArrowDropDown,
//                    contentDescription = "Вверх"
//                )
//            }
        )

        FilterChip(
            selected = selectedId == 2,
            onClick = {
                filterId(2)

            },
            label = {
                Text(
                    text = "aZ",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorScheme.onBackground,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .animateContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Outlined.ArrowDropUp,
//                    contentDescription = "Вверх"
//                )
//            }
        )

        SearchBar(
            state = searchBarState,
            inputField = {
                TextField(
                    value = searchQueryInput,
                    onValueChange = { searchQueryOutput(it) },
                    placeholder = {
                        Text(
                            text = "Поиск",
                            color = colorScheme.onBackground
                        )
                    },
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = colorScheme.onBackground,
                        focusedTextColor = colorScheme.onBackground,
                        unfocusedTextColor = colorScheme.onBackground,
                        unfocusedContainerColor = colorScheme.secondaryContainer,
                        focusedContainerColor = colorScheme.secondaryContainer
                    ),
                    keyboardOptions = KeyboardOptions(
                        showKeyboardOnFocus = KeyboardOptions.Default.showKeyboardOnFocus,
                    )
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = colorScheme.secondaryContainer,
                dividerColor = colorScheme.onSecondaryContainer
            ),
            shape = Shapes.medium,

        )
    }
}