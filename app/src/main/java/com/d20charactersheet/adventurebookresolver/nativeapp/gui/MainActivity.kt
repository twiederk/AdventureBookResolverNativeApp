package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : LogActivity() {

    private val game: Game by inject()
    private val toolbarPanel: ToolbarPanel by inject()

    internal var dialogBuilder: DialogBuilder = DialogBuilder()
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        toolbarPanel.create(findViewById(android.R.id.content))

        setSupportActionBar(toolbarPanel.getToolbar())
        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = sectionsPagerAdapter

        fab.setOnClickListener { game.saveBook() }

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
            else -> super.onOptionsItemSelected(item)
        }

    private fun restart(): Boolean {
        val restartDialog = dialogBuilder.create(this, RestartOnClickListener())
        restartDialog.show()
        return true
    }

    inner class RestartOnClickListener : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface?, which: Int) {
            game.restart()
            sectionsPagerAdapter.getItem(container.currentItem).onResume()
        }

    }
}
