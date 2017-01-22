# andrograph
Display and edit [JGraphT](https://github.com/jgrapht/jgrapht) graphs on android

## Installation

### As dependency

1. Save AAR as ${project}/app/libs/andrograph.aar
2. project/build.gradle (excerpt):
```gradle
allprojects {
	repositories {
		//your repos here
		flatDir{
            dirs 'libs'
        }
    }
}
```
3. project/app/build.gradle (excerpt):
```gradle
dependencies {
	//your dependencies here
	compile 'org.jgrapht:jgrapht-core:0.9.2'
    compile(name:'andrograph', ext:'aar')
}
```

### As module

1. Head to "Releases" and grab latest AAR
2. Add to Android Studio as "New Module -> Import .JAR/.AAR"
3. Edit your apps build.gradle to add compile project(':andrograph-release') as dependency

## Usage

* Have a JGraphT graph
* Add a GraphView to an Activity
* Build a GraphViewController
* Attach GraphViewController to GraphView
* Enjoy editing

## Select edges

DefaultGraphViewController can be equipped with an EdgeEvent instance.
This will be called before edges are updated, i.e. before adding and removing edges.
By consuming the event (return true), no further actions will be done.
By not consuming the event (return false), the default action will be performed.

This can be used e.g. to catch selection of edges, store it somehow, and paint it differently (via reference to an EdgePaintProvider)

## Architecture
![diagram](https://cloud.githubusercontent.com/assets/1840171/20796563/f1ec79a4-b7d6-11e6-8f24-20b7b5b4a0f5.png)

## Credits
* with support from [CaptBlackTea](https://github.com/CaptBlackTea)
