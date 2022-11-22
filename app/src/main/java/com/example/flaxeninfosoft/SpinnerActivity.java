package com.example.flaxeninfosoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;

import com.example.flaxeninfosoft.databinding.ActivitySpinnerBinding;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {
    ActivitySpinnerBinding binding;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void  init(){
        Random random = new Random();

        binding.btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disabling the button so that user
                // should not click on the button
                // while the wheel is spinning
                binding.btnSpin.setEnabled(false);

                // reading random value between 10 to 30
                int spin = random.nextInt(20)+10;

                // since the wheel has 10 divisions, the
                // rotation should be a multiple of
                // 360/10 = 36 degrees
                spin = spin * 36;

                // timer for each degree movement
                timer = new CountDownTimer(spin*20,1) {
                    @Override
                    public void onTick(long l) {
                        // rotate the wheel
                        float rotation = binding.ivWheel.getRotation() + 2;
                        binding.ivWheel.setRotation(rotation);
                    }

                    @Override
                    public void onFinish() {
                        // enabling the button again
                        binding.btnSpin.setEnabled(true);
                    }
                }.start();

            }
        });

        EmojiPopup popup = EmojiPopup.Builder.
                fromRootView(findViewById(R.id.root_view))
                .build(binding.etEmoji);

        binding.btnEmojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.toggle();
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmojiTextView emojiTextView = (EmojiTextView) LayoutInflater
                        .from(getApplicationContext())
                        .inflate(R.layout.emoji_text_view,binding.rootView,false);
                emojiTextView.setText(binding.etEmoji.getText().toString());

             //   binding.tvShowI.setText((CharSequence) emojiTextView);
                binding.rootView.addView(emojiTextView);

                binding.etEmoji.getText().clear();
            }
        });

    }
}