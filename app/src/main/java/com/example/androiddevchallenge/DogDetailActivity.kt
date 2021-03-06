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

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.ui.theme.MyTheme

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DogDetailKey = "DogDetailKey"
    }

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent.getParcelableExtra<Dog>(DogDetailKey)
        setContent {
            MyTheme {
                Column {
                    TopAppBar(
                        title = { Text(dog?.name ?: "详情") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    finish()
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.ArrowBack, "返回")
                            }
                        },
                    )
                    MyDogDetail(dog)
                }
            }
        }
    }

    @Composable
    fun MyDogDetail(dog: Dog?) {
        val openDialog = remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ApplyDialog(openDialog)
            DogDetail(dog) {
                openDialog.value = true
            }
        }
    }

    @Composable
    fun DogDetail(dog: Dog?, click: () -> Unit) {
        Box {
            Column(modifier = Modifier.padding(16.dp)) {

                if (dog == null) {
                    Text(text = "数据为空")
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F),
                        painter = painterResource(id = dog.head),
                        contentDescription = "狗的头像",
                        contentScale = ContentScale.Crop,
                    )

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

            FloatingActionButton(
                onClick = click,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                Text(text = "申请", color = colorResource(id = R.color.white))
            }
        }
    }

    @Composable
    fun ApplyDialog(openDialog: MutableState<Boolean>) {

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            Toast.makeText(context, "已经为您申请", Toast.LENGTH_SHORT).show()
                        },
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("取消")
                    }
                },
                text = {
                    Text("您确定要申请它吗")
                },
            )
        }
    }

    @Preview("Light Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun LightPreview() {
        MyTheme {
            MyDogDetail(
                Dog(
                    head = R.drawable.dog_one,
                    name = "AA",
                    kind = "萨摩",
                    age = "1岁"
                )
            )
        }
    }

    @Preview("Dark Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun DarkPreview() {
        MyTheme(darkTheme = true) {
            MyDogDetail(
                Dog(
                    head = R.drawable.dog_one,
                    name = "AA",
                    kind = "萨摩",
                    age = "1岁"
                )
            )
        }
    }
}
