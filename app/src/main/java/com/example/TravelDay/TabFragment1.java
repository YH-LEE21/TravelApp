package com.example.TravelDay;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager mLayoutManger;
    List<Memo> memoList;

    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  (ViewGroup) inflater.inflate(R.layout.tab_fragment_1, container,false);



        Button btnAdd = view.findViewById(R.id.btnAdd);
        memoList = new ArrayList<Memo>();

        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManger = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManger);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //새로운 메모 작성
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivityForResult(intent,0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain,strSub,0);

            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();
        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Memo> listdate;

        public RecyclerAdapter(List<Memo> listdate){
            this.listdate=listdate;
        }



        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdate.size();
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ItemViewHolder holder, int position) {
            Memo memo = listdate.get(position);

            holder.mainText.setText(memo.getMaintext());
            holder.subText.setText(memo.getSubText());

            if(memo.getIsdone() == 0){
                holder.img.setBackgroundColor(Color.LTGRAY);
            }
            else{
                holder.img.setBackgroundColor(Color.GREEN);
            }
        }

        void addItem(Memo memo){
            listdate.add(memo);
        }

        void remove(int position){
            listdate.remove(position);
        }

        class ItemViewHolder extends  RecyclerView.ViewHolder{

            private TextView mainText;
            private TextView subText;
            private ImageView img;


            public ItemViewHolder(@NonNull View itemView){
                super(itemView);

                mainText = itemView.findViewById(R.id.item_maintext);
                subText = itemView.findViewById(R.id.item_subtext);
                img = itemView.findViewById(R.id.item_image);

            }


        }
    }

}

