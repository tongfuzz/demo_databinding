### Android Jetpack之DataBinding 的使用

#### 介绍

DataBinding是安卓提供的一个通过声明格式的方式将数据源与UI组件来进行绑定的库，它区别于传统的通过编程来绑定数据的方式，使得项目中Activity等UI组件中绑定数据的方法相应的减少，这样使得它们变得更加的简单和便于维护，同时也能够提升App的性能，帮助开发者减少内存泄漏，空指针异常等问题。

文字描述的比较抽象，下面我们来通过官方提供的代码来解释它的优势：

日常为一个TextView设置文字时通常会这么写

```kotlin
findViewById<TextView>(R.id.sample_text).apply {
    text = viewModel.userName
}
```
使用DataBinding后，我们可以直接这样来写

```java
<TextView
    android:text="@{viewmodel.userName}" />
```
将xml布局文件中控件的相应属性通过相应的句法格式，直接在xml中进行绑定，而不需要再在Activity中通过findViewById()找控件并设置，这样大大简化了Activity
，下面我们来看一下如何使用DataBinding并将数据进行绑


#### 简单使用

##### 1，gradle配置

DataBinding具有灵活性和兼容性，它是一个support库，所以你可以将它使用到Android4.0或更高的版本上，对于Gradle它只支持1.5.0及以上

要使用DataBinding，只需在module对应的build.gradle文件中添加如下配置即可

```gradle
android {
    ...
    dataBinding{
        enabled true
    }
}
```
注意，在Gradle版本大于3.1.0-alpha06时，可以通过在gradle.properties中添加如下配置，以加快应用构建速度，在Gradle版本大于3.2时则不需要再进行配置，因为已经默认开启

```gradle
android.databinding.enableV2=true
```

##### 2，在Activity中的使用

>项目使用kotlin语言，对kotlin尚不熟悉的同学可以查看[kotlin语言中文站](https://www.kotlincn.net/docs/reference/)

首先创建MainActivity

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
```
定义一个User类

```kotlin
data class User(val firstName: String, val lastName: String)
```
修改布局文件，用于展示用户信息，注意，使用DataBinding的布局文件与我们常用的布局文件是有一点点不一样的，它以layout作为根标签，其中包含data元素和一个根布局元素，data元素中包含的是layout布局中需要用到的变量对象，变量用<variable/>标签表示，在variable标签中，name代表变量对象的名称，type代表变量对象的类型，在data元素后，是跟布局元素，在跟布局元素中我们可以通过 @{}表达式来引用data中定义的变量对象，如下所示，我们需要展示User对象信息，那么我们需要引用到的对象就是User对象，然后我们通过@{}表达式将user对象属性值赋值给TextView的text属性

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
>
	 <!-- data 用来存放布局文件中需要用到的变量对象 -->
    <data>
     <!-- variable 用来描述布局文件中用到的变量对象，name代表他在layout布局中的名称，type代表它的类型 -->
     <variable name="user" type="com.kk.tongfu.databinding.entity.User"/>
    </data>

    <!--根布局，也就是我们平常书写的layout布局 -->
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".MainActivity"
            android:orientation="vertical"
    >
    	 <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{user.userName}"
        />
        <!-- 通过@{}表达式将User信息赋值给TextView的text属性-->
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{Integer.toString(user.userAge)}"
        />
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{user.userAddress}"
        />
    </LinearLayout>
</layout>
```
在MainActivity中创建对象进行绑定，在onCreate方法中，我们不再使用setContentView设置布局，而是通过DataBindingUtil.setContentView(activity,layoutid)方法来设置布局并返回一个数据绑定类ViewDataBinding的子对象，然后通过这个ViewDataBinding子对象来设置布局文件中需要关联的对象，注意，ViewDataBinding子对象类型一般为布局文件名称的驼峰命名+Binding,比如布局文件名称为activity_main 那么ViewDataBinding子对象类型为ActivityMainBinding，如果无法找到此类，需要构建一下项目生成相应的文件

```java
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.user=User("tongfu",25,"shanghai")
    }
}
```
运行项目效果如下
<div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/demo_databinding/master/screenshots/databinding1.png"/>
 </div>

##### 3，表达式支持的操作和关键词

整体看来，DataBinding将本来在Activity中进行的一系列数据绑定工作，放到了xml文件中，这样大大简化了Activity，在我们拿到相应的绑定对象后，就可以在xml文件中通过@{}完成数据的绑定，此时@{}更像是一个方法，它的值作为相应的布局文件的属性值，其实@{}中可以使用的操作和关键词还有如下一些

1. <font color=red>+&emsp; - &emsp;/ &emsp;*&emsp; %</font>  数字运算符
2. <font color=red>+</font>	字符串拼接符
3. <font color=red>&& &emsp;&emsp;&emsp;||</font> 逻辑运算符
4. <font color=red>& &emsp;|&emsp; ^</font> 位运算符
5. <font color=red>+ &emsp;&emsp;- &emsp;&emsp;! &emsp;&emsp;~</font>一元运算符
6. <font color=red><< &emsp;&emsp; >>> &emsp;&emsp; >></font>位移运算符
7. <font color=red>==&emsp;&emsp; > &emsp;&emsp;< &emsp;&emsp;>= &emsp;&emsp;<=</font>比较运算符 注意<font color=red>&emsp;<&emsp;</font>需要转换为<font color=red>&emsp;\&lt;&emsp;</font>
8. <font color=red>instanceof</font> 判断对象类型
9. 类型转换
10. <font color=red>方法调用&emsp;&emsp;</font>注意不是对象的方法的显示调用，而是类似String.valueOf(index)这样的方法调用
11. 属性访问
12. 数组访问
13. <font color=red>?:</font>三元运算符

<font color=red>注意：</font>在@{}表达式中不能使用 <font color=red>this 、 super、 new</font>以及对象方法的显示调用

使用示例子

```xml
android:text="@{String.valueOf(index + 1)}"
android:visibility="@{age > 13 ? View.GONE : View.VISIBLE}"
android:transitionName='@{"image_" + id}'
```

##### 4，空指针问题

在@{}表达式中还可以使用<font color=red>??</font>判断是否为空

```xml
android:text="@{user.displayName ?? user.lastName}"
```
如上所示，当user.displayName不为null时，text属性值为user.displayName，如果为null，则text的属性值为user.lastName，它等同于如下表达式

```xml
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```
DataBinding还会主动为我们规避空指针异常，如果@{user.userName}中user为null，那么user.userName也为null，并不会抛出空指针异常，只是TextView并不展示任何文字，对于@{user.age}这种int类型的数据，则默认展示的是0，如果我们将MainActivity中的代码改成如下所示

```java
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding.user=User("tongfu",25,null)
        binding.user=null
    }
```
则运行效果如下图
<div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/demo_databinding/master/screenshots/databinding2.png"/>
 </div>
 
##### 5，集合的使用

DataBinding中使用集合，可以通过[]操作符来直接使用其中的元素，使用集合之前，我们需要先将相关联的类引入进来，然后然后定义key，或者index,接着就可以通过[]操作符来获取其中的元素了，示例如下

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
>

    <data>
      	 ......        
        <!--集合的使用 先将需要用到的类导入-->
        <import type="java.util.List"/>
        <import type="java.util.Map"/>
        <!--定义集合对象-->
        <variable name="list" type="List&lt;String>"/>
        <variable name="map" type="Map&lt;String,String>"/>
        <!--定义角标对象-->
        <variable name="index" type="int"/>
        <variable name="key" type="String"/>
        
    </data>

    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".MainActivity"
            android:orientation="vertical"
    >

        ......
        <!--直接通过角标或者key来获取对象-->
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{list[index]}"
        />
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{map[key]}"
        />

    </LinearLayout>
</layout>
```
在MainAcitivty中我们为数组对象和角标进行赋值

```java
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding.user=User("tongfu",25,null)
        //binding.user=null

        val map= mapOf(Pair("1","the first item in map"),Pair("2","the second item in map "), Pair("3","the thrid item in map"))
        val list= listOf("the first item in array","the second item in array","the thrid item in array")

        binding.map=map
        binding.list=list

        binding.index=1
        binding.key="3"

    }
```

##### 6,资源的引用

使用DataBinding引用资源，与我们正常在xml中引用资源差别不大，只是DataBinding更加灵活，功能也更加丰富
我们可以通过如下方式为TextView设置背景，只需定义一个boolen变量isRed,并在Activity中为其赋值即可

```xml
 <variable name="isRed" type="boolean"/>
 <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@{isRed?@color/red : @color/colorPrimary}"
        />
```
我们也可以直接通过如下方式个格式化字符串
在strings.xml中定义如下字符串,并在TextView中进行引用即可

```xml
// %s代表字符串，%d代表十进制数字
<resources>
    <string name="info">the %s of the student is %d</string>
</resources>
//直接通过方法调用的方式来格式化字符串
 <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text='@{@string/info("age",10)}'
        />

```
资源的引用总体来说与我们常用的方式差不多，下面奉上官方的一张图作为参考
<div align=center>
 <img width="500" height="200" src="https://raw.githubusercontent.com/tongfuzz/demo_databinding/master/screenshots/databinding3.png"/>
 </div>

##### 7 事件处理

DataBinding允许通过写表达式的方式处理view的相关事件，例如点击事件onClick,处理事件的机制有两种，两者除了语法不同外，还有一下区别
 
 1. 方法关联，这种方式的缺点是关联的方法的参数必须与相应的listener的回调方法参数相同
 2. 监听绑定，这种方式相对较灵活，能够配置方法的参数，但是要求只能在Gradle2.0及以上能够使用

 首先来查看方法关联，先定义一个view事件处理类MyHandlers，假定绑定的方法为onClick方法,在方法中更改textview的文字
 
 ```java
 class MyHandlers {
		  //方法关联方式，注意，由于我们关联的是onClick方法，此处方法的参数数量和顺序一定要与OnClickListener中的onClick方法参数保持一致
        fun onTextViewClick(view: View){
                (view as TextView).text="点击了"
        }
        //监听绑定方式 ，参数不需要保持一致
        fun onTextViewClick2(view:View,name:String):String{
                (view as TextView).text=name
                return name
        }

}
 ```
 然后在xml文件中关联
 
 ```xml
 <?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
>
       ......
		//声明变量
        <variable name="handler" type="com.kk.tongfu.databinding.callbacks.MyHandlers"/>
       ......
    </data>
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".MainActivity"
            android:orientation="vertical"
    >
        ......
        <!--在onClick属性中进行关联，关联方式为 变量名::方法名-->
        <TextView android:layout_width="80dp"
                  android:layout_height="50dp"
                  android:text="@string/click"
                  android:gravity="center"
                  android:background="@{@color/red}"
                  android:onClick='@{handler::onTextViewClick}'
        />

        ......
    </LinearLayout>

</layout>
 ```
 在Activity中进行对象的绑定
 
 ```java
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding.user=User("tongfu",25,null)
        ......

        binding.handler=MyHandlers()

    }
 ``` 
点击后会发现TextView的文字由点击变成了点击了

绑定监听的方式与方法关联代码上的不同点就是它的参数不需要保持一致，如下所示

```xml
<TextView android:layout_width="80dp"
                   android:layout_height="50dp"
                   android:text="@string/click"
                   android:background="@{@color/red}"
                   android:onClick='@{(view)->handler.onTextViewClick2
                   (view,"haha")}'
         />

```
如果不需要使用view，则可以省略

```xml
<TextView android:layout_width="80dp"
                   android:layout_height="50dp"
                   android:text="@string/click"
                   android:background="@{@color/red}"
                   android:onClick='@{()->handler.onTextViewClick2
                   ("haha")}'
         />

```

也可以在事件中使用三元运算符，如下所示

```xml
android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"
```

至于其他控件，我们也可以使用相同的方式，例如，我们像监听CheckBox的选中事件，则可以通过如下方式

```xml
<CheckBox android:layout_width="wrap_content" android:layout_height="wrap_content"
      android:onCheckedChanged="@{(cb, isChecked) -> handler.onTextViewClick2
                   ("haha",isChecked)}" />
```
### 进阶使用

##### 可观察对象

以前在Activity中为View绑定数据式，当数据源发生改变时，并不会主动更新UI，DataBinding为我们的数据对象提供了可观察的能力，使得当数据发生改变时，UI组件会自动更新

DataBinding提供了三种不同类型的可观察类：<font color=red>属性</font> &emsp;&emsp; <font color=red>集合</font> &emsp;&emsp;<font color=red>对象</font>

###### 可观察属性
---
DataBinding提供了以下几种原始的类来使属性可观察

* ObservableBoolean
* ObservableByte
* ObservableChar
* ObservableShort
* ObservableInt
* ObservableLong
* ObservableFloat
* ObservableDouble
* ObservableField<T>
* ObservableParcelable

来看看它如何使用，首先定义一个ObservableUser类，其中参数为可观察属性，对于非基础类型的属性我们使用ObservableField<T>

```kotlin
//注意此处定义属性尽量使用val定义只读属性，因为绑定数据改变是只改变字段中的值，而不会改变字段本身
class ObservableUser(
    val userName: ObservableField<String>,
    val userAge: ObservableInt,
    val userAddress: ObservableField<String>
)
```
然后在xml文件中引入此类，添加textview用来展示数据，添加button用来更改用户数据

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
        xmlns:android="http://schemas.android.com/apk/res/android"
>
    <data>
        <variable name="user" type="com.kk.tongfu.databinding.entity.ObservableUser"/>
    </data>
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userName}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{String.valueOf(user.userAge)}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userAddress}"
        />

        <Button android:id="@+id/btn_change_userinfo"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:text="@string/change_user_info"
        />
    </LinearLayout>

</layout>
```
最后在activity中绑定数据，并为button设置点击事件用来更改用户名称

```kotlin
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取ViewDataBinding对象，如果找不到类，构建一下项目即可
        val dataBinding:ActivitySpecialUseBinding=DataBindingUtil.setContentView(this,R.layout.activity_special_use)
        //定义一个ObservableUser对象
        val user=ObservableUser(ObservableField("tongfu"), ObservableInt(25), ObservableField("shanghai"))
        //进行绑定
        dataBinding.user=user
        //设置Button的点击事件，并更改user对象的userName属性，注意此处只是改变了对象的userName属性，你会发现UI同步进行了更改，这就是可观察属性
        dataBinding.btnChangeUserinfo.setOnClickListener{
            Log.e("onClick","changeUserName")
            user.userName.set("neza")
        }
    }
```
运行项目，点击button,你会发现虽然我们只更改了userName属性，但是引用了userName属性的TextView中的问题同样进行了更改
<div align=center>
 <img width="300" height="500" src="https://raw.githubusercontent.com/tongfuzz/demo_databinding/master/screenshots/databinding4.gif"/>
 </div>

###### 可观察集合
---
有些app使用动态结构来保存数据，例如User对象，我们有可能不确定此User对象究竟包含了多少信息，所以我们无法通过固定写法来保存user中的属性，因为我们不知道它有多少个属性，DataBinding提供了ObservableArrayMap来通过key和value保存数据

如下所示，我们更改xml文件，不再引入相应的用户类来保存数据，而是引入ObservableArrayMap来作为user对象，

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
        xmlns:android="http://schemas.android.com/apk/res/android"
>
    <data>
        <!--<variable name="user" type="com.kk.tongfu.databinding.entity.ObservableUser"/>-->
        <import type="android.databinding.ObservableArrayMap"/>
        <variable name="user" type="ObservableArrayMap&lt;String,Object>"/>
    </data>
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userName}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{String.valueOf(user.userAge)}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userAddress}"
        />

        <Button android:id="@+id/btn_change_userinfo"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:text="@string/change_user_info"
        />


    </LinearLayout>

</layout>
```
然后在Activity中创建ObservableArrayMap对象并进行绑定，同样我们在点击事件中更改user对象中的value值，你会发现效果与上述可观察属性一样

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: ActivitySpecialUseBinding = DataBindingUtil.setContentView(this, R.layout.activity_special_use)

        //val user = ObservableUser(ObservableField("tongfu"), ObservableInt(25), ObservableField("shanghai"))
        val user = ObservableArrayMap<String, Any>().apply {
            put("userName", "tongfu")
            put("userAge", 10)
            put("userAddress", "shanghai")
            put("userClass", "3-10")
        }
        dataBinding.user = user

        dataBinding.btnChangeUserinfo.setOnClickListener {
            Log.e("onClick", "changeUserName")
            //user.userName="neza"
            user.put("userName","neza")
        }
```
这里效果与上一个gif效果相同不再贴出

DataBinding还提供了一个ObservableArrayList类，用来保存key为integer的数据，这里不再介绍

###### 可观察对象
---

一个实现了Observable接口的类当它的属性发生了变化时，会通知他的订阅者，Observable有一个移除和添加订阅者的机制，但是需要我们去决定什么时候通知订阅者属性发生了变化。为了使开发更简单，DataBinding库为我们提供了BaseObservable类，它为我们提供了通知订阅者的功能，我们只需要添加一个@Bindable注解到get方法，并且在set方法中调用notifyPropertyChanged()即可

下面我们同样来定义一个ObservableImplUser类，继承BaseObservable类

```kotlin
class ObservableImplUser : BaseObservable() {

    //在get方法上使用@Bindable注解
    @get:Bindable
    var userName: String = ""
    set(value) {
        field=value
        //在set方法中调用notifyPropertyChanged方法通知订阅者
        notifyPropertyChanged(BR.userName)
    }

    @get:Bindable
    var userAge: Int = 0
    set(value) {
        field=value
        notifyPropertyChanged(BR.userAge)
    }

    @get:Bindable
    var userAddress: String = ""
    set(value){
        field=value
        notifyPropertyChanged(BR.userAddress)
    }
}
```
从上面的类中我们可以看到，在get方法上我们使用了@Bindable注解，同时在set方法中我们调用了notifyPropertyChanged()方法

<font color=red>注意,BR类为自动生成的类，如果在BR类中找不到定义的属性，需要在app的gradle文件中添加插件</font> 

```gradle
apply plugin: 'kotlin-kapt'
```
接下来在xml文件中引用定义的可观察对象

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
        xmlns:android="http://schemas.android.com/apk/res/android"
>
    <data>
        <!--<variable name="user" type="com.kk.tongfu.databinding.entity.ObservableUser"/>-->
        <!--<import type="android.databinding.ObservableArrayMap"/>
        <variable name="user" type="ObservableArrayMap&lt;String,Object>"/>-->

        <variable name="user" type="com.kk.tongfu.databinding.entity.ObservableImplUser"/>
    </data>
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userName}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{String.valueOf(user.userAge)}"
        />
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="@{user.userAddress}"
        />

        <Button android:id="@+id/btn_change_userinfo"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:text="@string/change_user_info"
        />
    </LinearLayout>
</layout>
```
同样在Activity中创建对象进行绑定，并添加点击事件更改用户名称

```kotlin
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: ActivitySpecialUseBinding = DataBindingUtil.setContentView(this, R.layout.activity_special_use)

        //val user = ObservableUser(ObservableField("tongfu"), ObservableInt(25), ObservableField("shanghai"))
        /* val user = ObservableArrayMap<String, Any>().apply {
             put("userName", "tongfu")
             put("userAge", 10)
             put("userAddress", "shanghai")
             put("userClass", "3-10")
         }
         dataBinding.user = user

         dataBinding.btnChangeUserinfo.setOnClickListener {
             Log.e("onClick", "changeUserName")
             //user.userName="neza"
             user.put("userName","neza")
         }*/

        val user = ObservableImplUser()
		 user.userName="tongfu"
        user.userAge=25
        user.userAddress="shanghai"
        dataBinding.user = user

        dataBinding.btnChangeUserinfo.setOnClickListener {
            Log.e("onClick", "changeUserName")
            user.userName="neza"
        }
    }
```
点击更改用户信息按钮你就会发现用户名由tongfu变成了neza


##### 生成Binding类

###### DataBinding在fragemnt和adapter中的使用
------

在开始的介绍中我们发现，获取Binding类时通过DataBindingUtil.setContentView(this,R.layout.activity_main)来获取的，这种方式只能适用于在Activity中使用，其实DataBinding还为我们提供了其他几种生成Binding类的方式

在Fragment中使用时，我们可以在OnCreateView通过如下方式获取Binding类

```kotlin

lateinit var binding:FragmentBindinguseBinding

override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_bindinguse,container,false)
        //返回的是layout布局解析的文件，等同于inflater.inflate(R.layout.fragment_bindinguse,container,false)
        return binding.root
    }
```
也可以通过下面这种方式

```kotlin
View viewRoot = LayoutInflater.from(this).inflate(layoutId, parent, attachToParent);
ViewDataBinding binding = DataBindingUtil.bind(viewRoot);
```

在Adapter中使用时我们可以通过如下方法,注意，在adapter中的数据绑定时采用的是setVariable方式，BR.user为系统自动生成

```kotlin
class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val dataBinding: AdapterUserBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapter_user,parent,false)
        return UserViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        val user= User("tongfu",position,"shanghai")
        //在adapter中的数据绑定时采用的是setVariable方式，BR.user为系统自动生成
        userViewHolder.dataBinding.setVariable(BR.user,user)
    }

    class UserViewHolder(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
```

###### 添加绑定适配器
---
DataBinding库允许我们通过指定方法调用去设置属性的值，即便这个属性不存在，只要有set方法就可以在xml文件中声明，例如RecyclerView的LayoutManager，一般我们会通过如下方式代码进行设置

```kotlin
recyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
```
由于RecyclerView有setLayoutManager()方法，现在通过绑定适配器直接可以通过如下方式,它们的效果是等同的，同理，我们也可以直接在xml中引用Adapter，使用这种方式，需要我们在xml文件定义的名称与set后面的名称相同，即声明了属性android:abc 对应的类中要有方法名为setAbc()的方法

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools">
    <data>
        <import type="android.support.v7.widget.LinearLayoutManager"/>
        <variable name="layoutmanager" type="LinearLayoutManager"/>

    </data>
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:background="@color/yellow"
    >
        <android.support.v7.widget.RecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layoutManager="@{layoutmanager}"
        />

    </LinearLayout>
</layout>
```

当然我们也可以自定义绑定逻辑，比如我想在所有的textview的后面都加上haha这个字符串，我们可以这么写：定义一个单例模式的类，然后在其中定义方法，并声明@BindingAdapter注解，如下：

```kotlin

	 //BindingAdapter注解中可以加入view原有的属性，也可以加入自己定义的属性
    @BindingAdapter("android:text")
    @JvmStatic fun setText(view:TextView,info:String){
        view.text = info+"haha"
    }
    //注解中加入自定义的属性
    @BindingAdapter("app:hideIfZero")
    @JvmStatic fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }
    
```

