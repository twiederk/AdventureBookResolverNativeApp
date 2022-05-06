package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager.widget.ViewPager
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.billing.Billing
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.android.ext.android.inject
import java.io.BufferedReader
import java.io.InputStream

class MainActivity : LogActivity() {

    private val toolbarPanel: ToolbarPanel by inject()
    private val game: Game by inject()
    private val billing: Billing by inject()

    private var restartDialog: RestartDialog = RestartDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarPanel.create(findViewById(android.R.id.content))
        setSupportActionBar(toolbarPanel.getToolbar())

        val viewPager = findViewById<ViewPager>(R.id.container)
        viewPager.adapter = SectionsPagerAdapter(supportFragmentManager)

        billing.startConnection(this)
        update()
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

    internal fun update() {
        toolbarPanel.update()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.option_restart -> restart()
            R.id.option_load -> load()
            R.id.option_create -> create()
            else -> super.onOptionsItemSelected(item)
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
    }

    private fun create(): Boolean {
        billing.startBillingFlow(this)
        return true
    }

    fun purchasedBook() {
        game.createBook("purchased book")
        update()
    }

}
