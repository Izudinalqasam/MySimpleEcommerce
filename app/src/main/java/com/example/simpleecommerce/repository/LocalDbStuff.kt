package com.example.simpleecommerce.repository

import com.example.simpleecommerce.model.database.PurchaseHistory
import com.example.simpleecommerce.model.network.ProductPromo
import io.reactivex.subjects.PublishSubject
import io.realm.Realm

class LocalDbStuff {

    companion object {
        val instance by lazy {
            LocalDbStuff()
        }
    }

    private var realm = Realm.getDefaultInstance()
    val publistResultHistory = PublishSubject.create<List<PurchaseHistory>>()

    fun getPurchasedProduct(){
        if (realm.isClosed) {
            realm = Realm.getDefaultInstance()
        }

        try {
            realm.executeTransaction { rl ->
                val result = rl.where(PurchaseHistory::class.java).findAll()
                publistResultHistory.onNext(rl.copyFromRealm(result))
            }
        } catch (e: Exception) {
            if (!realm.isClosed) {
                if (realm.isInTransaction) {
                    realm.cancelTransaction()
                }
            }
        } finally {
            realm.close()
        }
    }

    fun insertItemToHistory(item: ProductPromo){
        if (realm.isClosed) {
            realm = Realm.getDefaultInstance()
        }

        try {
            realm.executeTransaction { rl ->
                val purchaseHistory = PurchaseHistory(
                    item.description, item.id, item.imageUrl, item.loved, item.price, item.title)

                rl.copyToRealm(purchaseHistory)
            }
        } catch (e: Exception) {
            if (!realm.isClosed) {
                if (realm.isInTransaction) {
                    realm.cancelTransaction()
                }
            }
        } finally {
            realm.close()
        }
    }
}