package com.example.coffeevibe.ui.ui.customUi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.Shapes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SimpleSearchBarSample() {
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()
    val inputField =
        @Composable {
            InputField(
                modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {
                    Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search...")
                },
                leadingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Expanded) {
                        TooltipBox(
                            positionProvider =
                                TooltipDefaults.rememberTooltipPositionProvider(
                                    TooltipAnchorPosition.Above
                                ),
                            tooltip = { PlainTooltip { Text("Back") } },
                            state = rememberTooltipState(),
                        ) {
                            IconButton(
                                onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back",
                                )
                            }
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            )
        }
    SearchBar(state = searchBarState, inputField = inputField)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(
    title: String,
    searchQuery: String,
    isSearching: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSearchToggle: () -> Unit,

    onlyDiscount: Boolean,
    priceIncreasing: Boolean,
    priceDecreasing: Boolean,
    onOnlyDiscountChange: (Boolean) -> Unit,
    onPriceIncreasingChange: (Boolean) -> Unit,
    onPriceDecreasingChange: (Boolean) -> Unit,

    scrollBehavior: TopAppBarScrollBehavior
) {
    var filtersMenuOpen by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.background
        ),
        title = {
            SmallTextField(
                height = 40.dp,
                text = searchQuery,
                onTextChanged = { onSearchQueryChange(it) },
                placeholderText = "Поиск по меню...",
                fontSize = 16.sp
            )
        },
        navigationIcon = {
            Text(
                text = title,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                color = colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        actions = {
            FiltersAction(
                expanded = filtersMenuOpen,
                onExpandChange = { filtersMenuOpen = it },
                onlyDiscount = onlyDiscount,
                priceIncreasing = priceIncreasing,
                priceDecreasing = priceDecreasing,
                onOnlyDiscountChange = onOnlyDiscountChange,
                onPriceIncreasingChange = onPriceIncreasingChange,
                onPriceDecreasingChange = onPriceDecreasingChange
            )
        }
    )
}
@Composable
private fun FiltersAction(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onlyDiscount: Boolean,
    priceIncreasing: Boolean,
    priceDecreasing: Boolean,
    onOnlyDiscountChange: (Boolean) -> Unit,
    onPriceIncreasingChange: (Boolean) -> Unit,
    onPriceDecreasingChange: (Boolean) -> Unit
) {
    Box {
        IconButton(onClick = { onExpandChange(true) }) {
            Icon(Icons.Outlined.FilterAlt, contentDescription = "Фильтры")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) },
            shape = Shapes.medium
        ) {

            FilterItem(
                text = "Только со скидкой",
                checked = onlyDiscount,
                onCheckedChange = onOnlyDiscountChange
            )

            FilterItem(
                text = "По возрастанию цены",
                checked = priceIncreasing,
                onCheckedChange = {
                    onPriceIncreasingChange(it)
                    if (it) onPriceDecreasingChange(false)
                }
            )

            FilterItem(
                text = "По убыванию цены",
                checked = priceDecreasing,
                onCheckedChange = {
                    onPriceDecreasingChange(it)
                    if (it) onPriceIncreasingChange(false)
                }
            )
        }
    }
}

@Composable
private fun FilterItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    DropdownMenuItem(
        text = { Text(text) },
        onClick = { onCheckedChange(!checked) },
        leadingIcon = {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    )
}