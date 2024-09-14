package com.sixtyninefourtwenty.bsadplayground

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogBuilder
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogFragmentViewBuilder
import com.sixtyninefourtwenty.bottomsheetalertdialog.DialogButtonProperties
import com.sixtyninefourtwenty.bottomsheetalertdialog.misc.ViewBindingBottomSheetAlertDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.misc.createBottomSheetAlertDialog
import com.sixtyninefourtwenty.bsadplayground.databinding.TextWithRecyclerViewBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.show_short_dialog).setOnClickListener {
            BottomSheetAlertDialogBuilder(TextView(this).apply {
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            }).setTitle("Title")
                .setPositiveButton(DialogButtonProperties(text = "OK"))
                .show()
        }

        findViewById<Button>(R.id.show_long_dialog).setOnClickListener {
            BottomSheetAlertDialogBuilder(TextView(this).apply {
                text = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent scelerisque nisi ut odio consectetur congue. Sed ullamcorper tempus tellus finibus vulputate. Donec auctor velit orci, a sollicitudin felis dapibus nec. Pellentesque rutrum magna a urna consectetur, at faucibus sem placerat. Quisque rhoncus, nunc vitae viverra finibus, dui massa condimentum elit, vitae facilisis arcu nisl eget orci. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum sagittis molestie sem. Cras sed nulla faucibus, sollicitudin magna eget, finibus neque. Nulla vulputate, nulla fermentum ullamcorper congue, elit ante facilisis sem, vel congue mi lectus id sem.

                    Aenean faucibus rutrum imperdiet. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas nec leo vitae mauris laoreet aliquet vel sed sapien. Cras hendrerit nisl lectus, et malesuada lectus condimentum ornare. Donec pellentesque dui ut odio commodo molestie. Ut et eros tempus, faucibus nulla et, vulputate ligula. Etiam suscipit massa id elit ultrices euismod sed id nibh. Fusce elementum non nunc non scelerisque. Integer elementum ex eget interdum gravida. Cras risus lectus, auctor sed enim id, lacinia ultricies ex. Phasellus ac velit in felis semper mollis in ac odio. In vehicula nisl vitae porttitor ultricies. Cras sollicitudin est purus, sit amet congue leo sagittis eget. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc porta lectus ut massa egestas porta.

                    Vivamus ultrices nec lorem eu convallis. Pellentesque eget sem lacinia, egestas magna hendrerit, euismod elit. Praesent placerat ligula ut egestas aliquam. Morbi lobortis purus ac erat blandit, vitae interdum enim ultrices. Ut eget dapibus tortor. Etiam nisi enim, condimentum eu laoreet in, finibus tempus erat. In ut nisl nulla. Aenean rutrum porta eros quis suscipit. Duis mollis ligula quam, maximus convallis quam venenatis sit amet. Ut feugiat, nunc ut eleifend ornare, dolor leo dignissim magna, vel ultrices dolor risus sit amet turpis. Sed mauris nulla, tempor in orci eget, imperdiet consequat lectus. Donec sed accumsan augue. Praesent odio augue, condimentum eu purus sit amet, vehicula posuere nunc. Curabitur eu vulputate elit. 
                    
                    Etiam elementum sem sit amet est facilisis consectetur. Suspendisse potenti. Vivamus non arcu vel eros bibendum molestie nec in elit. In rhoncus vulputate risus, sed porta magna hendrerit in. In nec imperdiet ligula. Nam at ante gravida, efficitur purus sed, egestas quam. Nullam tincidunt ex nibh, in feugiat ex mollis vel. Aliquam ac velit consectetur, dictum justo eu, euismod lorem. Integer ultricies dolor id tincidunt laoreet. Nam blandit ex tortor, ac eleifend felis commodo nec.

                    Phasellus commodo viverra maximus. Suspendisse potenti. Sed dapibus ullamcorper facilisis. Aliquam nulla mauris, rhoncus ac feugiat sit amet, iaculis non turpis. Quisque placerat, urna id sagittis lacinia, dui lectus fringilla velit, id ornare dolor tortor ut ipsum. In et porttitor urna, et ultrices purus. Integer ut turpis ullamcorper nunc pulvinar venenatis. Nullam blandit ligula vitae tincidunt auctor. Nulla quis luctus nibh, et rhoncus nibh. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nunc tincidunt dolor tempor diam venenatis accumsan. Nam justo enim, ultrices sit amet sodales ac, porttitor et sem. 
                """.repeat(10).trimIndent()
            }).setTitle("Title")
                .setPositiveButton(text = "OK")
                .setNegativeButton(text = "Lorem ipsum dolor sit amet")
                .show()
        }

        /*findViewById<Button>(R.id.show_long_dialog).setOnClickListener {
            val dialogBinding = CollapsibleLongContentBinding.inflate(layoutInflater)
            dialogBinding.text.text = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent scelerisque nisi ut odio consectetur congue. Sed ullamcorper tempus tellus finibus vulputate. Donec auctor velit orci, a sollicitudin felis dapibus nec. Pellentesque rutrum magna a urna consectetur, at faucibus sem placerat. Quisque rhoncus, nunc vitae viverra finibus, dui massa condimentum elit, vitae facilisis arcu nisl eget orci. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum sagittis molestie sem. Cras sed nulla faucibus, sollicitudin magna eget, finibus neque. Nulla vulputate, nulla fermentum ullamcorper congue, elit ante facilisis sem, vel congue mi lectus id sem.

                    Aenean faucibus rutrum imperdiet. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas nec leo vitae mauris laoreet aliquet vel sed sapien. Cras hendrerit nisl lectus, et malesuada lectus condimentum ornare. Donec pellentesque dui ut odio commodo molestie. Ut et eros tempus, faucibus nulla et, vulputate ligula. Etiam suscipit massa id elit ultrices euismod sed id nibh. Fusce elementum non nunc non scelerisque. Integer elementum ex eget interdum gravida. Cras risus lectus, auctor sed enim id, lacinia ultricies ex. Phasellus ac velit in felis semper mollis in ac odio. In vehicula nisl vitae porttitor ultricies. Cras sollicitudin est purus, sit amet congue leo sagittis eget. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc porta lectus ut massa egestas porta.

                    Vivamus ultrices nec lorem eu convallis. Pellentesque eget sem lacinia, egestas magna hendrerit, euismod elit. Praesent placerat ligula ut egestas aliquam. Morbi lobortis purus ac erat blandit, vitae interdum enim ultrices. Ut eget dapibus tortor. Etiam nisi enim, condimentum eu laoreet in, finibus tempus erat. In ut nisl nulla. Aenean rutrum porta eros quis suscipit. Duis mollis ligula quam, maximus convallis quam venenatis sit amet. Ut feugiat, nunc ut eleifend ornare, dolor leo dignissim magna, vel ultrices dolor risus sit amet turpis. Sed mauris nulla, tempor in orci eget, imperdiet consequat lectus. Donec sed accumsan augue. Praesent odio augue, condimentum eu purus sit amet, vehicula posuere nunc. Curabitur eu vulputate elit. 
                    
                    Etiam elementum sem sit amet est facilisis consectetur. Suspendisse potenti. Vivamus non arcu vel eros bibendum molestie nec in elit. In rhoncus vulputate risus, sed porta magna hendrerit in. In nec imperdiet ligula. Nam at ante gravida, efficitur purus sed, egestas quam. Nullam tincidunt ex nibh, in feugiat ex mollis vel. Aliquam ac velit consectetur, dictum justo eu, euismod lorem. Integer ultricies dolor id tincidunt laoreet. Nam blandit ex tortor, ac eleifend felis commodo nec.

                    Phasellus commodo viverra maximus. Suspendisse potenti. Sed dapibus ullamcorper facilisis. Aliquam nulla mauris, rhoncus ac feugiat sit amet, iaculis non turpis. Quisque placerat, urna id sagittis lacinia, dui lectus fringilla velit, id ornare dolor tortor ut ipsum. In et porttitor urna, et ultrices purus. Integer ut turpis ullamcorper nunc pulvinar venenatis. Nullam blandit ligula vitae tincidunt auctor. Nulla quis luctus nibh, et rhoncus nibh. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nunc tincidunt dolor tempor diam venenatis accumsan. Nam justo enim, ultrices sit amet sodales ac, porttitor et sem. 
                """.repeat(10).trimIndent()
            dialogBinding.toggle.setOnClickListener {
                dialogBinding.text.isVisible = !dialogBinding.text.isVisible
            }
            BottomSheetAlertDialogBuilder(dialogBinding.root, isContentViewHeightDynamic = true).setTitle("Title")
                .setPositiveButton(DialogButtonProperties(text = "OK"))
                .setNegativeButton(text = "Lorem ipsum dolor sit amet")
                .show()
        }*/

        //MyFragment().show(supportFragmentManager, null)

    }

    class MyFragment : ViewBindingBottomSheetAlertDialogFragment<TextWithRecyclerViewBinding>(TextWithRecyclerViewBinding::inflate) {

        override fun createDialog(binding: TextWithRecyclerViewBinding): BottomSheetAlertDialogFragmentViewBuilder {
            return createBottomSheetAlertDialog(
                view = binding.root,
                titleText = "Title",
                positiveButtonProperties = DialogButtonProperties(text = "OK")
            )
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val binding = requireBinding()
            binding.list.layoutManager = LinearLayoutManager(requireContext())
            binding.list.adapter = MyAdapter()
        }

        private class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

            private val items = List(10) {
                """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent scelerisque nisi ut odio consectetur congue. Sed ullamcorper tempus tellus finibus vulputate. Donec auctor velit orci, a sollicitudin felis dapibus nec. Pellentesque rutrum magna a urna consectetur, at faucibus sem placerat. Quisque rhoncus, nunc vitae viverra finibus, dui massa condimentum elit, vitae facilisis arcu nisl eget orci. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum sagittis molestie sem. Cras sed nulla faucibus, sollicitudin magna eget, finibus neque. Nulla vulputate, nulla fermentum ullamcorper congue, elit ante facilisis sem, vel congue mi lectus id sem.

                    Aenean faucibus rutrum imperdiet. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas nec leo vitae mauris laoreet aliquet vel sed sapien. Cras hendrerit nisl lectus, et malesuada lectus condimentum ornare. Donec pellentesque dui ut odio commodo molestie. Ut et eros tempus, faucibus nulla et, vulputate ligula. Etiam suscipit massa id elit ultrices euismod sed id nibh. Fusce elementum non nunc non scelerisque. Integer elementum ex eget interdum gravida. Cras risus lectus, auctor sed enim id, lacinia ultricies ex. Phasellus ac velit in felis semper mollis in ac odio. In vehicula nisl vitae porttitor ultricies. Cras sollicitudin est purus, sit amet congue leo sagittis eget. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc porta lectus ut massa egestas porta.

                    Vivamus ultrices nec lorem eu convallis. Pellentesque eget sem lacinia, egestas magna hendrerit, euismod elit. Praesent placerat ligula ut egestas aliquam. Morbi lobortis purus ac erat blandit, vitae interdum enim ultrices. Ut eget dapibus tortor. Etiam nisi enim, condimentum eu laoreet in, finibus tempus erat. In ut nisl nulla. Aenean rutrum porta eros quis suscipit. Duis mollis ligula quam, maximus convallis quam venenatis sit amet. Ut feugiat, nunc ut eleifend ornare, dolor leo dignissim magna, vel ultrices dolor risus sit amet turpis. Sed mauris nulla, tempor in orci eget, imperdiet consequat lectus. Donec sed accumsan augue. Praesent odio augue, condimentum eu purus sit amet, vehicula posuere nunc. Curabitur eu vulputate elit. 
                    
                    Etiam elementum sem sit amet est facilisis consectetur. Suspendisse potenti. Vivamus non arcu vel eros bibendum molestie nec in elit. In rhoncus vulputate risus, sed porta magna hendrerit in. In nec imperdiet ligula. Nam at ante gravida, efficitur purus sed, egestas quam. Nullam tincidunt ex nibh, in feugiat ex mollis vel. Aliquam ac velit consectetur, dictum justo eu, euismod lorem. Integer ultricies dolor id tincidunt laoreet. Nam blandit ex tortor, ac eleifend felis commodo nec.

                    Phasellus commodo viverra maximus. Suspendisse potenti. Sed dapibus ullamcorper facilisis. Aliquam nulla mauris, rhoncus ac feugiat sit amet, iaculis non turpis. Quisque placerat, urna id sagittis lacinia, dui lectus fringilla velit, id ornare dolor tortor ut ipsum. In et porttitor urna, et ultrices purus. Integer ut turpis ullamcorper nunc pulvinar venenatis. Nullam blandit ligula vitae tincidunt auctor. Nulla quis luctus nibh, et rhoncus nibh. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nunc tincidunt dolor tempor diam venenatis accumsan. Nam justo enim, ultrices sit amet sodales ac, porttitor et sem. 
                """.repeat(10).trimIndent()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(TextView(parent.context))
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.view.text = items[position]
            }

            override fun getItemCount(): Int = items.size

            private class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {

            }

        }
    }

}