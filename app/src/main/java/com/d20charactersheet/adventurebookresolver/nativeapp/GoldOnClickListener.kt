package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class GoldOnClickListener : View.OnClickListener, KoinComponent {
    protected val game: Game by inject()
    protected val goldPanel: GoldPanel by inject()
}

class GoldIncreaseOnClickListener : GoldOnClickListener() {

    override fun onClick(v: View?) {
        game.increaseGold()
        goldPanel.update()
    }

}

class GoldDecreaseOnClickListener : GoldOnClickListener() {

    override fun onClick(v: View?) {
        game.decreaseGold()
        goldPanel.update()
    }

}

