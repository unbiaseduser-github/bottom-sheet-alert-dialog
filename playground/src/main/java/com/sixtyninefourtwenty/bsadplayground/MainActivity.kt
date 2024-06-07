package com.sixtyninefourtwenty.bsadplayground

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogBuilder
import com.sixtyninefourtwenty.bottomsheetalertdialog.DialogButtonProperties

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
                """.trimIndent()
            }).setTitle("Title")
                .setPositiveButton(DialogButtonProperties(text = "OK"))
                .setNegativeButton(text = "Lorem ipsum dolor sit amet")
                .show()
        }

    }
}