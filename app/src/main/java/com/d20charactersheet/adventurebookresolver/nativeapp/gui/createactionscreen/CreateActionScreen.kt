package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun CreateActionScreen(
    createActionScreenViewModel: CreateActionScreenViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CreateActionScreenAppBar(
                onBackClick = {
                    createActionScreenViewModel.reset()
                    navController.popBackStack()
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = createActionScreenViewModel.actionLabel,
                        modifier = Modifier.weight(4F),
                        onValueChange = { createActionScreenViewModel.onActionLabelChange(it) },
                        label = { Text("Action") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

                    )
                    OutlinedTextField(
                        value = createActionScreenViewModel.entryId,
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 8.dp),
                        onValueChange = { createActionScreenViewModel.onEntryIdChange(it) },
                        label = { Text("Id") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (createActionScreenViewModel.onCreateClick()) {
                                navController.popBackStack()
                            }
                        }),
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (createActionScreenViewModel.onCreateClick()) {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Create")
                }
                if (createActionScreenViewModel.errorMessage.isNotEmpty()) {
                    Text(text = createActionScreenViewModel.errorMessage)
                }
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateActionScreenPreview() {
    val createActionScreenViewModel = CreateActionScreenViewModel(Game())
    createActionScreenViewModel.onActionLabelChange("myAction")
    createActionScreenViewModel.onEntryIdChange("10")

    AdventureBookResolverTheme {
        CreateActionScreen(
            createActionScreenViewModel,
            navController = rememberNavController()
        )
    }
}
