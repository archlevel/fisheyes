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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.neucore.fisheyes.cameralist.CameraListActivity;
import com.neucore.fisheyes.login.LoginActivity;
import com.neucore.fisheyes.login.UserInfo;
import com.neucore.fisheyes.utils.PrefUtils;
import com.neucore.fisheyes.R;

public class GuideActivity extends BaseActivity{

	public static final String START_AVS_ACTIVITY = "startAvsActivity";
	private UserInfo mUserInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		mUserInfo = UserInfo.getUserInfo(this.getApplicationContext());
		
		Intent intent = getIntent();
		boolean startAvsActivity = false;
		if(null != intent){
			startAvsActivity = intent.getBooleanExtra(START_AVS_ACTIVITY, false);
		}
		
		final boolean needStartActivity = startAvsActivity;
		TextView startView = (TextView) findViewById(R.id.start);
		startView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(needStartActivity){
					Intent intent = new Intent();
					if(mUserInfo.isLogin){
						intent.setClass(getApplicationContext(), CameraListActivity.class);
					}else{
						intent.setClass(getApplicationContext(), LoginActivity.class);
					}
					startActivity(intent);
				}
				PrefUtils.putBoolean(GuideActivity.this, PrefUtils.HAVE_SHOW_GUIDE, true);
				finish();
			}
		});
	}
}
