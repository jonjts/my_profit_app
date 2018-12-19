package br.com.jonjts.myprofit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment>()
    val fm = getSupportFragmentManager()

    fun selected(f: Fragment) {
        if (!f.isVisible) {
            for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
            val ft = fm.beginTransaction()
            ft.add(R.id.container, f)
            ft.commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                selected(fragments[0])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                selected(fragments[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_itens -> {
                selected(fragments[2])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragmentsList()
        navegation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility = FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    fun onAddBillClicked(v: View){
        startActivity(Intent(this, BillInsertActivity::class.java))
    }

    fun initFragmentsList() {
        fragments.add(HomeFragment.newInstance())
        fragments.add(SearchFragment.newInstance())
        fragments.add(BillsFragment.newInstance())
        selected(fragments[0])

    }
}
