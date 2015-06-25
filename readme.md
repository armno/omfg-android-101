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

## Creating an actual app: bookmaster

- app icon can be set in `AndroidManifest.xml` with attribute `android:icon` on `<application>` tag.
- Gradle is a build tool in android world. It also does dependencies management.
- Installing new dependencies: add package name and version in `build.gradle` fi.le. then Sync Project with Gradle Files.
- `URLEncode` throws an exception when i forgot to add `permission.INTERNET` in the manifest file.
- `<uses-permission>` is placed before closing `</manifest>`

### HTTP requests

- `AsyncHttpClient` is the class used.
- 1) create `client` 2) call method `.get`. Actually the API is somehow like in JavaScript world. `client.get(url, callback)` unless `callback` here is an object but anyway, with success and failure response handlers.
- `getApplicationContext()` reminds me of `this` in javascript world. i think `this` is used in normal methods while `getApplicationContext` is used in callback methods. (please correct me if i'm wrong)

### Layouts

- i was too sleepy when i coded along this section. summarizing this note again helped me to understand RelativeLayout more. reading through each attributes in elements makes more sense now.
- we create a RelativeLayout for each row of books. i think of RelativeLayout is a self-contained piece of view where each element in the view doesn't have to care outside world.
- thumbnail + title + description is a perfect example.
- root element of RelativeLayout is `<RelativeLayout>` of course.
- inside of `<RelativeLayout>` we can UI elements just like with LinearLayout, the differences are:
		- we set `height` and `width` specifically for some elements
		- `android:layout_*` attributes are now relative ones, for example, `android:layout_alignCenterVertical` makes the element vertically centered relatively to the parent. while `_alignParentLeft` makes the element aligned to the left of its parent.
- `scaleType=centerInside` makes the element scale of its center. (`transform-origin` prop in css?)

- then we add a `<TextView>` and tell it to stay at the right of the thumbnail (referenced by thumnail's `id` attribute). also align-top to the thumbnail relatively as well.
- lastly we have author's name in a `<TextView>` relatively to the book's name (2nd item). since we set the layout based on the book's name, there is no need to set `marginLeft` of this element.
- so `id` attribute is really important in RelativeLayout world. also we need to make sure that there is no circular references of `id`s in the layout.
- (the word `inflate` is new to me. it means ขยาย in thai)

### putting JSON into new row layout

- we create new adapter class called `JSONAdapter` (extends `BaseAdapter`) because we want to map raw JSON to the new layout. (wondering if android doesn't have this built in like `ArrayAdapter` class...)
- properties: `Context` use with Picasso image loader, `LayoutInflater` communicates with the view, `JSONArray` for the datasource.
- then we create a subclass(?) inside our `JSONAdapter` called `ViewHolder` that will be reused over and over when scrolling. so we don't create too many views.
- in `getView` method, we create a new view to ViewHolder if it doesn't exist yet (using `findViewById` and all that). then we call `setTag` method to put the ViewHolder instance for future use.
- `getView` is also where we attach `row_layout.xml` to the view inflator.

- we check if the json row has cover image, then we use Picasso to fetch the image from the URL. otherwise just use the placeholder thumbnail image we have in `res` folder.
- Picasso fetches images in a separated thread with UI thread so it doesn't block the UI.

### dialogs

- `ProgressDialog` it is.
- create a dialog instance in `onCreate` method. call dialog `.show()` and `dismiss()` at ease.