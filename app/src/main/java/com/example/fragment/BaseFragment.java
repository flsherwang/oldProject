package com.example.fragment;

import java.lang.reflect.Field;

import com.example.constant.BusProvider;
import com.example.util.NetWorkToast;
import com.example.view.LoadingDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	private LayoutInflater inflater;
	private View contentView;
	private Context context;
	private ViewGroup container;

	private final static int TOASTTIME = 1000;
	private LoadingDialog proDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		BusProvider.getInstance().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		onCreateView(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected void onCreateView(Bundle savedInstanceState) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
		BusProvider.getInstance().unregister(this);
	}

	public Context getApplicationContext() {
		return context;
	}

	public void setContentView(int layoutResID) {
		setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}
	
	public ViewGroup getViewGroup() {
		return container;
	}

	public LayoutInflater getLayoutInflater() {
		return inflater;
	}

	public View findViewById(int id) {
		if (contentView != null)
			return contentView.findViewById(id);
		return null;
	}

	@Override
	public void onResume() {
		super.onResume();
//		Statistic.getStatistics().pageviewStart(this, this.getClass().getName());
	}

	@Override
	public void onPause() {
		super.onPause();
//		Statistic.getStatistics().pageviewEnd(this, this.getClass().getName());
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	public void showProgressDialog(String str) {
		proDialog = LoadingDialog.create(getActivity(), str);
		proDialog.show();
	}

	public void showProgressDialog() {
		showProgressDialog(null);
	}

	public void dismissProgressDialog() {
		if (proDialog != null) {
			proDialog.dismiss();
			proDialog = null;
		}
	}
	public void showNetWorkToast() {
		NetWorkToast.makeText(getActivity(), "", 3500).show();
	}

	public void dimissNetWorkToast() {
		NetWorkToast.removeView();
	}
}
