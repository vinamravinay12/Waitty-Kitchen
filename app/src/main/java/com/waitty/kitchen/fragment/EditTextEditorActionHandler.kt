package com.waitty.kitchen.fragment

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.waitty.kitchen.viewmodel.KeyItemActionListener

class EditTextEditorActionHandler(private val keyItemActionListener: KeyItemActionListener, private val data : Any?,private val valueEntered: String) : TextView.OnEditorActionListener {

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT) || (actionId == EditorInfo.IME_ACTION_DONE))  {
            keyItemActionListener.onKeyEvent(v?.text.toString(),data)
            v?.rootView?.let { FragmentUtils.hideKeyboard(it,it.context) }
        }

        return false
    }

}