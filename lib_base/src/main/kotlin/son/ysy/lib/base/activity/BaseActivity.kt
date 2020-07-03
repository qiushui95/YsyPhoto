package son.ysy.lib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import son.ysy.lib.base.annotation.BindLayoutRes

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
}