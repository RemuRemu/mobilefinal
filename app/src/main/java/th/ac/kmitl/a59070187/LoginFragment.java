package th.ac.kmitl.a59070187;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginFragment extends Fragment {
    //private FirebaseAuth mAuth;
    SharedPreferences sharedPref;
    SQLiteDatabase myDB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT,userid VARCHAR(12), name VARCHAR(50),  age INTEGER, password VARCHAR(25))");
        sharedPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        if (currentUser != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .addToBackStack(null)
                    .commit();
        }
        initRegisterBtn();

        Button _loginBtn = (Button) getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText _userId = (EditText) getView().findViewById(R.id.Login_userid);
                EditText _password = (EditText) getView().findViewById(R.id.Login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                Log.d("LOGIN", "On click");
                Log.d("LOGIN", "USER EMAIL = " + _userEmail);
                Log.d("LOGIN", "PASSWORD = " + _passwordStr);

                if (_userEmailStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("LOGIN", "USER OR PASSWORD IS EMPTY");
                    Toast.makeText(
                            getActivity(),"โปรดใส่อีเมลและรหัสผ่าน",Toast.LENGTH_SHORT
                    ).show();
                }else if (cursor.moveToNext())
                {
                    Log.d("final", "login success");
                    sharedPref.edit()
                            .putString("user id", cursor.getString(0))
                            .putString("name", cursor.getString(1))
                            .putInt("age", cursor.getInt(2))
                            .putString("password", cursor.getString(3))
                            .apply();
                    Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor = myDB.rawQuery("select userid, name, age, password from user where userid = '" + _userIdStr + "' and password = '" + _passwordStr + "'", null);
                else {
                    Log.d("final", "login failure");
                    Toast.makeText(getContext(), "username หรือ password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void initRegisterBtn(){
        TextView _regBtn = (TextView) getView().findViewById(R.id.login_register);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("USER", "GOTO REGISTER");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }
//    void signInWithEmail(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful() && mAuth.getCurrentUser().isEmailVerified()) {
//                            Log.d("LOGIN", "LOGIN SUCCESSFUL");
//                            getActivity().getSupportFragmentManager()
//                                    .beginTransaction()
//                                    .replace(R.id.main_view, new MenuFragment())
//                                    .addToBackStack(null)
//                                    .commit();
//                        }
//                        else {
//                            Log.d("LOGIN", "LOGIN FAIL", task.getException());
//                            mAuth.signOut();
//                            Toast.makeText(
//                                    getActivity(),"อีเมลหรือรหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT
//                            ).show();
//                        }
//                    }
//                });
//    }
}
