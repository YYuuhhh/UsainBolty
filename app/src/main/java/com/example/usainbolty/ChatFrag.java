package com.example.usainbolty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ChatFrag extends Fragment {
    private SpeechRecognizer speechRecognizer;
    public int checker = 0;
    public int checker2 = 0;
    private RecyclerView chatsRV;
    private FloatingActionButton sendMsgIB;
    private FloatingActionButton help;
    private FloatingActionButton rec;
    private CardView cardFaq;
    private TextView txtfq1;
    private TextView txtfq2;
    private TextView txtfq3;
    private TextView txtfq4;
    private TextView txtfq5;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";


    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.chat_frag, container, false);
        chatsRV = root.findViewById(R.id.idRVChats);
        txtfq1=root.findViewById(R.id.faqTxt1);
        txtfq2=root.findViewById(R.id.faqTxt2);
        txtfq3=root.findViewById(R.id.faqTxt3);
        txtfq4=root.findViewById(R.id.faqTxt4);
        txtfq5=root.findViewById(R.id.faqTxt5);
        rec=root.findViewById(R.id.micro);
        cardFaq = root.findViewById(R.id.cardochka);
        help=root.findViewById(R.id.help);
        sendMsgIB = root.findViewById(R.id.idIBSend);
        userMsgEdt = root.findViewById(R.id.idEdtMessage);
       speechRecognizer=SpeechRecognizer.createSpeechRecognizer(getContext());
        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru_RU");
        final TapTargetSequence sequence = new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(help, "FAQ", "При нажатии на виджет, вы сможете выбрать, на какой частозадаваемый вопрос вы хотите узнать ответ")
                                .outerCircleColor(R.color.gray)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(30)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .cancelable(true)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(40)
                                .id(1),
                        TapTarget.forView(sendMsgIB,"Отправка сообщения","Если вы не нашли свой вопрос в списке частозадаваемых, то можете написать и отправить свой вопрос напрямую")
                                .outerCircleColor(R.color.gray)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(30)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .cancelable(true)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(40)
                                .id(2)


                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                    }


                });
        sequence.start();




        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.getCache().clear();


        messageModalArrayList = new ArrayList<>();


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker==0) {
                    cardFaq.setVisibility(v.VISIBLE);
                    checker=1;
                }
                else{
                    cardFaq.setVisibility(v.INVISIBLE);
                    checker=0;
                }




            }
        });

        txtfq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cardFaq.setVisibility(v.INVISIBLE);
                sendMessage("Как записаться ко врачу?");




            }
        });

        txtfq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardFaq.setVisibility(v.INVISIBLE);
                sendMessage("Не вижу своего местоположения на карте");




            }
        });
        txtfq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardFaq.setVisibility(v.INVISIBLE);
                sendMessage("Не работает счетчик электромагнитной индукции");




            }
        });
        txtfq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardFaq.setVisibility(v.INVISIBLE);
                sendMessage("Как можно провести дистанционную консультацию со врачом?");




            }
        });
        txtfq5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardFaq.setVisibility(v.INVISIBLE);
                sendMessage("Расскажи шутку");




            }
        });



        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (userMsgEdt.getText().toString().isEmpty()) {

                    Toast.makeText(getContext(), "Введите свой вопрос", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to send message
                // to our bot to get response.
                sendMessage(userMsgEdt.getText().toString());

                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });



        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checker2==0){
                     speechRecognizer.startListening(speechRecognizerIntent);
                    checker2 =1;
                }
                else{
                    speechRecognizer.stopListening();
                    checker2=0;
                }
            }
        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.d("qwqwe","1");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("qwqwe","2");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle results) {

          ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
          Log.d("fdfdfdfd",data.toString());
                userMsgEdt.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        // on below line we are initialing our adapter class and passing our array list to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, getContext());

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
        return root;
    }

    private void sendMessage(String userMsg) {
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String url = "\n" +
                "http://api.brainshop.ai/get?bid=170323&key=tXBki6T2CQJQg7CO&uid=[uid]&msg=" + userMsg;
        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String botResponse = response.getString("cnt");
                    messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));

                    // notifying our adapter as data changed.
                    messageRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();


                    messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                Toast.makeText(getContext(), "No response from the bot..", Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsonObjectRequest);
    }
}
