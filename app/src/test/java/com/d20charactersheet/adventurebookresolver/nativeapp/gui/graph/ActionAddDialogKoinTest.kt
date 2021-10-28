package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.text.Editable
import android.view.View
import android.widget.EditText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.*

class ActionAddDialogKoinTest : KoinTest {

    private val game: Game by inject()
    private val graphPanel: GraphPanel by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

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
    fun `if label is empty do not add an action`() {
        // Arrange
        val actionLabelEditable: Editable = mock { on { toString() } doReturn "" }
        val actionLabelEditText: EditText = mock { on { text } doReturn actionLabelEditable }

        val actionIdEditable: Editable = mock { on { toString() } doReturn "10" }
        val actionIdEditText: EditText = mock { on { text } doReturn actionIdEditable }

        val view: View = mock {
            on { findViewById<EditText>(R.id.action_label_edit_text) } doReturn actionLabelEditText
            on { findViewById<EditText>(R.id.action_id_edit_text) } doReturn actionIdEditText
        }

        val messageDisplay: MessageDisplay = mock()

        // Act
        ActionAddDialog(messageDisplay).addAction(view)

        // Assert
        verify(messageDisplay).display(view, "Can't create action: Label is missing")
        verifyNoMoreInteractions(game, graphPanel)
    }

    @Test
    fun `if id is empty do not add an action`() {
        // Arrange
        val actionLabelEditable: Editable = mock { on { toString() } doReturn "myActionLabel" }
        val actionLabelEditText: EditText = mock { on { text } doReturn actionLabelEditable }

        val actionIdEditable: Editable = mock { on { toString() } doReturn "" }
        val actionIdEditText: EditText = mock { on { text } doReturn actionIdEditable }

        val view: View = mock {
            on { findViewById<EditText>(R.id.action_label_edit_text) } doReturn actionLabelEditText
            on { findViewById<EditText>(R.id.action_id_edit_text) } doReturn actionIdEditText
        }

        val messageDisplay: MessageDisplay = mock()

        // Act
        ActionAddDialog(messageDisplay).addAction(view)

        // Assert
        verify(messageDisplay).display(view, "Can't create action: Id is missing")
        verifyNoMoreInteractions(game, graphPanel)
    }

    @Test
    fun `if id is same as current entry do not add an action`() {
        // Arrange
        val actionLabelEditable: Editable = mock { on { toString() } doReturn "myActionLabel" }
        val actionLabelEditText: EditText = mock { on { text } doReturn actionLabelEditable }

        val actionIdEditable: Editable = mock { on { toString() } doReturn "1" }
        val actionIdEditText: EditText = mock { on { text } doReturn actionIdEditable }

        val view: View = mock {
            on { findViewById<EditText>(R.id.action_label_edit_text) } doReturn actionLabelEditText
            on { findViewById<EditText>(R.id.action_id_edit_text) } doReturn actionIdEditText
        }
        whenever(game.book).doReturn(AdventureBook())

        val messageDisplay: MessageDisplay = mock()

        // Act
        ActionAddDialog(messageDisplay).addAction(view)

        // Assert
        verify(game, times(2)).book
        verify(messageDisplay).display(view, "Can't create action: Id is same as current node")
        verifyNoMoreInteractions(game, graphPanel)
    }

    @Test
    fun `add action`() {
        // Arrange
        val actionLabelEditable: Editable = mock { on { toString() } doReturn "myActionLabel" }
        val actionLabelEditText: EditText = mock { on { text } doReturn actionLabelEditable }

        val actionIdEditable: Editable = mock { on { toString() } doReturn "10" }
        val actionIdEditText: EditText = mock { on { text } doReturn actionIdEditable }

        val view: View = mock {
            on { findViewById<EditText>(R.id.action_label_edit_text) } doReturn actionLabelEditText
            on { findViewById<EditText>(R.id.action_id_edit_text) } doReturn actionIdEditText
        }
        whenever(game.book).doReturn(AdventureBook())

        // Act
        ActionAddDialog().addAction(view)

        // Assert
        verify(game).addAction("myActionLabel", 10)
        verify(graphPanel).update()
    }

}