package com.example.TravelDay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {
    RecyclerView recyclerView1;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager mLayoutManger;
    List<Memo> memoList;
    Memo memo;
    int setPos;
    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  (View)inflater.inflate(R.layout.tab_fragment_1, container,false);



        Button btnAdd = view.findViewById(R.id.btnAdd);
        memoList = new ArrayList<Memo>();

        recyclerView1 = view.findViewById(R.id.recyclerview1);
        mLayoutManger = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(mLayoutManger);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView1.setAdapter(recyclerAdapter);



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
        if(resultCode == 1) requestCode = 1;
        if(requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            memo = new Memo(strMain,strSub);

            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();
        }
        else if(requestCode == 2){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            memo = new Memo(strMain,strSub);
            recyclerAdapter.setItem(setPos,memo);
            recyclerAdapter.notifyDataSetChanged();
        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Memo> listdate;
        public RecyclerAdapter( List<Memo> listdate){
            this.listdate=listdate;
        }



        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1,parent,false);
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
        }

        void addItem(Memo memo){
            listdate.add(memo);
        }

        void remove(int position){
            listdate.remove(position);
        }

        void setItem(int position,Memo memo){ listdate.set(position,memo);}

        class ItemViewHolder extends  RecyclerView.ViewHolder{

            private TextView mainText;
            private TextView subText;


            public ItemViewHolder(@NonNull View itemView){
                super(itemView);
                mainText = itemView.findViewById(R.id.item_maintext);
                subText = itemView.findViewById(R.id.item_subtext);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPos = getAdapterPosition() ;
                        if (setPos != RecyclerView.NO_POSITION) {
                            Intent intent = new Intent(getContext(),AddActivity.class);
                            Toast.makeText(getContext(),"메모 수정",Toast.LENGTH_SHORT).show();
                            startActivityForResult(intent,2);
                        }
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        int pos = getAdapterPosition();
                        builder.setTitle("삭제");
                        builder.setMessage("해당 항목을 삭제하시겠습니까?");
                        builder.setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        remove(pos);
                                        recyclerView1.setAdapter(recyclerAdapter);
                                        Toast.makeText(getContext(),"삭제 완료",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                        return true;
                    }
                });
            }
        }
    }

}

