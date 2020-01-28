package knaufdan.android.arch.base.component

interface IMessageComponent : IComponent<Unit> {
    override fun getBindingKey(): BindingKey = 0

    override fun getDataSource() = Unit
}
