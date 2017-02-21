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
### Interfaces
![diagram](https://cloud.githubusercontent.com/assets/1840171/23174786/cd987b6c-f85d-11e6-9cc8-1e24dcbdd8b9.png)

### Defaults
![diagram2](https://cloud.githubusercontent.com/assets/1840171/23174878/0b411b72-f85e-11e6-9057-15f59d8e7ade.png)

## Credits
* with support from [CaptBlackTea](https://github.com/CaptBlackTea)
