package org.techtown.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    ProcessThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String input = editText.getText().toString(); //입력상자에 글자를 넣고 버튼을 누르면
                Message message = Message.obtain();
                message.obj =input;   //문자열을 포함시켜 전송하기 위해, Message객체의 obj 변수에 할당

                thread.processHandler.sendMessage(message); // 새로 만든 스레드 안에 있는 핸들러로 메시지 전송하기

            }
        });

        thread = new ProcessThread();
    }
    class ProcessThread extends Thread{
        ProcessHandler processHandler = new ProcessHandler();

        public void run(){
            Looper.prepare();
            Looper.loop();
        }

        class ProcessHandler extends Handler{
            public void handleMessage(Message msg){
                final String output = msg.obj+ "from thread"; //새로 만든 스레드 안에서 전달받은 메시지 처리하기

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(output);
                    }
                });
            }
        }
    }
}
