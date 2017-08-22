package org.mifos.mobilewallet.mifospay.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import org.mifos.mobilewallet.mifospay.R;
import org.mifos.mobilewallet.mifospay.base.BaseActivity;
import org.mifos.mobilewallet.mifospay.common.SearchContract;
import org.mifos.mobilewallet.mifospay.common.presenter.SearchPresenter;
import org.mifos.mobilewallet.mifospay.common.ui.adapter.SearchAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import mifos.org.mobilewallet.core.domain.model.SearchResult;

/**
 * Created by naman on 21/8/17.
 */

public class SearchActivity extends BaseActivity implements SearchContract.SearchView {

    @Inject
    SearchPresenter mPresenter;

    SearchContract.SearchPresenter mSearchPresenter;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResults;

    @Inject
    SearchAdapter searchAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setToolbarTitle("");
        showBackButton();

        mPresenter.attachView(this);

        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(searchAdapter);
        rvSearchResults.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

    }

    @OnTextChanged(R.id.et_search)
    public void searchTextChanged() {
        if (etSearch.getText().toString().length() > 3) {
            showSwipeProgress();
            mSearchPresenter.performSearch(etSearch.getText().toString());
        } else {
            searchAdapter.clearData();
        }
    }

    @Override
    public void setPresenter(SearchContract.SearchPresenter presenter) {
        this.mSearchPresenter = presenter;
    }

    @Override
    public void showSearchResult(List<SearchResult> searchResults) {
        searchAdapter.setData(searchResults);
        hideSwipeProgress();
    }
}
