package com.example.woddy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbummBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbummBoardFragment extends Fragment {

   private AlbumAdapter adpater = new AlbumAdapter();

    public AlbummBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbummBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbummBoardFragment newInstance(String param1, String param2) {
        AlbummBoardFragment fragment = new AlbummBoardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_albumm_board);

        //recylcerView 초기화
        RecyclerView recyclerView = getView().findViewById(R.id.album_recycler_view);

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getActivity()));
        recyclerView.setAdapter(adpater);

        //아이템 로드
        //adapter.setitems(new Sample)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albumm_board, container, false);
    }
}