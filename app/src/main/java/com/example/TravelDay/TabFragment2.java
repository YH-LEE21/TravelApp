package com.example.TravelDay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabFragment2 extends Fragment {

    RecyclerView recyclerView2;
    RecyclerAdapter recyclerAdapter2;
    RecyclerView.LayoutManager yLayoutManger;
    List<Calc> calcList;
    Calc calc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  (View)inflater.inflate(R.layout.tab_fragment_2, container, false);

        Button  btnCalc = view.findViewById(R.id.btnCalc);

        calcList = new ArrayList<Calc>();
        recyclerView2 = view.findViewById(R.id.recyclerview2);
        yLayoutManger = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(yLayoutManger);

        recyclerAdapter2 = new RecyclerAdapter(calcList);
        recyclerView2.setAdapter(recyclerAdapter2);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CalcActivity.class);
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
            String strMain = data.getStringExtra("Calc");
            String strSub = data.getStringExtra("sub");

            calc = new Calc(strMain,strSub);

            recyclerAdapter2.addItem(calc);
            recyclerAdapter2.notifyDataSetChanged();
        }
        else if(requestCode == 2){
            String strMain = data.getStringExtra("Calc");
            String strSub = data.getStringExtra("sub");

            calc = new Calc(strMain,strSub);


        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Calc> listdate;
        public RecyclerAdapter( List<Calc> listdate){
            this.listdate=listdate;
        }



        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Calc calc = listdate.get(position);

            holder.calcText.setText(calc.getMainCalc());
            holder.subText.setText(calc.getSubText());
        }

        @Override
        public int getItemCount() {
            return listdate.size();
        }

        void addItem(Calc calc){
            listdate.add(calc);
        }

        void remove(int position){
            listdate.remove(position);
        }

        class ItemViewHolder extends  RecyclerView.ViewHolder{
            private TextView calcText;
            private TextView subText;

            public ItemViewHolder(@NonNull View itemView){
                super(itemView);
                calcText = itemView.findViewById(R.id.item_calcMain);
                subText = itemView.findViewById(R.id.item_subtext);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            Intent intent = new Intent(getContext(),CalcActivity.class);
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
                                        calcList.remove(pos);
                                        recyclerView2.setAdapter(recyclerAdapter2);
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
