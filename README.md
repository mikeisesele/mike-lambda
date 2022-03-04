#  multi library <br>
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Generic library to provide base functionality for faster developement<br>

## Include in your project
[![JitPack](https://jitpack.io/v/skydoves/BaseRecyclerViewAdapter.svg)](https://jitpack.io/#CodaarX/mike-lambda/1.0.0)</br>


#### Gradle
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
     implementation 'com.github.CodaarX:mike-lambda:1.0.2'
}
```


In any context, A call to the Mike keyword will provide functionalities to work with as the IDE auto complete will reveal.

## MikeUtils Usage

1. Toast

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        
        MikeUtils.toast(this, "Toast Message")
    }

}
```

2. Log 

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

         MikeUtils.log(T) //  the log call is a generic and takes in any data type. Strings, Array, custom objects etc.
    }

}
```

3. Navigate to activity 

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // this uses the inline reified function to navgate to any activity at run time.

        // use this to send data via intent to destination activity

        // put all required info in the bundle object (data)
        val bundle = Bundle().apply { 
            // put data in bundle here
        }

        
        // pass the data as the third parameter to the method
        MikeUtils.navigateToActivity(this, DestinationActivity::class.java, bundle)
         
        // use this to navigate to destination activity without sending data
        MikeUtils.navigateToActivity(this, DestinationActivity::class.java, null)
    }
}
```

4 Ask for permission

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // functionality only request for a single permission
        // message is a message to be displayed if the permission was denied.
        MikeUtils.askPermission(Manifest.permission.ACCESS_FINE_LOCATION, this, "Message")
        MikeUtils.askPermission(PERMISSION_NAME_HERE), this, "Message")
        
        // this also works
         MikePermission.askPermission(Manifest.permission.ACCESS_FINE_LOCATION, this, "Message")
    }
}
```

### Set Image to views

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

         MikeGlide.setImage(view: ImageVIew, ImageURL: String) // provide the image view, and the string url of the image
    }
}
```

### Perform IO operations

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

         Mike_IoExecutor.runOnIoThread {
            // some function to invoke in IO operation using the newSingleThreadExecutor() method of the Execitors class 
            // Executors.newSingleThreadExecutor()
        }
    }
}
```

### Generic Dialogue Builder

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

         MikeGenericDialogueBuilder.showDialogue(
            this, // context
            "Dialogue Title", 
            "Dialogue message",
            "Positive button name",
            "Negative button name",
            posiiveButtonClickHandler(), // function to invoke positive response
            negativeButtonClickHandler() // function to invoke negative response
        )
    }
}
```

### Sealed class - Network request state manager - no need to create resource class yourself

```kotlin

class ProgrammingJokesUseCaseImpl  @Inject constructor(private val jokesRepository: JokesRepository) : ProgrammingJokesUseCase, BaseRepositoryRemoteOperation() {


    override suspend fun invoke(): Flow<MikeResource<JokesModelSafe>> =

        flow {
            emit(
                try {
                    MikeResource.Loading<String>("Loading")               // wrap loading state in loading class
                    
                    val response = jokesRepository.getProgrammingJoke()
                    val result = response.body()

                    if (result != null && response.isSuccessful) {
                        MikeResource.Success(result.toJokesModelSafe())   // wrap success response in success class
                    } else {
                        MikeResource.Error(response.message())            // wrap error message in error class
                    }
                } catch (e: Exception) {
                    MikeResource.Error(e.message ?: "an error occurred")  // wrap error message in error class
                }
            )
        }
    }
}
```

### Network State Observer

1. Initialize with application class - Method 1
```kotlin
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MikeNetworkLiveData.init(this)   // network live data initialization  Method 1
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      // no need to initialize network live data here
    }

    override fun onResume() {
        super.onResume()

        if( MikeNetworkLiveData.isNetworkAvailable() ) {
            // perform network operation here
        }
        
         // OR        
        
        // observe the network directly
       MikeNetworkLiveData.observe(this) {
            if (it) {
                // perform some operations here
            }
       }
    }
}
```

2. Initialize with application context in main activity - Method 2
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MikeNetworkLiveData.init(applicationContext as Application)    // network live data initialization  Method 2
    }
    
    override fun onResume() {
        super.onResume()

        if( MikeNetworkLiveData.isNetworkAvailable() ) {
            // perform network operation here
        }
        
        // OR
        
        // observe the network directly
       MikeNetworkLiveData.observe(this) {
            if (it) {
                // perform some operations here
                }
            }
        }
    }
}
```

# License
```xml
Copyright 2018 skydoves

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
