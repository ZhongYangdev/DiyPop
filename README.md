#DiyPop
![示例](https://upload-images.jianshu.io/upload_images/25929436-49ead0532944eb45.gif?imageMogr2/auto-orient/strip)

自定义日期选择弹窗，基于开源控件[AndroidPicker](https://github.com/gzu-liyujiang/AndroidPicker)实现。
此案例是根据交流群的一位网友需求写的，主要是选择两个日期，和自定义弹窗。没什么特别的难度，主要是**自定义弹窗的实现**和**日期滚轮监听**。

#自定义弹窗
案例中的自定义弹窗 *DatePop* 就是通过继承 *PopupWindow* 实现的。需要注意的细节是 弹窗的显示和关闭要设置动画，包括弹窗打开时的背景渐变。

#日期滚轮监听
开始日期 和 结束日期是两个TextView。通过代码设置 **setSelected** 属性实现点击选中，通过自定义常规drawable和选中drawable设置不同状态。