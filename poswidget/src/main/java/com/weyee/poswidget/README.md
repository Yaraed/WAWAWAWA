# 基础控件

1. Button

2. EditText

3. Spinner

4. StateView  
@See [StateManager](https://github.com/alibaba/StateManager)  
对Android通用展示的封装，解决Loading、Exception及常用业务的复用问题  
包含了Loading状态，Exception状态，Net Error状态，EmptyData状态及核心态...，通过调用showState()方法，可以方便的显示各种状态  
特性  
只有在显示对应状态的时候，才会加载View(需要提前注册好各种需要的状态)
支持定制各种状态的文案及视觉
支持拓展自己的状态
支持局部或者全局显示各种状态
支持Activity、Fragment、Layout及帮助类的方式
支持在RecyclerView中单个Item变换各种状态

5. Ticket(小票View)