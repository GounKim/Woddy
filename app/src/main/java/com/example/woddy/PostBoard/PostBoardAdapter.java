package com.example.woddy.PostBoard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Posting;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.Posting.ShowPosting;
import com.example.woddy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PostBoardAdapter extends RecyclerView.Adapter<PostBoardAdapter.ViewHolder> {

    private ArrayList<Posting> PostingList;
    private ArrayList<String> postingPath;

    private String boardName;
    private String tagName;

    public PostBoardAdapter(String boardName, String tagName) {
        this.PostingList = new ArrayList<>();
        this.postingPath = new ArrayList<>();
        this.boardName = boardName;
        this.tagName = tagName;
    }

    public void setItems(ArrayList<Posting> PostingList, ArrayList<String> postingPath) {
        this.PostingList = PostingList;
        this.postingPath = postingPath;
    }

    @NonNull
    @Override
    public PostBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_board_item, parent, false);
        PostBoardAdapter.ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override

    public void onBindViewHolder(final ViewHolder holder, int position) {

        Posting data = PostingList.get(position);

        holder.title.setText(data.getTitle());
        holder.writer.setText(data.getWriter());
        holder.message.setText(data.getContent());
        holder.boardName.setText(boardName);
        holder.tagName.setText(tagName);
        holder.writtenTime.setText(timestamp(data.getPostedTime()));
        holder.likeNum.setText(data.getNumberOfLiked() + "");
    }

    @Override
    public int getItemCount() {
        return postingPath.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout layout;
        private TextView title, writer, message, boardName, tagName;
        private TextView writtenTime, likeNum;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (FrameLayout) itemView.findViewById(R.id.post_board_item_layout);
            title = (TextView) itemView.findViewById(R.id.post_board_item_title);
            writer = (TextView) itemView.findViewById(R.id.post_board_item_writer);
            message = (TextView) itemView.findViewById(R.id.post_board_item_message);
            boardName = (TextView) itemView.findViewById(R.id.post_board_item_boardName);
            tagName = (TextView) itemView.findViewById(R.id.post_board_item_tagName);
            writtenTime = (TextView) itemView.findViewById(R.id.post_board_item_writtenTime);
            likeNum = (TextView) itemView.findViewById(R.id.post_board_item_like_num);

            layout.setClickable(true);
            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //사용자가 클릭한 아이템의 position을 준다
                    int pos = getAdapterPosition();
                    //포지션이 recylerView의 아이템인지 확인
                    if(pos != RecyclerView.NO_POSITION){
                        //액티비티 전환
                        Intent intent = new Intent(v.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("documentPath", postingPath.get(pos));
                        v.getContext().startActivity(intent);
                    }


                }
            });
        }
    }

    private String timestamp(Date date) {    // 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
