package com.example.woddy.Info;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.Depart;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class DepartRecyclerView extends RecyclerView.ViewHolder {
    LinearLayout linearLayout; //얘를 눌러야 펼쳐짐
    LinearLayout deptDetailLayout; //펼쳐질 레이아웃
    ImageView arrowImageView; //화살표 모양(접으면 ^ 펼치면 V)
    ImageView deptLogoImageView; //기관 로고
    TextView deptNameTextView; //기관명
    TextView deptIntroduceTextView; //기관소개
    TextView deptCallNumberTextView; //기관 전화번호
    Button deptCallButton; //기관에 전화걸기 버튼
    TextView deptConnectTextView; //기관 프로그램 페이지
    Button deptConnectButton; //기관 페이지 연결 버튼

    String url, telnumber;

    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public DepartRecyclerView(@NonNull @NotNull View itemView) {
        super(itemView);

        linearLayout = itemView.findViewById(R.id.info_depart_linearlayout);
        deptDetailLayout = itemView.findViewById(R.id.dept_detail_linearlayout);
        arrowImageView = itemView.findViewById(R.id.closeImageView);
        deptLogoImageView = itemView.findViewById(R.id.dept_logo_imageview);
        deptNameTextView = itemView.findViewById(R.id.dept_name_textview);
        deptIntroduceTextView = itemView.findViewById(R.id.dept_introduce_textview);
        deptCallNumberTextView = itemView.findViewById(R.id.dept_call_number_textview);
        deptCallButton = itemView.findViewById(R.id.dept_call_button);
        deptConnectTextView = itemView.findViewById(R.id.dept_connect_textview);
        deptConnectButton = itemView.findViewById(R.id.dept_connect_button);

        //아이템 클릭 시 펼치기 위한 리스너
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });
        //전화걸기
        deptCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = "tel:" + telnumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                itemView.getContext().startActivity(intent);
            }
        });
        //연결하기
        deptConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                itemView.getContext().startActivity(intent);
            }
        });

    }

    public void onBind(Depart data, int position, SparseBooleanArray selectedItems) {

        if (!data.getLogo().isEmpty()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference(data.getLogo());

            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(itemView)
                                .load(task.getResult())
                                .fitCenter()
                                .into(deptLogoImageView);
                    }
                }
            });
        }

        deptNameTextView.setText(data.getDepart());
        deptIntroduceTextView.setText(data.getContent());
        deptCallNumberTextView.setText(data.getTel());
        deptConnectTextView.setText(data.getProgram());
        changeVisibility(selectedItems.get(position));
        telnumber = data.getTel().replace("-", "");
        url = data.getUrl();
    }

    private void changeVisibility(final boolean isExpanded) {
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        deptDetailLayout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = deptDetailLayout.getHeight();
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(deptDetailLayout.getMeasuredHeight(), targetHeight)
                : ValueAnimator.ofInt(targetHeight, deptDetailLayout.getMeasuredHeight());
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // imageView의 높이 변경
//                deptDetailLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
//                deptDetailLayout.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                deptDetailLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                arrowImageView.setImageResource(isExpanded ? R.drawable.ic_baseline_keyboard_arrow_down_24 : R.drawable.ic_baseline_keyboard_arrow_up_24);
            }
        });
        // Animation start
        va.start();
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener
                                                         onViewHolderItemClickListener) {
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
}
