###PtrSwipeMenuRecyclerView
---
######本项目对RecyclerView进行再次封装，集合了最常用的下拉刷新、上拉加载更多和侧滑菜单功能。
######另，项目中也利用ItemTouchHelper简单实现了对item的拖拽操作，长按可对item进行拖拽移动

>项目在RecyclerView的基础上实现了侧滑菜单、下拉刷新、滚动到底部自动加载更多的功能，支持自定义下拉刷新和加载更多的头部HeaderView、底
部FooterView，侧滑菜单可根据自身业务给不同的item设置不同的侧滑菜单，或作根据item的具体数据情况决定是否设置侧滑菜
单。 相关效果图等详见下面的说明。

**1.效果图片**

侧滑菜单效果：

![image](https://github.com/zhangyuChen1991/some_sources/blob/master/ptrSwipeRecyclerView/swipe.gif)

下拉刷新效果：

![image](https://github.com/zhangyuChen1991/some_sources/blob/master/ptrSwipeRecyclerView/pull-down.gif)

上拉加载效果:

![image](https://github.com/zhangyuChen1991/some_sources/blob/master/ptrSwipeRecyclerView/pull-up.gif)

拖拽效果：

![image](https://github.com/zhangyuChen1991/some_sources/blob/master/ptrSwipeRecyclerView/drag.gif)



**2.使用说明**
> 在所需引用此库的module的build.gradle文件中添加:
```
  dependencies {
     compile 'com.library.widget:library:1.0.1'
  }
```

注：项目功能为初步实现，仍在改进中。欢迎提出意见，共同讨论共同进步！