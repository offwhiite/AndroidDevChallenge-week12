package com.kuromame.composepathway

import android.net.Uri.encode
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.kuromame.composepathway.model.AdoptedChild
import com.kuromame.composepathway.model.FosterParent

class MainActivity : AppCompatActivity() {

    private val testData = listOf(
        AdoptedChild(
            name = "Taro",
            image = R.drawable.photo_1,
            old = "5",
            description = "She needs some work, but she's a very nice girl!",
            descriptionDetail = "She is a very calm and sweet girl.\n" +
                    "When we first took him in, his tail was down because he was nervous, but he soon got used to it and came to us with his tail wagging.",
            fosterParent = FosterParent(
                name = "田中太郎",
                image = R.drawable.biyou_tarumi_man
            )
        ),
        AdoptedChild(
            name = "Jiro",
            image = R.drawable.photo_2,
            old = "5",
            description = "I'm looking for a foster home.",
            descriptionDetail = "She is a very calm and sweet girl.\n" +
                    "When we first took him in, his tail was down because he was nervous, but he soon got used to it and came to us with his tail wagging.",

            fosterParent = FosterParent(
                name = "田中太郎",
                image = R.drawable.biyou_tarumi_man
            )
        ),
        AdoptedChild(
            name = "saburo",
            image = R.drawable.photo_3,
            old = "5",
            description = "手がかかりますが、いいこです",
            descriptionDetail = "She is a very calm and sweet girl.\n" +
                    "When we first took him in, his tail was down because he was nervous, but he soon got used to it and came to us with his tail wagging.",

            fosterParent = FosterParent(
                name = "田中太郎",
                image = R.drawable.biyou_tarumi_man
            )
        ),
        AdoptedChild(
            name = "shiro",
            image = R.drawable.photo_4,
            old = "5",
            description = "手がかかりますが、いいこです",
            descriptionDetail = "She is a very calm and sweet girl.\n" +
                    "When we first took him in, his tail was down because he was nervous, but he soon got used to it and came to us with his tail wagging.",

            fosterParent = FosterParent(
                name = "田中太郎",
                image = R.drawable.biyou_tarumi_man
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(testData = testData)
        }
    }
}

@Composable
fun MainView(testData: List<AdoptedChild>) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "adaptedChildList") {
        composable("adaptedChildList") {
            AdoptedChildCard(testData, navHostController = navController)
        }
        composable(
            "adaptedChildDetail/{name}/{old}/{descriptionDetail}/{image}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("old") { type = NavType.StringType },
                navArgument("image") { type = NavType.IntType },
                navArgument("descriptionDetail") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            AdoptedChildDetail(
                name = backStackEntry.arguments?.getString("name") ?: "NoName",
                old = backStackEntry.arguments?.getString("old") ?: "unspecified",
                image = backStackEntry.arguments?.getInt("image") ?: R.drawable.dog_akitainu,
                descriptionDetail = backStackEntry.arguments?.getString("descriptionDetail")
                    ?: "unspecified",
            )
        }
    }
}

@Composable
fun AdoptedChildCard(
    adoptedChild: List<AdoptedChild>,
    navHostController: NavHostController
) {

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        adoptedChild.forEach {
            Card(elevation = 8.dp, modifier = Modifier.padding(16.dp)) {
                Column(
                    Modifier
                        .clickable(onClick = {
                            navHostController.navigate(
                                "adaptedChildDetail/${it.name}/${it.old}/${encode(it.descriptionDetail)}/${it.image}"
                            )
                        })
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val painter = painterResource(id = it.fosterParent.image)
                        val imageModifier = Modifier
                            .size(32.dp, 32.dp)
                            .clip(shape = RoundedCornerShape(100.dp))
                        Image(painter = painter, contentDescription = "", modifier = imageModifier)
                        Text(adoptedChild[0].description)
                    }

                    val dogPainter = painterResource(id = it.image)
                    Image(
                        painter = dogPainter, contentDescription = "", modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.size(0.dp, 16.dp))
        }
    }
}

@Composable
fun AdoptedChildDetail(
    name: String,
    image: Int,
    old: String,
    descriptionDetail: String
) {
    val typography = MaterialTheme.typography

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        val dogPainter = painterResource(id = image)
        Image(
            painter = dogPainter, contentDescription = "", modifier = Modifier
                .fillMaxWidth(), contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(0.dp, 16.dp))

        Text(text = name, style = typography.h1)
        Text(text = "$old years old", style = typography.h2)
        Text(text = descriptionDetail, style = typography.body1)
    }
}

@Preview
@Composable
fun PreviewAdoptedChildCard() {
    AdoptedChildCard(
        adoptedChild =
        listOf(
            AdoptedChild(
                name = "Taro",
                image = R.drawable.dog_akitainu,
                old = "5",
                description = "いいこです",
                descriptionDetail = "She is a very calm and sweet girl.\n" +
                        "When we first took him in, his tail was down because he was nervous, but he soon got used to it and came to us with his tail wagging.",

                fosterParent = FosterParent(
                    name = "田中太郎",
                    image = R.drawable.biyou_tarumi_man
                )
            )
        ),
        navHostController = rememberNavController()
    )
}