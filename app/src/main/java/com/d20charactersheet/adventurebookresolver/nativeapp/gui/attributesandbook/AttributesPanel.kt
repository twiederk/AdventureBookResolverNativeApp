package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AttributesPanel(private val game: Game) :
    Panel {

    internal lateinit var strengthValueTextView: TextView
    internal lateinit var dexterityValueTextView: TextView
    internal lateinit var luckValueTextView: TextView

    override fun create(rootView: View) {
        setupStrengthAttribute(rootView)
        setupDexterityAttribute(rootView)
        setupLuckAttribute(rootView)
    }

    private fun setupStrengthAttribute(rootView: View) {
        strengthValueTextView = rootView.findViewById(R.id.strength_value_text_view)
        setOnClickListenersToAttributeButtons(
            rootView,
            R.id.strength_plus_button,
            R.id.strength_minus_button,
            AttributeName.STRENGTH
        )
    }

    private fun setupDexterityAttribute(rootView: View) {
        dexterityValueTextView = rootView.findViewById(R.id.dexterity_value_text_view)
        setOnClickListenersToAttributeButtons(
            rootView,
            R.id.dexterity_plus_button,
            R.id.dexterity_minus_button,
            AttributeName.DEXTERITY
        )
    }

    private fun setupLuckAttribute(rootView: View) {
        luckValueTextView = rootView.findViewById(R.id.luck_value_text_view)
        setOnClickListenersToAttributeButtons(
            rootView,
            R.id.luck_plus_button,
            R.id.luck_minus_button,
            AttributeName.LUCK
        )
    }

    private fun setOnClickListenersToAttributeButtons(
        rootView: View,
        plusButtonResourceId: Int,
        minusButtonResourceId: Int,
        attributeName: AttributeName
    ) {
        rootView.findViewById<Button>(plusButtonResourceId)
            .setOnClickListener(
                AttributeIncreaseOnClickListener(
                    attributeName
                )
            )
        rootView.findViewById<Button>(minusButtonResourceId)
            .setOnClickListener(
                AttributeDecreaseOnClickListener(
                    attributeName
                )
            )
    }

    override fun update() {
        updateAttributeValueTextView(strengthValueTextView, game.book.attributes.strength)
        updateAttributeValueTextView(dexterityValueTextView, game.book.attributes.dexterity)
        updateAttributeValueTextView(luckValueTextView, game.book.attributes.luck)
    }

    private fun updateAttributeValueTextView(attributeValueTextView: TextView, attribute: Attribute) {
        attributeValueTextView.text = displayAttributeValue(attribute)
    }

    private fun displayAttributeValue(attribute: Attribute) = "${attribute.value} / ${attribute.maxValue}"

}

abstract class AttributeOnClickListener(
    protected val attributeName: AttributeName
) : View.OnClickListener, KoinComponent {

    protected val attributesPanel: AttributesPanel by inject()
    protected val game: Game by inject()

    abstract override fun onClick(v: View?)
}

class AttributeIncreaseOnClickListener(attributeName: AttributeName) : AttributeOnClickListener(attributeName) {

    override fun onClick(v: View?) {
        game.increaseAttribute(attributeName, 1)
        attributesPanel.update()
    }

}

class AttributeDecreaseOnClickListener(attributeName: AttributeName) : AttributeOnClickListener(attributeName) {

    override fun onClick(v: View?) {
        game.decreaseAttribute(attributeName, 1)
        attributesPanel.update()
    }

}
