package com.example.woddy.Info;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Depart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DepartData {
    ArrayList<Depart> items = new ArrayList<>();
    ArrayList<String> docPath = new ArrayList<>();

    FirestoreManager manager;
    private RecyclerViewAdapter adapter;

    public DepartData(Context context) {
        this.manager = new FirestoreManager();
    }

    public ArrayList<Depart> getItems(RecyclerView recyclerView, String boardName) {
        adapter = new RecyclerViewAdapter(boardName);
        manager.getDepartQuery(boardName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Depart newsDepart = document.toObject(Depart.class);
                                    String tmp = newsDepart.getContent().replace("\\n", "\n");
                                    newsDepart.setContent(tmp);
                                    docPath.add(document.getReference().getPath());
                                    items.add(newsDepart);
                                }
                                adapter.setItems(items, docPath);

                                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
                                recyclerView.setAdapter(adapter);
                            } else {
                                Log.d("DepartData", "Nothing Founded!");
                            }
                        } else {
                            Log.d("DepartData", "Finding Postings failed!");
                        }
                    }
                });
        return items;
    }
}
