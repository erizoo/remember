package com.remember.app.ui.cabinet.memory_pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.pixplicity.easyprefs.library.Prefs;
import com.remember.app.R;
import com.remember.app.data.models.MemoryPageModel;
import com.remember.app.data.models.ResponsePages;
import com.remember.app.ui.adapters.PageFragmentAdapter;
import com.remember.app.ui.cabinet.main.MainActivity;
import com.remember.app.ui.cabinet.memory_pages.show_page.ShowPageActivity;
import com.remember.app.ui.utils.MvpAppCompatFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PageFragment extends MvpAppCompatFragment implements PageView, PageFragmentAdapter.Callback, MainActivity.CallbackPage {

    @InjectPresenter
    PagePresenter presenter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.show_all)
    TextView showAll;

    private Unbinder unbinder;
    private PageFragmentAdapter pageFragmentAdapter;
    private int countPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Prefs.putBoolean("PAGE_FRAGMENT", true);
        ((MainActivity) getActivity()).setCallback(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memory_pages, container, false);
        unbinder = ButterKnife.bind(this, v);
        presenter.getPages();
        pageFragmentAdapter = new PageFragmentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pageFragmentAdapter);
        pageFragmentAdapter.setCallback(this);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
            Prefs.putBoolean("EVENT_FRAGMENT", false);
            Prefs.putBoolean("PAGE_FRAGMENT", true);
        } else {
            Prefs.putBoolean("EVENT_FRAGMENT", true);
            Prefs.putBoolean("PAGE_FRAGMENT", false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.show_all)
    public void showAll(){
        presenter.getPages();
    }

    @Override
    public void onReceivedPages(List<MemoryPageModel> memoryPageModels) {
        showAll.setVisibility(View.GONE);
        pageFragmentAdapter.setItems(memoryPageModels);
    }

    @Override
    public void sendItem(MemoryPageModel person) {
        Intent intent = new Intent(getActivity(), ShowPageActivity.class);
        intent.putExtra("PERSON", person);
        intent.putExtra("IS_LIST", true);
        startActivity(intent);
    }

    @Override
    public void sendItemsSearch(List<MemoryPageModel> result) {
        showAll.setVisibility(View.VISIBLE);
        pageFragmentAdapter.setItems(result);
        pageFragmentAdapter.notifyDataSetChanged();
    }
}