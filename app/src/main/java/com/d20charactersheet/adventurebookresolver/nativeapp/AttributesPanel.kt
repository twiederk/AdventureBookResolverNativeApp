package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName

class AttributesPanel : Panel {

    private lateinit var strengthValueTextView: TextView
    private lateinit var dexterityValueTextView: TextView
    private lateinit var luckValueTextView: TextView

    override fun create(rootView: View) {
        setupStrengthAttribute(rootView)
        setupDexterityAttribute(rootView)
        setupLuckAttribute(rootView)
        update()
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
            .setOnClickListener(AttributeOnClickListener(this, MainActivity.game, attributeName, 1))
        rootView.findViewById<Button>(minusButtonResourceId)
            .setOnClickListener(AttributeOnClickListener(this, MainActivity.game, attributeName, -1))
    }

    override fun update() {
        updateAttributeValueTextView(strengthValueTextView, MainActivity.game.book.attributes.strength)
        updateAttributeValueTextView(dexterityValueTextView, MainActivity.game.book.attributes.dexterity)
        updateAttributeValueTextView(luckValueTextView, MainActivity.game.book.attributes.luck)
    }

    private fun updateAttributeValueTextView(attributeValueTextView: TextView, attribute: Attribute) {
        attributeValueTextView.text = displayAttributeValue(attribute)
    }

    private fun displayAttributeValue(attribute: Attribute) = "${attribute.value} / ${attribute.maxValue}"

}

class AttributeOnClickListener(
    private val attributesPanel: AttributesPanel,
    private val game: Game,
    private val attributeName: AttributeName,
    private val value: Int
) : View.OnClickListener {

    override fun onClick(v: View?) {
        game.changeAttribute(attributeName, value)
        attributesPanel.update()
    }

}
