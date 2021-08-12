package com.ruben.composition.screens.bottomsheet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ruben.composition.R
import com.ruben.composition.components.MojAppBar
import com.ruben.composition.ui.theme.CommentETColor
import com.ruben.composition.ui.theme.Pink
import com.ruben.composition.ui.theme.SettingSwitchColor
import com.ruben.composition.ui.theme.SettingValueColor

/**
 * Created by Ruben Quadros on 12/08/21
 **/
@Composable
fun CommentsFilterScreen(navigateBack: () -> Unit) {
    val textState = remember {
        mutableStateOf(TextFieldValue())
    }
    val filterList = remember {
        mutableStateListOf<String>()
    }

    val needsUpdate = remember {
        mutableStateOf(false)
    }

    fun onRemove(index: Int) {
        filterList.removeAt(index)
        needsUpdate.value = true
    }

    Scaffold(
        topBar = { MojAppBar(
            title = stringResource(id = R.string.comments_filter),
            navigateBack = navigateBack)
        },
        content = {
            Column(modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.comments_filter_desc1),
                    color = SettingValueColor
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.comments_filter_desc2),
                    color = SettingValueColor
                )
                TextField(
                    value = textState.value, onValueChange = { textState.value = it },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(CommentETColor),
                    placeholder = { Text(text = "Add Keyword1, keyword2, ...", color = SettingValueColor) },
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle(color = SettingValueColor),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            Log.d("Ruben", "Done")
                            filterList.add(textState.value.text)
                            textState.value = TextFieldValue(text = "")
                            needsUpdate.value = true
                        }
                    )
                )
                if (filterList.size > 0) {
                    Log.d("Ruben", "${filterList.size}")
                    LazyColumn {
                        itemsIndexed(filterList) { index, item ->
                            FilterItem(
                                index = index,
                                filterString = item,
                                onRemove = { removeIndex -> onRemove(index = removeIndex) }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FilterItem(index: Int, filterString: String, onRemove: (Int) -> Unit) {
    Log.d("Ruben", filterString)
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (minus, text, plus) = createRefs()
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(minus) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            onClick = { onRemove(index) }
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Minus Button", tint = Pink)
        }
        Text(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentWidth()
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(minus.end)
                    end.linkTo(plus.start)
                    bottom.linkTo(parent.bottom)
                },
            text = filterString,
            color = SettingValueColor
        )
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(plus) {
                    top.linkTo(parent.top)
                    start.linkTo(text.end)
                    bottom.linkTo(parent.bottom)
                },
            onClick = {

            }
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Button", tint = SettingSwitchColor)
        }
    }
}