package com.remember.app.ui.cabinet.memory_pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.remember.app.R;
import com.remember.app.data.models.MemoryPageModel;
import com.remember.app.data.models.ResponsePages;
import com.remember.app.ui.adapters.PageFragmentAdapter;
import com.remember.app.ui.cabinet.memory_pages.show_page.ShowPageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PageFragment extends MvpAppCompatFragment implements PageView, PageFragmentAdapter.Callback{

    @InjectPresenter
    PagePresenter presenter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private PageFragmentAdapter pageFragmentAdapter;
    private int countPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memory_pages, container, false);
        unbinder = ButterKnife.bind(this, v);
        presenter.getPages(countPage);
        pageFragmentAdapter = new PageFragmentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pageFragmentAdapter);
        pageFragmentAdapter.setCallback(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    countPage++;
                    presenter.getPages(countPage);
                }
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onReceivedPages(ResponsePages responsePages) {
        pageFragmentAdapter.setItems(responsePages.getResult());
    }

    @Override
    public void sendItem(MemoryPageModel person) {
        Intent intent = new Intent(getActivity(), ShowPageActivity.class);
        intent.putExtra("PERSON", person);
        intent.putExtra("IS_LIST", true);
        startActivity(intent);
    }
}