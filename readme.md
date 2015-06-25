# android-101

learning android app dev (again!) from [a tutorial at raywenderlich.com](http://www.raywenderlich.com/78576/android-tutorial-for-beginners-part-2) and [part 3](http://www.raywenderlich.com/78578/android-tutorial-for-beginners-part-3).

## random notes taken.

- created an app from "Blank Activity" template.
- there are 3 main files here: main class (java), layout (xml), and strings (xml).
- `R` is magic to me.
- `MainActivity.java` has `onCreate` method which is executed when the app launches.
- comparing with another tutorial on attaching `onclick` event listener to a button

```java
// leveluptuts
Button mainButton = (Button) findViewById(R.id.button);
mainButton.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		// method content
	}
});
```

another way to do it is making `MainActivity` implements `View.OnClickListener` interface and create `onClick` event listener in `MainActivity` class itself.

```java
// raywenderlich.com
class MainActivity implements View.OnClickListener {

	// onCreate
	Button mainButton = (Button) findViewById(R.id.button);
	mainButton.setOnClickListener(this);

	// then
	@Override
	public void onClick(View v) {
		// method content
	}
}
```

i like the second one more.

- 1) create a class property for each element in the layout (with proper class)
- 2) grab the element from the layout in `onCreate` method: `mainListView = (ListView) findViewById(R.id.main_listview)`.
- 3) do something with it

### Layouts

- `<LinearLayout>` is easier to understand (to me at least) than the default `<RelativeLayout>`. (or it is just getting started?)
- `TextView` -> `setText()`
- `EditView` -> `getText()`
- `getText()` needs `toString()` -> `mainText.getText().toString()`.
- like in objective-c, comparing strings use: `.equals()` to compare their values.

- `ImageView`: i never got its position right until now. never.
- `ListView` in layout xml doesn't look scary at all.
- but in the main activity, it's becoming picky now.
- we need to have an `ArrayAdapter` as a bridge between our dataset (array in java) with the actual `<ListView>` in the view.
- updating the list view: 1) modify data set (source array). 2) call `.notifyDataSetChanged()` method on ArrayAdapter instance.

- adding event listener to an item in `<ListView>` needs interface `AdapterView.OnItemClickListener`
- `setOnItemClickListener` is bound to each items in `<ListView>`

### Intents

- 1) grab the menu item from menu xml
- 2) add `Intent.ACTION_SEND` to the menu items
- 3) let android does the job adding all available options
- output on an actual device is not the same with in the emulator
- last-used share option is placed next to menu item in menu bar. i think it's a bit off.

### SharedPreferences

- the concept is like localStorage in the web world.
- key-value, stored in a file somewhere.
- `Toast` is that cool overlay message.
- we create a dialog on the fly when there is no user's name stored in SharedPreferences.
- we also create view elements on the fly and put them in the created dialog.
- `alert.setPositiveButton` and `alert.setNegativeButton` are event listener methods for OK and Cancel buttons
- now i see the use of closure-style event listener (or whatever it is named)
  - many click listeners have the same method name which is `onClick`
  - they are just belongs to different interfaces.
  - we cannot have the same method names in our main class.
  - that's why we have to put `onClick` methods inline to each click listener

(i don't like java. just fyi. (5 years ago i would say _i hate java_). but it seems to be the only choice here.)