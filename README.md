# CustomCiruclarProgressBar

## Usage
Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories.

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

    dependencies {
	        compile 'com.github.raj10071997:CustomCiruclarProgressBar:1.0.1'
	}
  
  
## GIF
	
![videotogif_2017 09 28_14 39 46](https://user-images.githubusercontent.com/24502136/30959211-132d06d8-a45d-11e7-82ce-440d7e1ef9a9.gif)


```
For stopping the progress just call stopProgressing() method of the custom view.
```

### XML
```xml
<com.example.customprogressbar.customView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:id="@+id/customBar"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        app:Image="@drawable/microphone"
        app:label="wait"
        app:main_circle_color="#097054"
        app:label_size="80"
        app:stroke_width="20"
        app:rotatingInverseRate="200"
        app:sweep_angle="50"
        app:label_color="#FF9900"
        app:rotating_bar_color="#6599FF" />
```

### Java
```java
 	customView myview = (customView) findViewById(R.id.customBar);
        myview.setImageID(R.drawable.microphone);
        myview.setRotatingInverseRate(200);
        myview.setRotatingBarColor(Color.parseColor("#6599FF"));
        myview.setStrokeWidth(20);
        myview.setSweepAngle(50);
        myview.setLabel("wait");
        myview.setLabelSize(40);
        myview.setLabelColor(Color.parseColor("#FF9900"));
        myview.setMainCircleColor(Color.parseColor("#097054"));
```

# License
<b>CustomCircularProgressBar</b> is licensed under `MIT license`. View [license](LICENSE.md).
