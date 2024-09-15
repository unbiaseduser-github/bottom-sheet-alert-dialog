[![jitpack badge](https://jitpack.io/v/unbiaseduser-github/bottom-sheet-alert-dialog.svg)](https://jitpack.io/#unbiaseduser-github/bottom-sheet-alert-dialog)

<h1 align="center">Bottom Sheet Alert Dialog</h1>

<p align="center">
AlertDialog-style bottom sheet dialogs
</p>
<br>

## Usage
The "main entry point" of the library is the `BaseDialogBuilder` and its two concrete subclasses,
`BottomSheetAlertDialogBuilder` and `BottomSheetAlertDialogFragmentViewBuilder`.

Note that `setXButton` is a wildcard for `set[Positive/Negative/Neutral]Button`.
### BottomSheetAlertDialogBuilder
#### Kotlin
```kotlin
BottomSheetAlertDialogBuilder(context)
    .setTitle("Title")
    .setContentView(BottomSheetAlertDialogContentView.unmodified(view))
    .setXButton(
        text = "Button text",
        listener = { Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show() },
        dismissAfterClick = false
    )
    .show()
```
#### Java
```java
new BottomSheetAlertDialogBuilder(context)
    .setTitle("Title")
    .setContentView(BottomSheetAlertDialogContentView.unmodified(view))
    .setXButton(new DialogButtonProperties.Builder("Button text")
        .setOnClickListener(() -> Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show())
        .disableDismissAfterClick()
        .build())
    .show();
```

### BottomSheetAlertDialogFragmentViewBuilder
#### Kotlin
```kotlin
class MyFragment : BottomSheetDialogFragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return BottomSheetAlertDialogFragmentViewBuilder(this, requireContext())
            .setTitle("Title")
            .setContentView(BottomSheetAlertDialogContentView.unmodified(view))
            .setXButton(
                text = "Button text",
                listener = { Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show() },
                dismissAfterClick = false
            )
            .rootView
    }
    
}
```
#### Java
```java
public class MyFragment extends BottomSheetDialogFragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return new BottomSheetAlertDialogFragmentViewBuilder(this, requireContext())
                .setTitle("Title")
                .setContentView(BottomSheetAlertDialogContentView.unmodified(view))
                .setXButton(new DialogButtonProperties.Builder("Button text")
                        .setOnClickListener(() -> Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show())
                        .disableDismissAfterClick()
                        .build())
                .getRootView();
    }
    
}
```

## Stuff that might feel "out of place"

### Prevent dismiss after button click
What's the `dismissAfterClick` parameter passed into the `setXButton` methods (or `DialogButtonProperties.Builder.disableDismissAfterClick`),
you ask? It prevents the dialog from dismissing when `false` is passed in - in which case you must
dismiss it yourself. How to do so depends on the scenario:

#### BottomSheetAlertDialogBuilder
Create a `BottomSheetDialog`, hold a reference to it and use the constructor that takes it as a parameter.
When it's time to dismiss the dialog, call `dismiss()` on the referenced dialog.
```kotlin
val dialog = BottomSheetDialog(context)
BottomSheetAlertDialogBuilder(context, dialog)
    .setXButton(
        text = "Button text",
        listener = { dialog.dismiss() },
        dismissAfterClick = false
    )
    .show()
```

#### BottomSheetAlertDialogFragmentViewBuilder
Simply call `BottomSheetDialogFragment.dismiss()`.

### Content view handling
What's the `setContentView(BottomSheetAlertDialogContentView.unmodified(view))` all about? The library
allows you to pass in either a unmodified view (like above), or a view that will be wrapped in a
`NestedScrollView`. To achieve the latter, call `BottomSheetAlertDialogContentView.verticallyScrollable(view)` instead.

### `doActions` and the `BottomSheetAlertDialogActions` object
You might have noticed that there's a `doActions()` method that gives you a `BottomSheetAlertDialogActions`
object. That object allows limited access to the dialog root UI - those that (hopefully) won't break intended behavior
if abused. If you need more control, simply create a subclass of a builder class - they expose a
`BottomSheetAlertDialogUi` object that gives somewhat more flexibility.

(I didn't showcase it above since it's not something that can be easily described by a single code snippet.)

### Dialog's default expanded/collapsed state
By default, both concrete builder classes call `expandDialog()` when initialized, which is a shorthand for
`dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED`. If you want the stock behavior,
create a subclass of a builder class and call `collapseDialog()` (or `dialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED`)
when initialized.