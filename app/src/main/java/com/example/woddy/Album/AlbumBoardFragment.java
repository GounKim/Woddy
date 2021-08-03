package com.example.woddy.Album;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woddy.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AlbumBoardFragment extends Fragment {

    ArrayList<ImgPost> imgposts;
    ListView customListView;
    private static AlbumAdapter albumAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album_board, container, false);

        //data를 가져와서 어답터와 연결해 준다. 서버에서 가져오는게 대부분 이다.
        imgposts = new ArrayList<>();
        imgposts.add(new ImgPost("https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));
        imgposts.add(new ImgPost("https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        imgposts.add(new ImgPost("https://image.tmdb.org/t/p/w600_and_h900_bestv2/5MgWM8pkUiYkj9MEaEpO0Ir1FD9.jpg", "Cho Yeo-jeong (조여정) is a South Korean actress. Born on February 10, 1981, she began her career as a model in 1997 at the age of 16 and launched her acting career two years later. She is best known for her roles in the provocative period films “The Servant” (2010) and “The Concubine” (2012) and the television dramas “I Need Romance” (2011), “Haeundae Lovers” (2012), “Divorce Lawyer in Love” (2015) and “Perfect Wife” (2017)."));
        imgposts.add(new ImgPost("https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        imgposts.add(new ImgPost("https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));


        customListView = (ListView) rootView.findViewById(R.id.listView_left);
        albumAdapter = new AlbumAdapter(getContext(),imgposts);
        customListView.setAdapter(albumAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.textView).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
        }

    public class ImgPost {
        private String img_url;
        private String text;

        public ImgPost(String img_url, String text) {
            this.img_url = img_url;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public String getImg_url() {
            return img_url;
        }
    }
    }

