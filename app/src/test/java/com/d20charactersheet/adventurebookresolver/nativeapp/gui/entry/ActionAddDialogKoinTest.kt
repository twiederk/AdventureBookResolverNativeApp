package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.text.Editable
import android.view.View
import android.widget.EditText
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class ActionAddDialogKoinTest : KoinTest {

    private val game: Game by inject()
    private val actionPanel: ActionPanel by inject()
    private val graphPanel: GraphPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<ActionPanel>()
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

        // Act
        ActionAddDialog().addAction(view)

        // Assert
        verifyNoMoreInteractions(game, actionPanel, graphPanel)
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

        // Act
        ActionAddDialog().addAction(view)

        // Assert
        verifyNoMoreInteractions(game, actionPanel, graphPanel)
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

        // Act
        ActionAddDialog().addAction(view)

        // Assert
        verify(game).addAction("myActionLabel", 10)
        verify(actionPanel).update()
        verify(graphPanel).update()
    }

}