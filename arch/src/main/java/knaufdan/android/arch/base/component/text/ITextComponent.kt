package knaufdan.android.arch.base.component.text

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.text.implementation.TextViewModel
import knaufdan.android.arch.databinding.recyclerview.IDiffItem

interface ITextComponent : IComponent<TextViewModel>, IDiffItem
