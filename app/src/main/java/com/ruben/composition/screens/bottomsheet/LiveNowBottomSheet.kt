package com.ruben.composition.screens.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruben.composition.R
import kotlinx.coroutines.launch

@Preview
@ExperimentalMaterialApi
@Composable
fun LiveNowBottomSheet() {
    MaterialTheme {

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
                sheetContent = {
                    Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)) {
                        Box(modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Live Now", color = Color.White, fontSize = 20.sp)
                        }

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                            items(100) {
                                LiveUserEntry()
                            }
                        }
                    }
                },
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                sheetBackgroundColor = Color.Black,

                ) {
            Box(Modifier.fillMaxSize()) {
                Button(onClick = {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        scope.launch { scaffoldState.bottomSheetState.expand() }
                    } else {
                        scope.launch { scaffoldState.bottomSheetState.collapse() }
                    }
                }, modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)) {
                    Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Show")
                }

            }

        }
    }

}

@Preview
@Composable
private fun LiveUserEntry() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { }) {
        Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = colors.onSurface.copy(alpha = 0.2f)

        ) {
            Image(painter = painterResource(id = R.drawable.profile), contentDescription = "")
        }
        Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1.0f)) {
            Text("Full Name", color = Color(0xFFD7D7D8))
            Text("@handle", color = Color(0xFF8A8A8F))
            Text("Role", color = Color(0xFF8A8A8F))
        }
        Button(onClick = { }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color(0xFF131319)
        )) {
            Text(text = "Remove", color = Color(0xFFD7D7D8))
        }
    }
}