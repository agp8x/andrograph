# andrograph
Display and edit [JGraphT](https://github.com/jgrapht/jgrapht) graphs on android

## installation

1. Head to "Releases" and grab latest AAR
2. Add to Android Studio as "New Module -> Import .JAR/.AAR"
3. Edit your apps build.gradle to add compile project(':andrograph-release') as dependency

## usage

* Have a JGraphT graph
* Add a GraphView to an Activity
* Build a GraphViewController
* Attach GraphViewController to GraphView
* Enjoy editing

## select edges

DefaultGraphViewController can be equipped with an EdgeEvent instance.
This will be called before edges are updated, i.e. before adding and removing edges.
By consuming the event (return true), no further actions will be done.
By not consuming the event (return false), the default action will be performed.

This can be used e.g. to catch selection of edges, store it somehow, and paint it differently (via reference to an EdgePaintProvider)

## architecture
![diagram](https://cloud.githubusercontent.com/assets/1840171/20796563/f1ec79a4-b7d6-11e6-8f24-20b7b5b4a0f5.png)

## credits
* with support from [CaptBlackTea](https://github.com/CaptBlackTea)
