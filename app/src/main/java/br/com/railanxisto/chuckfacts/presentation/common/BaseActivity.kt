package br.com.railanxisto.chuckfacts.presentation.common

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    protected fun showSnackbar(@StringRes textResId: Int) =
        Snackbar.make(findViewById(android.R.id.content), textResId, Snackbar.LENGTH_LONG).show()
}