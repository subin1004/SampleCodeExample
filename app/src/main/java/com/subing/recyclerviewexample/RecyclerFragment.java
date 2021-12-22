package com.subing.recyclerviewexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.subing.recyclerviewexample.databinding.FragmentRecyclerBinding;

import java.util.ArrayList;

public class RecyclerFragment extends Fragment implements RecyclerAdapter.OnListItemSelectedInterface{

    // binding
    private FragmentRecyclerBinding binding;
    private View view;

    // recyclerview
//    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // view binding
        binding = FragmentRecyclerBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        recyclerInit();
        return view;
    }

    private void recyclerInit(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Data> data = new ArrayList<>(Data.DATA);

        recyclerAdapter = new RecyclerAdapter(data, this);  // listener
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemSelected(View view, int position) {
        RecyclerAdapter.ViewHolder viewHolder = (RecyclerAdapter.ViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(position);

        String extraTitle = viewHolder.textView.getText().toString();   // intentExtra = title
        Toast.makeText(getContext(), extraTitle, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PARAM_ID, extraTitle);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
//                        MainActivity.this, view, ViewCompat.getTransitionName(view) // 이거는 창 전체를 이동할 때
                        // View와 ViewName을 쌍으로 여러개 전달 (Shared Element Transition)
                        getActivity(),
                        new Pair<>(view.findViewById(R.id.imageView), DetailActivity.VIEW_NAME_HEADER_IMAGE),
                        new Pair<>(view.findViewById(R.id.textView), DetailActivity.VIEW_NAME_HEADER_TITLE),
                        new Pair<>(view.findViewById(R.id.textView2), DetailActivity.VIEW_NAME_HEADER_CONTENT)
                );

        startActivity(intent, options.toBundle());
    }
}
