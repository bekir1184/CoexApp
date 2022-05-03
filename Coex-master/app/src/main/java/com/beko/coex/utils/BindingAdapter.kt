package com.beko.coex.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.beko.coex.R

@BindingAdapter("app:setImage")
fun ImageView.setImage(category : String?) {
    when(category){
        "bill" -> this.setImageResource(R.drawable.ic_bill)
        "food" -> this.setImageResource(R.drawable.ic_baseline_fastfood_24)
        "rent" -> this.setImageResource(R.drawable.ic_baseline_home_24)
        "other" -> this.setImageResource(R.drawable.ic_baseline_more_24)
    }
}
@BindingAdapter("app:setCategory")
fun TextView.setCategory(category : String?) {
    when(category){
        "bill" -> this.text = "Fatura"
        "food" -> this.text = "Gıda"
        "rent" -> this.text = "Kira"
        "other" -> this.text = "Diğer"
    }
}
@BindingAdapter("app:setSplitName")
fun TextView.setSplitName(name : String?) {
    this.text = name!!.split("@")[0]
}
