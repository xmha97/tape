package ir.ammari.tape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.ui.NavDisplay
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

import ir.ammari.tape.ui.theme.TapeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TapeTheme {

                val context = LocalContext.current

                var openDialog by rememberSaveable { mutableStateOf(false) }
                var text by rememberSaveable { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                openDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }
                    }
                ) { innerPadding ->

                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )

                    if (openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = {
                                Text("Enter a playlist name")
                            },
                            text = {
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = text,
                                    onValueChange = {
                                        text = it
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    )
                                )
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        openDialog = false

                                        Toast.makeText(
                                            context,
                                            text,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Text("Confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        openDialog = false
                                    }
                                ) {
                                    Text("Dismiss")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreen(
                    onOpenSettings = {
                        backStack += Settings
                    }
                )
            }
            entry<Settings> {
                SettingsScreen()
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TapeTheme {
        MainScreen()
    }
}
