package com.waitty.kitchen.databinding

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import com.waitty.kitchen.R
import com.waitty.kitchen.fragment.EditTextEditorActionHandler
import com.waitty.kitchen.fragment.FragmentUtils
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.utility.WKCheckChangeListener
import com.waitty.kitchen.viewmodel.KeyItemActionListener

object BindingUtils {

    @BindingAdapter("buttontextcolor")
    @JvmStatic fun setFont(button: Button, color: Int) {
        button.setTextColor(color)
    }

    @BindingAdapter("ibsource")
    @JvmStatic fun setImageViewResource(imageButton: ImageButton, resource: Drawable?) {
//        RegistrationViewModel.imageButton = imageButton;
//        RegistrationViewModel.resource = resource;
        imageButton.setImageDrawable(resource)
    }


    @BindingAdapter("imageSource")
    @JvmStatic fun setImageViewDrawable(imageView: ImageView, resource: Int?) {
        resource?.let { imageView.setImageResource(it) }
    }

    @BindingAdapter("rightDrawable")
    @JvmStatic fun setButtonDrawable(button: Button, resource: Drawable?) {
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null)
    }

    @BindingAdapter("shake")
    @JvmStatic fun setShakeAnimation(view: View?, error: MutableLiveData<Int>) {
        if (error.value ?: 0 > 0) {
            Utility.doShakeAnimation(view)
        }
    }

    @BindingAdapter("changeFocus")
    @JvmStatic fun onFocusChanged(editText: EditText, listener: View.OnFocusChangeListener?) {
        editText.onFocusChangeListener = listener
    }

    @BindingAdapter("imageUrl")
    @JvmStatic fun setImageUrl(imageView: ImageView, path: String?) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        val context = imageView.context
        Picasso.get().load(path).into(imageView)

    }

    @BindingAdapter("progressVisibility")
    @JvmStatic fun setVisibility(view: View, visibility: Int) {
        view.visibility = visibility
    }


    @BindingAdapter("progressAnimate")
    @JvmStatic fun animate(view: View, isProgressVisible: Boolean) {
        if (isProgressVisible) view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_indeterminate_progress))
    }


    @BindingAdapter("currentPage")
    @JvmStatic fun setNewTab(viewPager: ViewPager, newTab: MutableLiveData<Int>?) {
        if (newTab == null) {
            return
        }
        if (newTab.value == -1) {
            return
        }
        if (viewPager.currentItem != newTab.value) {
            viewPager.setCurrentItem(newTab.value!!, true)
        }
    }


    @BindingAdapter(value = ["itemChecked", "checkedItem"],requireAll = true)
    @JvmStatic fun setCheckChange(checkBox: CompoundButton, checkListener : WKCheckChangeListener, item : Any? ) {
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) checkListener.onCheckChanged(item)
        }
    }

    @BindingAdapter(value = ["onKeyAction", "itemPosition"],requireAll = true)
    @JvmStatic fun setKeyChangeListener(editText: EditText,keyItemActionListener: KeyItemActionListener, position : Int) {
        editText.setOnEditorActionListener(EditTextEditorActionHandler(keyItemActionListener,position,editText.text.toString()))
    }



}