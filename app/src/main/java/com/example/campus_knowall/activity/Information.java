package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.Com;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.MainActivity;
import com.example.campus_knowall.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Information extends AppCompatActivity {
    private EditText ename,ephone;
    private Button edit;
    private ImageView back;
//    private Spinner esex;
//    private String nsex;
//    String[] arr={"男","女"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_information);
        initView();
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //修改监听
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=getIntent();
                //获取当前用户
                final User current_user = BmobUser.getCurrentUser(User.class);
                Log.d("Edit", "用户" + current_user.getObjectId()+"的用户名本来是"+current_user.getNickname());
                Log.d("Edit", "用户" + current_user.getObjectId()+"的电话本来是"+current_user.getPhone());
//                Log.d("Edit", "用户" + current_user.getObjectId()+"的性别本来是"+current_user.getGender());
                String nname=ename.getText().toString().trim();
                String nphone=ephone.getText().toString().trim();
//                esex.setPrompt("请选择您的性别：");
//                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Information.this, android.R.layout.simple_list_item_1,arr);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                esex.setAdapter(adapter);
//                esex.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                        nsex=arr[arg2];
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                    }
//                });
//                String nsex=esex.getSelectedItem().toString();
                Log.d("Edit", "用户名改为" + nname);
                Log.d("Edit", "电话改为" + nphone);
//                Log.d("Edit", "性别改为" + nsex);
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("objectId",current_user.getObjectId());
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            User object = list.get(0);
                            //需要有空置判定
                            //改名
                            if (!ename.getText().toString().isEmpty()){
                                object.setNickname(nname);
                                Log.d("Edit", "把用户" + current_user.getObjectId()+"的用户名改了");
                                Toast.makeText(Information.this, "修改昵称成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            //改电话
                            if (!ephone.getText().toString().isEmpty()){
                                object.setPhone(nphone);
                                Log.d("Edit", "把用户" + current_user.getObjectId()+"的电话改了");
                                Toast.makeText(Information.this, "修改电话成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            //改性别，好像只能改一次，看看有没有默认值
//                            if (nsex.equals("男")){
//                                object.setGender("man");
//                                Log.d("Edit", "把用户" + current_user.getObjectId()+"的性别改为男");
//                            }else if (nsex.equals("女")){
//                                object.setGender("gril");
//                                Log.d("Edit", "把用户" + current_user.getObjectId()+"的性别改为女");
//                            }
                            object.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        // 更新成功
                                        Log.d("Edit", "更新成功，请查看数据库");
                                    } else {
                                        // 更新失败
                                    }
                                }
                            });
                        } else {
                            // 查询失败或未找到匹配记录
                        }
                    }
                });
            }
        });
    }
    private void initView() {
        ename = findViewById(R.id.ename);
        ephone = findViewById(R.id.ephone);
//        esex=findViewById(R.id.esex);
        edit=findViewById(R.id.edit);
        back=findViewById(R.id.back);
    }
}
