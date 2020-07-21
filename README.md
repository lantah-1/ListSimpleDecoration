#### 说明
这是一个针对recyclerview的库，可提供简单的粘性头部，列表间距功能

#### 使用

1.跟目录的build.gradle
```
maven { url = "https://dl.bintray.com/lamandys/repository" }
```

2. 要使用的module.gradle
```
    implementation 'com.lamandys:listSimpleDecoration:1.0.0'
```

3. 代码中加入
```
recyclerList.addItemDecoration(LineDecoration.default())
```

或者是 粘性头部：

```
val stickyDecoration = StickyDecoration.Builder()
            .stickyTextColor(Color.parseColor("#456EFF"))
            .stickyTextSize(14)
            .stickyItemBackgroundColor(Color.parseColor("#5E6D82"))
            .stickyItemHeight(150)
            .stickTextMarginLeft(100f)
            .withStickyHoverBuilder(object : StickyHoverBuilder {
                override fun isFirstItemInGroup(position: Int): Boolean {
                    val date = viewModel.myListData.value?.get(position) ?: return false
                    return date.isFirstItemInGroup
                }

                override fun groupName(position: Int): String {
                    val date = viewModel.myListData.value?.get(position) ?: return ""
                    return date.groupName
                }

            })
            .build()
        recyclerList.addItemDecoration(stickyDecoration)
```