package com.ruben.composition.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ruben.composition.R
import com.ruben.composition.components.BackButtonAppBar
import com.ruben.composition.data.MockData
import com.ruben.composition.model.LiveStreamerEntity
import com.ruben.composition.ui.theme.Pink
import com.ruben.composition.ui.theme.Teal200

/**
 * Created by Ruben Quadros on 10/08/21
 **/
@Composable
fun LiveNowCarousel(navigateBack: () -> Unit) {

    val isShowCarousel = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { BackButtonAppBar(title = stringResource(id = R.string.all_following), navigateBack = navigateBack) },
        content = {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (background, toolTip) = createRefs()
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Background Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(background) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
                if (isShowCarousel.value) {
                    ShowLiveStreamers()
                } else {
                    Row(modifier = Modifier
                        .background(color = Teal200, shape = RoundedCornerShape(10.dp))
                        .clickable {
                            isShowCarousel.value = true
                        }
                        .constrainAs(toolTip) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp, start = 8.dp, bottom = 8.dp)
                                .align(Alignment.CenterVertically),
                            text = stringResource(id = R.string.live_streams_number),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                        Image(
                            modifier = Modifier.padding(top = 11.dp, end = 8.dp, bottom = 8.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Down Arrow",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ShowLiveStreamers() {
    LazyRow(modifier = Modifier.background(Color.Black)) {
        items(MockData.getMockLiveStreamers()) { liveStreamerEntity ->
            LiveStreamer(liveStreamerEntity)
        }
    }
}

@Composable
fun LiveStreamer(liveStreamerEntity: LiveStreamerEntity) {
    Column(modifier = Modifier.background(Color.Black)
        .padding(vertical = 8.dp)) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            val (profilePicRing, profilePic, liveText) = createRefs()
            Box(modifier = Modifier
                .clip(CircleShape)
                .border(1.5.dp, Pink, CircleShape)
                .size(66.dp)
                .constrainAs(profilePicRing) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)
                    .constrainAs(profilePic) {
                        top.linkTo(profilePicRing.top, margin = 5.dp)
                        start.linkTo(profilePicRing.start, margin = 5.dp)
                        end.linkTo(profilePicRing.end, margin = 5.dp)
                        bottom.linkTo(profilePicRing.bottom, margin = 5.dp)
                    }
            )
            Box(modifier = Modifier
                .background(Pink)
                .border(1.dp, Color.Black)
                .constrainAs(liveText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {
                Text(
                    text = stringResource(id = R.string.all_live),
                    color = Color.White,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
            }
        }
        Text(
            modifier = Modifier.width(78.dp),
            text = liveStreamerEntity.handle,
            color = Color.White,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LiceCarouselPreview() {
    LiveNowCarousel {

    }
}

@Preview(showBackground = true)
@Composable
fun LiveStreamerPreview() {
    LiveStreamer(liveStreamerEntity = LiveStreamerEntity("@ruben", ""))
}