package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EntryDialogKoinTest : KoinTest {

    private val game: Game by inject()
    private val graphPanel: GraphPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<GraphPanel>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onCreate_createDialog_fillViewsWithDataFromGame() {
        // arrange
        val view: View = mock()
        val entryIdTextView: TextView = mock()
        val entryTitleEditText: EditText = mock()
        val entryNoteEditText: EditText = mock()
        val actionMoveRecyclerView: RecyclerView = mock()
        whenever(view.findViewById<EditText>(R.id.entry_title_edit_text)).doReturn(entryTitleEditText)
        whenever(view.findViewById<TextView>(R.id.entry_id_text_view)).doReturn(entryIdTextView)
        whenever(view.findViewById<EditText>(R.id.entry_note_edit_text)).doReturn(entryNoteEditText)
        whenever(view.findViewById<RecyclerView>(R.id.action_move_recycler_view)).doReturn(actionMoveRecyclerView)

        val book = AdventureBook()
        book.setEntryTitle("myFirstEntry")
        book.setEntryNote("myFirstNote")
        whenever(game.book).doReturn(book)
        val underTest = EntryDialog()
        underTest.itemTouchHelper = mock()

        // act
        underTest.onCreate(view)

        // assert
        verify(entryTitleEditText).setText("myFirstEntry")
        verify(entryIdTextView).text = "(1)"
        verify(entryNoteEditText).setText("myFirstNote")
    }

    @Test
    fun updateEntry_pressedOkButton_updateCurrentEntry() {
        // arrange
        val underTest = EntryDialog()
        underTest.entryTitleEditText = mock()
        val titleEditable: Editable = mock { on { toString() } doReturn "myTitle" }
        whenever(underTest.entryTitleEditText.text).doReturn(titleEditable)

        underTest.entryNoteEditText = mock()
        val noteEditable: Editable = mock { on { toString() } doReturn "myNote" }
        whenever(underTest.entryNoteEditText.text).doReturn(noteEditable)

        val book = AdventureBook()
        whenever(game.book).doReturn(book)

        // act
        underTest.updateEntry()

        // assert
        assertThat(book.getEntryTitle()).isEqualTo("myTitle")
        assertThat(book.getEntryNote()).isEqualTo("myNote")
        verify(graphPanel).update()
    }

}