package com.d20charactersheet.adventurebookresolver.nativeapp.billing

import android.content.Context
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.SkuType
import com.d20charactersheet.adventurebookresolver.nativeapp.Logger
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.MainActivity


class Billing(private val logger: Logger = Logger) : PurchasesUpdatedListener, SkuDetailsResponseListener {

    private val SKU = "empty_book"
//    private val SKU = "android.test.purchased"
//    private val SKU = "android.test.canceled"
//    private val SKU = "android.test.item_unavailable"

    internal lateinit var billingClient: BillingClient
    internal var skuDetailsList: List<SkuDetails>? = null
    private var activity: MainActivity? = null

    // --------------------
    // -- Initialization --
    // --------------------
    fun startConnection(context: Context) {
        billingClient = BillingClient.newBuilder(context).setListener(this).enablePendingPurchases().build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                logger.debug("onBillingSetupFinished (billingResult.responseCode=${billingResult.responseCode})")
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    querySkuDetailsList()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                logger.debug("onBillingServiceDisconnected")
            }
        })
    }

    private fun querySkuDetailsList() {
        logger.debug("queryPrice ()")

        if (billingClient.isReady) {

            val skuList: MutableList<String> = ArrayList()
            skuList.add(SKU)

            val params = SkuDetailsParams.newBuilder()
            params.setSkusList(skuList).setType(SkuType.INAPP)

            billingClient.querySkuDetailsAsync(params.build(), this)
        }
    }

    override fun onSkuDetailsResponse(billingResult: BillingResult?, skuDetailsList: List<SkuDetails>?) {
        logger.debug("onSkuDetailsResponse(billingResult.responseCode=${billingResult?.responseCode}, skuDetailsList=$skuDetailsList)")
        if (billingResult!!.responseCode == BillingResponseCode.OK) {
            this.skuDetailsList = skuDetailsList
        }
    }


    // -------------
    // -- Billing --
    // -------------
    fun startBillingFlow(activity: MainActivity) {
        logger.debug("startBillingFlow(activity=${activity}")
        this.activity = activity
        if (billingClient.isReady) {
            skuDetailsList?.get(0)?.let {
                val flowParams = BillingFlowParams.newBuilder().setSkuDetails(it).build()
                billingClient.launchBillingFlow(activity, flowParams)
            }
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        logger.debug("onPurchasesUpdated(billingResult=$billingResult,purchases=$purchases)")
        if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
            logger.debug("billingResult.responseCode=BillingResponseCode.OK")
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingResponseCode.USER_CANCELED) {
            logger.debug("billingResult.responseCode=BillingResponseCode.USER_CANCELED")
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            logger.debug("handle any other error codes")
            // Handle any other error codes.
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        logger.debug("handlePurchase(purchase=$purchase")
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // Grant entitlement to the user.
            activity?.purchasedBook()

            // Consume immediately
            if (billingClient.isReady) {
                val consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
                billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
                    if (billingResult.responseCode == BillingResponseCode.OK && purchaseToken != null) {
                        logger.debug("consumeAsync success, responseCode: BillingResponseCode.OK")
                    } else {
                        logger.debug("consumeAsync ERROR, responseCode: ${billingResult.responseCode}")
                    }
                }
            }

        }
    }

    fun clearHistory() {
        if (billingClient.isReady) {
            billingClient.queryPurchases(SkuType.INAPP).purchasesList.forEach {
                val consumeParams = ConsumeParams.newBuilder().setPurchaseToken(it.purchaseToken).build()
                billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
                    if (billingResult.responseCode == BillingResponseCode.OK && purchaseToken != null) {
                        logger.debug("onPurchases Updated consumeAsync, purchases token removed: $purchaseToken")
                    } else {
                        logger.debug("onPurchases some troubles happened: ${billingResult.responseCode}")
                    }
                }
            }
        }
    }
}
