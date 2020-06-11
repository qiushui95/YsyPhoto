package me.yangcx.base.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.yangcx.base.annotations.BindLayoutRes

abstract class BaseActivity : AppCompatActivity() {
    private fun getLayoutRes() = BindLayoutRes.getLayoutRes(javaClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(getLayoutRes())
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}