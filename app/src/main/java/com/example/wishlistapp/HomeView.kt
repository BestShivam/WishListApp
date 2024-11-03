package com.example.wishlistapp


import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){

    val context = LocalContext.current
    Scaffold (modifier = Modifier.statusBarsPadding(),
        topBar = { AppBarView(title = "Wish List",
            onBackNavClicked = { Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()})},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(context, "FAB clicked", Toast.LENGTH_SHORT).show()
                /*TODO Navigate to Add list Screen*/
                navController.navigate(Screen.Add.route+"/0L")
                                           },
                contentColor = Color.White,
                backgroundColor = Color.Black,
                modifier = Modifier.padding(20.dp)
                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

    ){
        val wishList = viewModel.getAllWish.collectAsState(initial = listOf())
       LazyColumn(modifier = Modifier.padding(it)) {
           items(wishList.value, key= {wish -> wish.id}){wish ->

               val dismissState = rememberDismissState(
                   confirmStateChange = {
                       if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                           viewModel.deleteWish(wish)
                       }
                       true
                   }
               )

               SwipeToDismiss(state = dismissState,
                   background = {
                       val color by animateColorAsState(targetValue =
                       if(dismissState.dismissDirection == DismissDirection.EndToStart)
                       Color.Red
                       else Color.Transparent,
                           label = "")
                       Box(modifier = Modifier
                           .fillMaxWidth()
                           .background(color)
                           .padding(20.dp),
                           contentAlignment = Alignment.CenterEnd
                       ){
                           Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",
                               tint = Color.White)
                       }
                   },
                   directions = setOf(DismissDirection.EndToStart),
                   dismissThresholds = {FractionalThreshold(0.5f)},
                   dismissContent = {
                       WishItem(wish = wish) {
                           val id = wish.id
                           navController.navigate(Screen.Add.route+"/$id")

                       }
                   }
               )

           }
       }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishItem(wish : Wish,onClick :()->Unit){
  Card(onClick = onClick,
      backgroundColor = Color.White,
      elevation = 10.dp,
      modifier = Modifier
          .padding(start = 8.dp, end = 8.dp, top = 8.dp)
          .fillMaxSize()) {
      Column (modifier = Modifier.padding(16.dp)){
          Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
          Text(text = wish.description)
      }

  }
}


