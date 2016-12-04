# CakeRouter

Android下，无需提前配置参数，即可通过URL启动一个activity（可带参数）

### Install

```java

packagingOptions {
	exclude 'META-INF/services/javax.annotation.processing.Processor'
}

dependencies {
	compile 'com.github.zhaoxuan:cake-router:0.1'
}

```

### Usage

* 路由定义规则
        String url = "[domaim]://[pageName]?[paramKey]@[paramType]=[paramValue]&[paramKey1]@[paramType1]=[paramValue1]";
        
 完整示例： 
 		//未提前配置pageName情况下，需给出全类名
 		String url = "eleme://com.zhaoxuan.test.RouterActivity?param1@str=abcdefg&param2@i=10&param3@c=p";
        
        //已提前配置pageName情况下，直接给出配置名即可
 		String url = "eleme://router?param1@str=abcdefg&param2@i=10&param3@c=p";

	* domaim : 自定义你app的域名，如 eleme
	* pageName : 要跳转的页面名称
	* paramKey: 跳转Activity时，Intent参数名
	* paramType： 参数类型
	* paramValue： 参数值
	* 参数与参数之间用 & 分割，参数由参数名，参数类型，参数值组成：[paramKey]@[paramType]=[paramValue]

* 提前配置
CakeRouter在没有提前配置pageName的情况下，需使用全类名，url过于沉长。所以额外支持了提前配置pageName.修饰如下：

		@CakeRouterUrl("main")
		public class MainActivity extends AppCompatActivity {}

* ParamType
可支持的参数类型及url缩写对照如下：

用来限定scheme中的参数的数据类型，定义请参考下表

|format|type|
|:-:|:-:|
|@i|int|
|@i[]|int[]|
|@l|long|
|@l[]|long[]|
|@f|float|
|@f[]|floag[]|
|@d|double|
|@d[]|double[]|
|@bl|boolean|
|@bl[]|boolean[]|
|@bt|byte|
|@bt[]|byte[]|
|@str|string|
|@str[]|string[]|
|@s|short|
|@s[]|short[]|
|@c|char|
|@c[]|char[]|
|@全类名|json对象|



* route to Activity

```java
@CakeRouterUrl("router_test")
public class RouterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_test);

        Intent in = getIntent();
        String strExtra = in.getStringExtra("str");
        int intExtra = in.getIntExtra("int", -1);
        char charExtra = in.getCharExtra("char", 'a');

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(strExtra + " " + intExtra + " " + charExtra);

    }
}


public class RouterActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        CakeRouter cakeRouter = new CakeRouter.Builder("eleme")
                .build();

        String url = "router_test://main?str@str=abcdefg&int@i=10&char@c=p";

        cakeRouter.dispatch(this, url);
    }
    }

```

* build scheme

```java


