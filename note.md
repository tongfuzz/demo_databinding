* 如果书写过程中出现找不到某个自动生成的类时，需要重新构建一下项目

* 使用@{}引用数据时，如果表达式中需要用到双引号，需要将最外层改成单引号，如下

```xml
<TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text='@{@string/info("age",10)}'
        />
```

* 在引用表达式中不能出现<font color=red> &emsp;<&emsp;</font>符号，需要用<font color=red > &emsp; \&lt; &emsp;</font>来表示

```xml
<variable name="map" type="Map&lt;String,String>"/>
```
* 使用BaseObservable定义可观察对象时，如果BR文件中找不到相应的属性，需要在app的gradle文件中添加如下插件

```gradle
apply plugin: 'kotlin-kapt'
```

* 使用可观察属性时，使用val定义，在更改值时只需通过set方法即可，不要直接使用对象引用属性的方式来更改值