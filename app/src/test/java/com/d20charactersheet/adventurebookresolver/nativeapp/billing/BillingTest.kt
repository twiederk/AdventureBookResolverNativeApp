package com.d20charactersheet.adventurebookresolver.nativeapp.billing

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails
import com.d20charactersheet.adventurebookresolver.nativeapp.Logger
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class BillingTest {

    @Test
    fun startBillingFlow_everythingIsFine_launchBillingFlow() {
        // Arrange
        val billingClient: BillingClient = mock()
        whenever(billingClient.isReady).doReturn(true)

        val skuDetailsList: List<SkuDetails> = mock()
        whenever(skuDetailsList[0]).doReturn(SkuDetails("productId"))

        val logger: Logger = mock()

        val underTest = Billing(logger)
        underTest.billingClient = billingClient
        underTest.skuDetailsList = skuDetailsList

        // Act
        underTest.startBillingFlow(mock())

        // Assert
        verify(underTest.billingClient).isReady
        verify(underTest.billingClient).launchBillingFlow(any(), any())
        argumentCaptor<String> {
            verify(logger).debug(capture())
            assertThat(firstValue).startsWith("startBillingFlow(activity=")
        }
    }

}