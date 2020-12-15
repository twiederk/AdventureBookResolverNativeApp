package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ProvisionsOnClickListener : View.OnClickListener, KoinComponent {
    protected val game: Game by inject()
    protected val provisionsPanel: ProvisionsPanel by inject()
}

class ProvisionsIncreaseOnClickListener : ProvisionsOnClickListener() {

    override fun onClick(v: View?) {
        game.increaseProvisions()
        provisionsPanel.update()
    }

}

class ProvisionsDecreaseOnClickListener : ProvisionsOnClickListener() {

    override fun onClick(v: View?) {
        game.decreaseProvisions()
        provisionsPanel.update()
    }

}

class ProvisionsEatOnClickListener : ProvisionsOnClickListener() {

    override fun onClick(v: View?) {
        game.eatProvision()
        provisionsPanel.update()
    }

}

