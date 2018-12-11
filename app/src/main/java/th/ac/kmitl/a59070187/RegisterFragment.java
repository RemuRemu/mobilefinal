package th.ac.kmitl.a59070187;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);}
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        initRegisterBtn();
    }
    void initRegisterBtn(){
        TextView _regBtn = (TextView) getView().findViewById(R.id.reg_register);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText _userID = (EditText) getView().findViewById(R.id.reg_userID);
                EditText _name = (EditText) getView().findViewById(R.id.reg_name);
                EditText _age = (EditText) getView().findViewById(R.id.reg_age);
                EditText _password = (EditText) getView().findViewById(R.id.reg_password);

                String _userIDStr = _userID.getText().toString();
                String _nameStr = _name.getText().toString();
                int _ageInt = Integer.parseInt(_age.getText())
                String _passwordStr = _password.getText().toString();

                String status;

                if (_userIDStr.isEmpty() || _nameStr.isEmpty() || _age.getText().toString().isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "FIELD NAME IS EMPTY");
                }else if (_userIDStr.length()< 6 || _userIDStr.length() > 12){
                    Log.d("USER", "LENGTH NOT ENOUGH");
                    Toast.makeText(
                            getActivity(),
                            " ความยามขั้นต่ำ 6 ตัว และไม่เกิน 12 ตัว",
                            Toast.LENGTH_SHORT
                    ).show();

                }else if (_ageInt< 10 || _ageInt >80) {
                    Log.d("USER", "LENGTH NOT ENOUGH");
                    Toast.makeText(
                            getActivity(),
                            " อายุอยู่ในช่วง 10-80",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (_passwordStr.length() <= 6) {
                    Log.d("USER", "LENGTH NOT ENOUGH");
                    Toast.makeText(
                            getActivity(),
                            "พาสเวิร์ดต้องมากกว่า 6 ตัว",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (!_nameStr.contains(" ") == false) {
                    Log.d("USER", "ช่องว่างต้องมีแค่ 1ช่อง และ ขั้นต่ำ 1ช่อง");
                    Toast.makeText(
                            getActivity(),
                            "พาสเวิร์ดต้องมากกว่า 6 ตัว",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else{
                    SQLiteDatabase myDB;
                    @Override
                    public void onCreate(@Nullable Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);

                        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT,userid VARCHAR(12), name VARCHAR(50),  age INTEGER, password VARCHAR(30))");
                        Bundle bundle = getArguments();
                        try
                        {
                            status = "edit";
                        }
                        catch (NullPointerException e)
                        {
                            if (sleep == null)
                            {
                                status = "new";
                            }
                            else
                            {
                                Log.d("test", "null pointer : " + e.getMessage());
                            }
                        }
                        Log.d("test", "status : " + status);

                        ContentValues row = new ContentValues();
                        row.put("userid", _userIDStr);
                        row.put("name", _nameStr);
                        row.put("age", _ageInt);
                        row.put("password", _passwordStr);
                    Log.d("USER", "GOTO HOME");
                        if (status.equals("new"))
                        {
                            myDB.insert("user", null, row);
                        }
                        else if (status.equals("edit"))
                        {
                            myDB.update("sleep", row, "id="+sleep.getId(), null);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();

                }
            }
        });
    }
}