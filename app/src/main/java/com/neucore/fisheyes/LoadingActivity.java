/*
 * Copyright (C) 2015 iChano incorporation's Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neucore.fisheyes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.igexin.sdk.PushManager;
import com.neucore.fisheyes.cameralist.CameraListActivity;
import com.neucore.fisheyes.login.LoginActivity;
import com.neucore.fisheyes.login.UserInfo;
import com.neucore.fisheyes.setting.CameraSettingsTabActivity;
import com.neucore.fisheyes.utils.PrefUtils;
import com.neucore.fisheyes.R;
import com.umeng.analytics.MobclickAgent;


public class LoadingActivity extends Activity{
	
	private UserInfo mUserInfo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		CarshHandler.getIntance().init(this.getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		//startActivity(new Intent(LoadingActivity.this, CameraSettingsTabActivity.class));
		final MyViewerHelper myViewerHelper = MyViewerHelper.getInstance(getApplicationContext());
		myViewerHelper.setLoginListener(new MyViewerHelper.LoginListener() {
			
			@Override
			public void onLoginResult(boolean success) {
				if(success){
					boolean haveShowGuide = PrefUtils.getBoolean(LoadingActivity.this, PrefUtils.HAVE_SHOW_GUIDE);
					Intent intent = new Intent();
					if(haveShowGuide){
						if(mUserInfo.isLogin){
							intent.setClass(getApplicationContext(), CameraListActivity.class);
						}else{
							intent.setClass(getApplicationContext(), LoginActivity.class);
						}
					}else{
						intent.setClass(getApplicationContext(), GuideActivity.class);
						intent.putExtra(GuideActivity.START_AVS_ACTIVITY, true);
					}
					myViewerHelper.setLoginListener(null);
					startActivity(intent);
					LoadingActivity.this.finish();
				}
			}
		});

		mUserInfo = UserInfo.getUserInfo(this.getApplicationContext());
		
		//init getui.
		PushManager.getInstance().initialize(this.getApplicationContext());

	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void onBackPressed() {
		//do not let user exit;
		//super.onBackPressed();
	}
}