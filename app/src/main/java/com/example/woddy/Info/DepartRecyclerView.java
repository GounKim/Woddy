package com.example.woddy.Info;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Depart;
import com.example.woddy.R;

import org.jetbrains.annotations.NotNull;

public class DepartRecyclerView extends RecyclerView.ViewHolder {
    LinearLayout linearLayout; //얘를 눌러야 펼쳐짐
    LinearLayout deptDetailLayout; //펼쳐질 레이아웃
    TextView deptNameTextView; //기관명
    TextView deptIntroduceTextView; //기관소개
    TextView deptCallNumberTextView; //기관 전화번호
    Button deptCallButton; //기관에 전화걸기 버튼
    TextView deptConnectTextView; //기관 프로그램 페이지
    Button deptConnectButton; //기관 페이지 연결 버튼

    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public DepartRecyclerView(@NonNull @NotNull View itemView) {
        super(itemView);

        linearLayout = itemView.findViewById(R.id.info_depart_linearlayout);
        deptDetailLayout=itemView.findViewById(R.id.dept_detail_linearlayout);
        deptNameTextView = itemView.findViewById(R.id.dept_name_textview);
        deptIntroduceTextView = itemView.findViewById(R.id.dept_introduce_textview);
        deptCallNumberTextView = itemView.findViewById(R.id.dept_call_number_textview);
        deptCallButton = itemView.findViewById(R.id.dept_call_button);
        deptConnectTextView = itemView.findViewById(R.id.dept_connect_textview);
        deptConnectButton = itemView.findViewById(R.id.dept_connect_button);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });
    }

    public void onBind(Depart data, int position, SparseBooleanArray selectedItems) {
        deptNameTextView.setText(data.getDepart());
        deptIntroduceTextView.setText(data.getContent());
        deptCallNumberTextView.setText(data.getTel());
        deptConnectTextView.setText(data.getUrl());
        changeVisibility(selectedItems.get(position));
    }

    private void changeVisibility(final boolean isExpanded) {
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // imageView의 높이 변경
                deptDetailLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
                deptDetailLayout.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                deptDetailLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
}
