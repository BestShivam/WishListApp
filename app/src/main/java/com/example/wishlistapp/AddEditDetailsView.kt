package com.example.wishlistapp

import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch


@Composable
fun WishTextField( label: String,value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = colorResource(id = R.color.purple_500),
            unfocusedLabelColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black)
        )
    )
}

@Composable
fun AddEditDetailsView (
    id : Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snakeMesssage = remember{ mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    if (id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState = wish.value.title.trim()
        viewModel.wishDescriptionState = wish.value.description.trim()
    }else{
        viewModel.wishTitleState =""
        viewModel.wishDescriptionState=""
    }

    Scaffold(
        topBar = {
            AppBarView(
                title =
                if (id != 0L) "Update Wish" else "Add Wish",
                {navController.navigateUp()}
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState) {
                viewModel.onTitleChanges(it)
            }

            WishTextField(label = "Description", value = viewModel.wishDescriptionState) {
                viewModel.onDescriptionChages(it)
            }


            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                    if(id != 0L){
                        // TODO Update Wish
                        viewModel.updateWish(
                            wish = Wish(id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim())
                        )
                    }else{
                        //  Add Wish
                        viewModel.addAWish(
                            Wish(
                                title = viewModel.wishTitleState,
                                description = viewModel.wishDescriptionState
                            )
                        )
                        snakeMesssage.value = "Wish is created!"
                    }


                }
                else{
                    snakeMesssage.value = "Enter field to create a wish "
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snakeMesssage.value)
                    navController.navigateUp()
                }
            }) {
                Text(
                    text = if (id != 0L) "update Wish" else "Add Wish",
                    style = TextStyle(
                        fontSize = 10.sp
                    )
                )

            }
        }
    }



}

