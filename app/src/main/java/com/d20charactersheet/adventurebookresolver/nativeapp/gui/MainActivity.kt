package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.SetupNavGraph
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.koin.android.ext.android.inject
import java.io.BufferedReader
import java.io.InputStream

class MainActivity : LogActivity() {

    private val game: Game by inject()
    private val createBookScreenViewModel: CreateBookScreenViewModel by inject()
    private val solutionScreenViewModel: SolutionScreenViewModel by inject()
    private val bookViewModel: BookViewModel by inject()
    private val graphViewModel: GraphViewModel by inject()
    private val createActionScreenViewModel: CreateActionScreenViewModel by inject()
    private val entryScreenViewModel: EntryScreenViewModel by inject()
    private val inventoryScreenViewModel: InventoryScreenViewModel by inject()

    private var restartDialog: RestartDialog = RestartDialog()

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdventureBookResolverTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    bookViewModel = bookViewModel,
                    graphViewModel = graphViewModel,
                    load = { load() },
                    restart = { restart() },
                    createBookScreenViewModel = createBookScreenViewModel,
                    solutionScreenViewModel = solutionScreenViewModel,
                    createActionScreenViewModel = createActionScreenViewModel,
                    entryScreenViewModel = entryScreenViewModel,
                    inventoryScreenViewModel = inventoryScreenViewModel,
                )
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 100
            )
        }

    }

    private fun restart(): Boolean {
        restartDialog.create(this).show()
        return true
    }

    private fun load(): Boolean {
        launchIntentForOpenDocumentActivity()
        return true
    }

    private fun launchIntentForOpenDocumentActivity() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openDocumentActivityResultLauncher.launch(intent)
    }

    private val openDocumentActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                loadBookFromIntent(result)
            }
        }

    private fun loadBookFromIntent(result: ActivityResult) {
        val intent = checkNotNull(result.data)
        val uri = checkNotNull(intent.data)
        val inputStream: InputStream = checkNotNull(contentResolver.openInputStream(uri))
        val content: List<String> = inputStream.bufferedReader().use(BufferedReader::readLines)
        game.book = BookStore().import(content)
        solutionScreenViewModel.clear()
        inventoryScreenViewModel.reset()
    }

//    private fun exportImage() {
//        try {
//            val graphView = findViewById<GraphView>(R.id.graph_view)
//            GraphBitmap().createAndSaveBitmap(graphView)
//            Toast.makeText(this, "Exported graph.", Toast.LENGTH_LONG).show()
//        } catch (exception: Exception) {
//            Toast.makeText(
//                this,
//                "Graph is to large to export. Reduce zoom and try to export again.",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

}
