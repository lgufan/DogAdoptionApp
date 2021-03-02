/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Column {
                    TopAppBar(
                        title = { Text("全部") },
                    )
                    MyApp(dogList, ::onItemClick)
                }
            }
        }
    }

    private fun onItemClick(dog: Dog) {
        startActivity(
            Intent().apply {
                putExtra(DogDetailActivity.DogDetailKey, dog)
                setClass(this@MainActivity, DogDetailActivity::class.java)
            }
        )
    }

}

val dogList = listOf(
    Dog(
        head = R.drawable.dog_one,
        name = "二傻",
        kind = "哈士奇",
        age = "1岁"
    ), Dog(
        head = R.drawable.dog_two,
        name = "大傻",
        kind = "阿拉斯加犬",
        age = "2岁"
    ), Dog(
        head = R.drawable.dog_three,
        name = "三傻",
        kind = "萨摩耶",
        age = "3岁"
    )
)

// Start building your app here!
@Composable
fun MyApp(
    dogList: List<Dog>,
    onItemClick: (Dog) -> Unit
) {


//    val image = imageResource(R.drawable.header)

    Surface(color = MaterialTheme.colors.background) {

        if (dogList.isNullOrEmpty()) {
            Text(text = "没有数据")
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(dogList) { dog ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(dog)
                            }) {
                            Image(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape),
                                painter = painterResource(id = dog.head),
                                contentScale = ContentScale.Crop,
                                contentDescription = "狗的头像"
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = dog.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "种类：${dog.kind}")
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "年纪：${dog.age}")
                            }
                        }
                    }
                })
        }

    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(dogList) {}
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(dogList) {}
    }
}
