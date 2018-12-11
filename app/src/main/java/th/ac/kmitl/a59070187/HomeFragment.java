package th.ac.kmitl.a59070187

import android.support.v4.app.Fragment;

public class HomeFragment extends Fragment {
    SharedPreferences sharedPref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            sharedPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        }
        catch (NullPointerException e)
        {
            Log.d("test","getSharedPrefences return NullPointerE :" + e.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initText();
        initProfileBtn();
        initFriendBtn();
        initSignOutBtn();
    }
    public void initText()
    {
        TextView line_1 = getView().findViewById(R.id.line_1);
        TextView quote = getView().findViewById(R.id.quote);
        Line_1.setText("Hello " + sharedPref.getString("name", "name not found"));

        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "quote.txt");
        if (!file.exists())
        {
            quote.setText("there is no qote");
        }
        else
        {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader buff = new BufferedReader(new FileReader(file));
                String line;

                while ((line = buff.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                buff.close();
            }
            catch (IOException e) {
                Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("test", "read file error : " + e.getMessage());
            }
            quote.setText(text.toString());
        }
    }

    public void initProfileBtn(){
       Button profileBtn= getView().findViewById(R.id.home_friend_button);
       friendButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.d("test", "GOTO PROFILE");
               getActivity().getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.main_view, new ProfileFragment())
                       .addToBackStack(null)
                       .commit();
           }
       });
    }

    public void initFriendBtn(){
        Button friendBtn = getView().findViewById(R.id.home_friend_button);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "GOTO FRIEND");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new FriendFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    public void initSignOutBtn(){
        Button signOutBtn = getView().findViewById(R.id.home_friend_button);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "signout");
                sharedPref.edit()
                        .clear()
                        .apply();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}