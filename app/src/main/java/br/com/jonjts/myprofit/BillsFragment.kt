package br.com.jonjts.myprofit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jonjts.myprofit.adapter.BillAdapter
import br.com.jonjts.myprofit.adapter.RecyclerItemClickListenr
import br.com.jonjts.myprofit.callback.BillListCallback
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import kotlinx.android.synthetic.main.fragment_bill_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BillsFragment.OnListFragmentInteractionListener] interface.
 */
class BillsFragment : BaseFragment(), BillListCallback {


    var billList: RecyclerView? = null
    var emptyView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bill_list, container, false)

        emptyView = view.lnl_empty
        init(view)

        return view
    }

    override fun init(v: View) {
        super.init(v)
        billList = v.list

        with(v.list) {
            layoutManager = LinearLayoutManager(context)
            addOnItemTouchListener(
                RecyclerItemClickListenr(context,
                    this, object : RecyclerItemClickListenr.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            onBillClicked(view.tag as Bill)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {

                        }
                    })
            )
        }

        reload()
    }

    override fun reload() {
        if (!isAdded) {
            return
        }
        val month = getSelectedMes()
        val year = getSelectedAno()
        val mValues = App.database?.billDao()!!.consultByMonthYear(
            Util.firstDate(month, year),
            Util.lastDate(month, year)
        )
        billList?.adapter = BillAdapter(mValues, emptyView, billList)
    }

    override fun onBillClicked(bill: Bill) {
        var it = Intent(activity, BillUpdateActivity::class.java)
        val b = Bundle()
        b.putLong("id", bill?.id!!)
        it.putExtras(b)
        startActivityForResult(it, MainActivity.openBillActivity)
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() = BillsFragment()
    }
}
