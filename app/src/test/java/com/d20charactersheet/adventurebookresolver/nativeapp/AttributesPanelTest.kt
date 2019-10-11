package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.Attributes
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions
import org.junit.Test

class AttributesPanelTest {

    @Test
    fun `create attributes panel`() {
        // Arrange
        val attributes = Attributes(
            strength = Attribute(AttributeName.STRENGTH, 14, 20),
            dexterity = Attribute(AttributeName.DEXTERITY, 8, 9),
            luck = Attribute(AttributeName.LUCK, 5, 10)
        )

        val strengthValueTextView = mock<TextView>()
        val strengthPlusButton = mock<Button>()
        val strengthMinusButton = mock<Button>()
        val dexterityValueTextView = mock<TextView>()
        val dexterityPlusButton = mock<Button>()
        val dexterityMinusButton = mock<Button>()
        val luckValueTextView = mock<TextView>()
        val luckPlusButton = mock<Button>()
        val luckMinusButton = mock<Button>()

        val rootView = mock<View> {
            on { findViewById<TextView>(R.id.strength_value_text_view) } doReturn strengthValueTextView
            on { findViewById<TextView>(R.id.strength_plus_button) } doReturn strengthPlusButton
            on { findViewById<TextView>(R.id.strength_minus_button) } doReturn strengthMinusButton
            on { findViewById<TextView>(R.id.dexterity_value_text_view) } doReturn dexterityValueTextView
            on { findViewById<TextView>(R.id.dexterity_plus_button) } doReturn dexterityPlusButton
            on { findViewById<TextView>(R.id.dexterity_minus_button) } doReturn dexterityMinusButton
            on { findViewById<TextView>(R.id.luck_value_text_view) } doReturn luckValueTextView
            on { findViewById<TextView>(R.id.luck_plus_button) } doReturn luckPlusButton
            on { findViewById<TextView>(R.id.luck_minus_button) } doReturn luckMinusButton
        }

        val game = Game(AdventureBook(attributes = attributes))

        // Act
        AttributesPanel(game).create(rootView)

        // Assert
        verify(strengthValueTextView).text = "14 / 20"
        argumentCaptor<AttributeOnClickListener> {
            verify(strengthPlusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }
        argumentCaptor<AttributeOnClickListener> {
            verify(strengthMinusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }

        verify(dexterityValueTextView).text = "8 / 9"
        argumentCaptor<AttributeOnClickListener> {
            verify(dexterityPlusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }
        argumentCaptor<AttributeOnClickListener> {
            verify(dexterityMinusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }

        verify(luckValueTextView).text = "5 / 10"
        argumentCaptor<AttributeOnClickListener> {
            verify(luckPlusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }
        argumentCaptor<AttributeOnClickListener> {
            verify(luckMinusButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(AttributeOnClickListener::class.java)
        }
    }

    @Test
    fun `change attribute onClick`() {
        // Arrange
        val game = mock<Game>()
        val attributesPanel = mock<AttributesPanel>()
        val underTest = AttributeOnClickListener(attributesPanel, game, AttributeName.STRENGTH, 1)

        // Act
        underTest.onClick(mock())

        // Assert
        verify(game).changeAttribute(AttributeName.STRENGTH, 1)
        verify(attributesPanel).update()
    }

}

