package com.example.hackathon

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.ui.theme.TabItem
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackathonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
    }
}



var valu: List<Float> = listOf(10f,20f,30f)
var dateData: String = "10 - 10 - 2022"
var optNum  = 1

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContent() {


    val list = listOf(TabItem.Calender,TabItem.Analysis,TabItem.Profile)
    val pagerState = rememberPagerState(initialPage = 0)


    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        TabContent(tabs = list, pagerState = pagerState)
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier.height(70.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Black,
        indicator = { tabPositions ->
            Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
        }) {
        tabs.forEachIndexed { index, tabItem ->

            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {Text(tabItem.title) },
                icon = { Icon(imageVector = tabItem.icons,contentDescription = null) },
                selectedContentColor = Color(red = 255, green = 191, blue = 0),
                unselectedContentColor = Color.LightGray,
                enabled = true
            )

        }


    }


}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(tabs:List<TabItem>,pagerState: PagerState) {
    HorizontalPager(count = tabs.size,state=pagerState) { page ->
        tabs[page].screens()

    }
}


@Composable
fun CalendarScreen() {
    val img1 = painterResource(id = R.drawable.bac1)
    var happyval by remember {
        mutableStateOf(value = 0)
    }
    var sadval by remember {
        mutableStateOf(value = 0)
    }
    var Lazyval by remember {
        mutableStateOf(value = 0)
    }
    val day: Int
    val month: Int
    val year: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    var date by remember {
        mutableStateOf(value = "")
    }

    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,{d, year1, month1, day1 ->
            val month = month1 + 1
            date = "$day1 - $month - $year1"
        }, year, month, day
    )
    Box() {
        Image(painter = img1, contentDescription = img1.toString(),modifier= Modifier.fillMaxSize(), contentScale = ContentScale.Crop )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.border(width = 1.dp, color = Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(percent = 20)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 151, green = 169, blue = 246, alpha = 150),
                    contentColor = Color.White),
                onClick = {datePickerDialog.show()}) {
                Text(text = "Select your day!")

            }
            Text(text = "$date", fontSize = 36.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Feel?", fontSize = 30.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                Card(elevation = 10.dp,modifier = Modifier
                    .background(color = Color.Transparent)
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(size = 16.dp))
                    , shape = RoundedCornerShape(size = 16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally
                        ,verticalArrangement = Arrangement.SpaceBetween
                        ,modifier = Modifier
                            .background(color = Color(0, 0, 0, 150))
                            .clip(
                                shape = RoundedCornerShape(size = 16.dp),
                            )) {
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if (happyval < 10)
                                    happyval = happyval + 1 }) {
                            Text(text = "+",fontWeight = FontWeight.ExtraBold, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Happiness:", fontWeight = FontWeight.SemiBold, color = Color.Green)
                        Text(text = "$happyval")
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if(happyval != 0)
                                    happyval = happyval - 1 }) {
                            Text(text = "-",fontWeight = FontWeight.ExtraBold, color = Color.Black, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(30.dp))
                Card(elevation = 10.dp,modifier = Modifier
                    .background(color = Color.Transparent)
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(size = 16.dp))
                    , shape = RoundedCornerShape(size = 16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally
                        ,verticalArrangement = Arrangement.SpaceBetween
                        ,modifier = Modifier
                            .background(color = Color(0, 0, 0, 150))
                            .clip(
                                shape = RoundedCornerShape(size = 16.dp),
                            )) {
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if (sadval < 10)
                                    sadval = sadval + 1 }) {
                            Text(text = "+",fontWeight = FontWeight.ExtraBold, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Sadness:",fontWeight = FontWeight.SemiBold, color = Color.Red)
                        Text(text = "$sadval")
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if(sadval != 0)
                                    sadval = sadval - 1 }) {
                            Text(text = "-",fontWeight = FontWeight.ExtraBold, color = Color.Black,  fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(30.dp))
                Card(elevation = 10.dp,modifier = Modifier
                    .background(color = Color.Transparent)
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(size = 16.dp))
                    , shape = RoundedCornerShape(size = 16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally
                        ,verticalArrangement = Arrangement.SpaceBetween
                        ,modifier = Modifier
                            .background(color = Color(0, 0, 0, 150))
                            .clip(
                                shape = RoundedCornerShape(size = 16.dp),
                            )) {
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if (Lazyval < 10)
                                    Lazyval = Lazyval + 1 }) {
                            Text(text = "+",fontWeight = FontWeight.ExtraBold, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Laziness:", fontWeight = FontWeight.SemiBold)
                        Text(text = "$Lazyval")
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(modifier = Modifier.size(width = 140.dp, height = 30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 2000),
                                contentColor = Color.White),
                            onClick = {
                                if(Lazyval != 0)
                                    Lazyval = Lazyval - 1 }) {
                            Text(text = "-",fontWeight = FontWeight.ExtraBold, color = Color.Black,  fontSize = 18.sp)
                        }
                    }
                }




            }

            Button(modifier = Modifier.size(height = 60.dp, width = 80.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 255, green = 191, blue = 0, alpha = 155),
                    contentColor = Color.White),
                onClick = {
                    valu = listOf<Float>(happyval.toFloat(), sadval.toFloat(), Lazyval.toFloat())
                    dateData  = date.toString()
                    if (optNum < 3)
                        optNum += 1
                    else
                        optNum = 1

                }) {
                Text(text = "PLOT!")
            }

        }
    }

}



@Composable
fun AnalysisScreen() {
    Box() {
        val img4 = painterResource(id = R.drawable.blur)
        Image(painter = img4, contentDescription = img4.toString(), modifier= Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            /* {when(optNum){
                    1 -> GraphCard(points = valu, colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = dateData)
                    2 -> GraphCard(points = valu, colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = dateData)
                    3 -> GraphCard(points = valu, colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = dateData)
                    else -> GraphCard(points = valu, colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = dateData)

                }*/
            GraphCard(points = listOf(25f, 55f, 11f), colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = "23 - 09 - 2022" )
            GraphCard(points = listOf(67f, 34f, 12f), colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = "11 - 03 - 2022" )
            GraphCard(points = listOf(455f, 56f, 10f), colors = listOf(Color.Green, Color.Red, Color.Black), modifier = Modifier.padding(3.dp), date = "13 - 12 - 2022" )
        }
    }

}

@Preview
@Composable
fun GraphCardPreview() {
    GraphCard(points = listOf(25f, 55f, 11f), colors = listOf(Color.Green, Color.Blue, Color.Black), modifier = Modifier.padding(3.dp), date = "23 - 23 - 23" )
}

@Composable
fun GraphCard(modifier: Modifier = Modifier
              ,points: List<Float>
              ,colors: List<Color>
              ,date: String
) {
    val total = points.sum()

    var proportions = points.map {
        it * 100 / total }

    var sweepAnglePercentage = proportions.map {
        360 * it / 100 }

    var startAngel = 270f

    Card(elevation = 20.dp
        , modifier = modifier
            .size(height = 300.dp
                , width = 450.dp)
        , shape = RoundedCornerShape(30)
    ) {
        Column(modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = date, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Canvas(modifier = modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f)
                .background(color = Color.White)) {

                sweepAnglePercentage.forEachIndexed() { index, sweepAngle ->
                    DrawArc(
                        colors[index],
                        startAngel,
                        sweepAngle
                    )
                    startAngel += sweepAngle
                }
            }



        }

    }
}

fun DrawScope.DrawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(width = size.width, height = size.height),
        style = Stroke(
            width = 100f
        )
    )
}



@Composable
fun ProfileScreen() {
    val img2 = painterResource(id = R.drawable.profileblur)
    val img3 = painterResource(id = R.drawable.pic)
    Box() {
        Image(painter = img2, contentDescription = img2.toString(), modifier = Modifier
            .fillMaxSize(),
            contentScale = ContentScale.Crop)
        Image(painter = img3, contentDescription = "Profile Picture", modifier = Modifier
            .padding(top = 25.dp)
            .align(Alignment.TopCenter)
            .clip(shape = RoundedCornerShape(50)),
            contentScale = ContentScale.Fit)

        Column(modifier = Modifier.align(Alignment.BottomCenter)
            ,verticalArrangement = Arrangement.SpaceEvenly
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Alex Jones", fontWeight = FontWeight.Bold, fontSize = 40.sp, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Membership", fontWeight = FontWeight.SemiBold, fontSize = 25.sp, color = Color.White)
            Text(text = "Premium", fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Mobile", fontWeight = FontWeight.SemiBold, fontSize = 25.sp, color = Color.White)
            Text(text = "+91 903959789", fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "EMail", fontWeight = FontWeight.SemiBold, fontSize = 25.sp, color = Color.White)
            Text(text = "alexjones49@gmail.com", fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(30.dp))

        }
    }

}
