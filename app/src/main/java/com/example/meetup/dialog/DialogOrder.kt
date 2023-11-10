import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.meetup.R
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogOrderBinding
import com.example.meetup.databinding.FragmentMenuBinding

interface OrderDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogOrder : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogOrderBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: OrderDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOrderBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 취소 버튼 클릭
        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.buttonAddFood.setOnClickListener {
            this.confirmDialogInterface?.onClickYesButton(id!!)
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
}