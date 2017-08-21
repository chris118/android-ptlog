##### 1. 根目录build.gradle添加

```
allprojects {
    repositories {
        jcenter()
        maven { url 'http://maven.putao.io/nexus/content/repositories/putao/' }
        maven { url 'http://maven.putao.io/nexus/content/repositories/PutaoCentral/' }
    }
}

```

##### 2. 工程build.gradle中添加依赖：

```
compile 'com.putao.mobile:ptlog:1.0.0'

```