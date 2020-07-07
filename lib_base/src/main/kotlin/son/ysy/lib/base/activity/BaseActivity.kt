package son.ysy.lib.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import son.ysy.lib.base.annotation.BindLayoutRes

abstract class BaseActivity(@LayoutRes private val LayoutRes: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(LayoutRes)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}