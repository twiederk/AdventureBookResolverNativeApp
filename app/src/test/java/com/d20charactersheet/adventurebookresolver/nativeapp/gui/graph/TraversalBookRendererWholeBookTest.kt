package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File

class TraversalBookRendererWholeBookTest {

    private val bookTheWarlockOfFiretopMountain =
        ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}01_The_Warlock_of_Firetop_Mountain"
    private val bookTheCitadelOfChaos = ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}02_The_Citadel_of_Chaos"
    private val bookTheForestOfDoom = ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}03_The_Forest_of_Doom"
    private val bookStarshipTraveller = ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}04_Starship_Traveller"
    private val bookCityOfThieves = ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}05_City_of_Thieves"
    private val bookTempleOfTerror = ".${File.separator}src${File.separator}test${File.separator}resources${File.separator}12_Temple_of_Terror"

    @Test
    fun render_bookTheWarlockOfFiretopMountain_renderGraphOfBookTheWarlockOfFiretopMountain() {
        // arrange
        val book = BookStore().load(bookTheWarlockOfFiretopMountain)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(163)
    }

    @Test
    fun render_bookTheCitadelOfChaos_renderGraphOfBookTheCitadelOfChaos() {
        // arrange
        val book = BookStore().load(bookTheCitadelOfChaos)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(260)
    }

    @Test
    fun render_bookTheForestOfDoom_renderGraphOfBookTheForestOfDoom() {
        // arrange
        val book = BookStore().load(bookTheForestOfDoom)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(272)
    }

    @Test
    fun render_bookStarshipTraveller_renderGraphOfBookStarshipTraveller() {
        // arrange
        val book = BookStore().load(bookStarshipTraveller)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(20)
    }

    @Test
    fun render_bookCityOfThieves_renderGraphOfBookCityOfThieves() {
        // arrange
        val book = BookStore().load(bookCityOfThieves)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(293)
    }

    @Test
    fun render_bookTempleOfTerror_renderGraphOfBookTempleOfTerror() {
        // arrange
        val book = BookStore().load(bookTempleOfTerror)
        val game = Game().apply { this.book = book }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).hasSize(341)
    }

}