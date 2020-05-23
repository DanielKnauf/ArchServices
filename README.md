# ArchServices

## Description
The underlying architecture concept of _ArchServices_ is to keep `Activities` and `Fragments` as small and clean as possible. The `ViewModel` should handle all logic (even lifecycle events) or  delegate it to another class. 
From this premise, base classed for `Activity` and `Fragment` were created which handle the _Mvvm_-setup (e.g. connect layout to viewmodel) and forward events to the `ViewModel` (e.g. onBackPressed or the bundle of onCreate). Also `Micro Services` for different functionalities were created which can easily be injected into the `ViewModel` via _Dagger2_. 

## How to use it

The latest `master` version can be received as a dependency from [jitpack.io](https://jitpack.io): 
```
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}

dependencies {
   implementation 'com.github.DanielKnauf:archservices:master-SNAPSHOT'
}
```
## Used in
* [Simple Timer App](https://github.com/DanielKnauf/SimpleTimer)
* [WiP] [b2u](https://github.com/DanielKnauf/b2u)


## License
```
Copyright (C) 2019 Daniel Knauf

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
