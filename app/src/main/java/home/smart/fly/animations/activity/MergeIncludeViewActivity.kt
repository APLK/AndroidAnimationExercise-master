package home.smart.fly.animations.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import home.smart.fly.animations.R
import home.smart.fly.animations.utils.StatusBarUtil
import kotlinx.android.synthetic.main.include_view.*

class MergeIncludeViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0);
        setContentView(R.layout.activity_merge_include_view)

        back.setOnClickListener { finish() }
    }
}
