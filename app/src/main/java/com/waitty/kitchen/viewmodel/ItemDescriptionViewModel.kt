package com.waitty.kitchen.viewmodel

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.BulletSpan
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waitty.kitchen.adapter.OrderItemCustomizationOptionsAdapter
import com.waitty.kitchen.model.OrderItem
import java.util.*
import kotlin.collections.ArrayList

class ItemDescriptionViewModel : ViewModel() {

    private val itemsDescriptionUnorderedListData = MutableLiveData<String>()
    private val orderItems = MutableLiveData<List<OrderItem>>()
    private val selectedOrderItem = MutableLiveData<OrderItem>()
    private var rupeeSymbol : String = ""
    private val allItemsList = ArrayList<String>()
    private val customizationsMap = TreeMap<String,List<String>>()






    fun setRupeeSymbol(rupeeSymbol : String) {
        this.rupeeSymbol = rupeeSymbol
    }
    fun getOrderItems() = orderItems

    fun getOrderItem(position: Int): MutableLiveData<OrderItem>? {
        val orderItem = orderItems.value?.get(position)
        return orderItem?.let { MutableLiveData(it) }
    }

    fun unorderedListOfItemDescription(position: Int): MutableLiveData<String> {
        val orderItem = getOrderItem(position)
        itemsDescriptionUnorderedListData.value = orderItem?.value?.let { getSpannableString(it) }
        return itemsDescriptionUnorderedListData

    }


    fun getItemName(position: Int) : MutableLiveData<String> {
        return MutableLiveData(orderItems.value?.get(position)?.dishDetails?.name ?: "")
    }

    fun getItemQuantity(position: Int) : MutableLiveData<String> {
        return MutableLiveData(StringBuilder().append(orderItems.value?.get(position)?.quantity ?: 0).toString())
    }

    fun getItemPrice(position: Int) : MutableLiveData<String> {
        return MutableLiveData(StringBuilder().append(rupeeSymbol).append(orderItems.value?.get(position)?.dishAmount ?: "").toString())
    }

    fun isItemReady(position: Int) : MutableLiveData<Boolean>{

        return MutableLiveData(orderItems.value?.get(position)?.isPrepared ?: false)
    }


    private fun getSpannableString(orderItems: OrderItem): String {

        return getUnorderedListString(getOptionsMap(orderItems))
    }


     fun getAllItemsList(map : TreeMap<String,List<String>>) : ArrayList<String> {
        val allItemsList = ArrayList<String>()

        for(key in map.keys) {
            allItemsList.add(key)
            map[key]?.let { allItemsList.addAll(it) }
        }
    return allItemsList

    }





    fun getOptionsMap(orderItem: OrderItem) : TreeMap<String,List<String>> {
        val ordersMap = TreeMap<String,List<String>>()


            for (item in orderItem.orderItemCustomizations) {
                if (item.orderItemCustomizationsOptions.isEmpty()) continue
                val header = item.orderCustomizationDetails.name

                val itemsCustomizationOptionList = ArrayList<String>()

                for (option in item.orderItemCustomizationsOptions) {
                    itemsCustomizationOptionList.add(StringBuilder().append(option.orderCustomizationOptionDetails.name).append(
                            if (!option.orderCustomizationOptionDetails.amount.isNullOrEmpty()) " - " + option.orderCustomizationOptionDetails.amount else "").toString())

                }
                ordersMap[header] = itemsCustomizationOptionList


        }
        return ordersMap
    }


    private fun getUnorderedListString(customizationsMap: TreeMap<String, List<String>>): String {

        var unorderedListString = ""


        for (header in customizationsMap.keys) {
            val itemDescriptionList = customizationsMap[header]
            val stringBuilder = java.lang.StringBuilder()
            stringBuilder.append("<h5>").append(header).append("</h5>").append("<ul>")
            val iterator = itemDescriptionList?.iterator()

            while(iterator != null && iterator.hasNext()) {
                val item = iterator.next()
                stringBuilder.append("\t").append("<li>").append(item).append("</li>")
            }
            stringBuilder.append("</ul><br>")
            unorderedListString += stringBuilder.toString()
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(unorderedListString,Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(unorderedListString).toString()
        }
    }



}