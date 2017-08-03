##### 1. 拷贝ptlog.aar到libs目录

##### 2. build.gradle文件加入

```
repositories {
    flatDir {
    dirs 'libs'
}
```

##### 3. 添加依赖：

```
compile 'com.orhanobut:logger:2.1.1'
compile 'org.greenrobot:greendao:3.2.2'
compile(name:'ptlog', ext:'aar')
```