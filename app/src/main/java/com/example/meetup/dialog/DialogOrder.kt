import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogOrderBinding
import com.example.meetup.databinding.FragmentMenuBinding
import com.example.meetup.databinding.RowOrderFoodCheckBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.example.meetup.model.CartFood
import com.example.meetup.sharedPreference.MyApplication

interface OrderDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogOrder(var cartFoodList: List<CartFood>) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogOrderBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: OrderDialogInterface? = null

    lateinit var homeActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOrderBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding.run {
            recyclerviewFood.run {
                adapter = OrderDialogRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(context)
            }
        }

        // 취소 버튼 클릭
        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        // 상품 담기 버튼 클릭
        binding.buttonAddFood.setOnClickListener {
            this.confirmDialogInterface?.onClickYesButton(id!!)
            if(MyApplication.cartItem.size != 0) {
                if(cartFoodList.get(0).storeId == MyApplication.cartItem.get(0).storeId) {
                    for(i in 0 until cartFoodList.size) {
                        MyApplication.cartItem.add(cartFoodList.get(i))
                    }
                } else {
                    MyApplication.cartItem.clear()
                    for(i in 0 until cartFoodList.size) {
                        MyApplication.cartItem.add(cartFoodList[i])
                    }
                    Toast.makeText(homeActivity, "해당 가게의 상품이 담겼습니다!", Toast.LENGTH_LONG).show()
                }
            } else {
                MyApplication.cartItem = cartFoodList.toMutableList()
            }
            Log.d("장바구니", "cartItemList Final : ${MyApplication.cartItem}")
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        var activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

        activityHomeBinding.root.setBackgroundResource(R.drawable.blur_background)
    }

    inner class OrderDialogRecyclerViewAdapter : RecyclerView.Adapter<OrderDialogRecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowOrderFoodCheckBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowFoodName: TextView
            val rowOptionName: TextView
            val rowDeliveryOption: Button

            init {
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowDeliveryOption = rowBinding.buttonDeliveryOption
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowOrderFoodCheckBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return cartFoodList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowFoodName.text = "${cartFoodList.get(position).foodName}"
            holder.rowOptionName.text = "${cartFoodList.get(position).foodOptionName}"
            holder.rowDeliveryOption.text = "${cartFoodList.get(position).orderDeliveryOption}"
            holder.rowDeliveryOption.text = "${cartFoodList.get(position).orderDeliveryOption}"
            if(cartFoodList.get(position).orderDeliveryOption == "포장") {
                holder.rowDeliveryOption.setBackgroundResource(R.drawable.button_pickup_background)
            } else {
                holder.rowDeliveryOption.setBackgroundResource(R.drawable.button_delivery_background)
            }
        }
    }
}