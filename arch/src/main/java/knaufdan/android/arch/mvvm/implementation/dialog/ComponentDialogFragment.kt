package knaufdan.android.arch.mvvm.implementation.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import knaufdan.android.arch.BR
import knaufdan.android.arch.R
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogAnimation
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogSize
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogStyle

class ComponentDialogFragment(
    component: IComponent<IComponentViewModel>,
    private val dialogStyle: DialogStyle
) : DialogFragment() {
    private val viewModel = component.getDataSource()
    private val layoutRes = component.getLayoutRes()
    private val viewModelKey = component.getBindingKey()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        run {
            dialog?.setCanceledOnTouchOutside(true)

            DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                layoutRes,
                container,
                false
            ).apply {
                setVariable(viewModelKey, viewModel)
                setVariable(BR.fm, childFragmentManager)
            }
                .run {
                    lifecycleOwner = this@ComponentDialogFragment

                    executePendingBindings()

                    dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)

                    root
                }
        }

    override fun getTheme(): Int {
        return dialogStyle.dialogAnimation.toAndroidAnimation()
    }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            when (dialogStyle.dialogSize) {
                DialogSize.FULL_SCREEN -> setLayoutParams()
                DialogSize.FULL_WIDTH -> setLayoutParams(heightParam = ViewGroup.LayoutParams.WRAP_CONTENT)
                DialogSize.WRAP_CONTENT -> setLayoutParams(
                    widthParam = ViewGroup.LayoutParams.WRAP_CONTENT,
                    heightParam = ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        viewModel.onAttach()
    }

    override fun onStop() {
        viewModel.onDetach()

        super.onStop()
    }

    override fun onDismiss(dialog: DialogInterface) {
        viewModel.onDetach()

        super.onDismiss(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    private fun Dialog.setLayoutParams(
        widthParam: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        heightParam: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        window?.setLayout(widthParam, heightParam)
    }

    companion object {
        private fun DialogAnimation.toAndroidAnimation(): Int =
            when (this) {
                DialogAnimation.NONE -> R.style.ArchComponentDialogFragment
                DialogAnimation.SLIDE_IN_OUT -> R.style.ArchComponentDialogFragment_SlideInOut
                DialogAnimation.FADE_IN_OUT -> R.style.ArchComponentDialogFragment_FadeInOut
            }
    }
}
