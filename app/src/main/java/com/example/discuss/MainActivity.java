package com.example.discuss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    public static final PostCollection allPosts = new PostCollection();
    public static final String DISPLAY_NAME = "com.example.discuss.example.DISPLAY_NAME";

    private static class User {

        public String username, password, displayName;

        public User(String u, String p, String d) {
            username = u;
            password = p;
            displayName = d;
        }

        public static boolean containsUsername(Set<User> users, String username) {
            for (User u : users) {
                if (u.username.equals(username)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean userHasPassword(Set<User> users, String username, String password) {
            for (User u : users) {
                if (u.username.equals(username) && u.password.equals(password)) {
                    return true;
                }
            }
            return false;
        }

        public static String getDisplayName(Set<User> users, String username) {
            for (User u : users) {
                if (u.username.equals(username)) {
                    return u.displayName;
                }
            }
            throw new RuntimeException();
        }

    }

    private static Set<User> initializeLogins() {
        Set<User> users = new HashSet<>();

        users.add(new User("andy@gmail.com", "andy", "Andy"));
        users.add(new User("bob@gmail.com", "bob", "Bob"));
        users.add(new User("cathy@gmail.com", "cathy", "Cathy"));
        users.add(new User("", "", "Default"));

        return users;
    }

    private static final Set<User> users = initializeLogins();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // delete these later
        try {

            allPosts.addPost(new Post("Help me now!", new PostItem(
                    "Can you tell me what is 2 + 2? Thanks!",
                    "Lucas", true
            ))); allPosts.addPost(new Post("Urgently need answers!", new PostItem(
                    "Can you tell me what is 3 - 5? Thanks!",
                    "Jerry", true
            ))); allPosts.addPost(new Post("I'm going insane!", new PostItem(
                    "Can you tell me what is 7 * 3? Thanks!",
                    "Frederick", true
            ))); allPosts.addPost(new Post("Please...", new PostItem(
                    "Can you tell me what is 9 / 2? Thanks!",
                    "Boblet", true
            ))); allPosts.addPost(new Post("Help a dude out!", new PostItem(
                    "Can you tell me what is the capital city of Canada? Thanks!",
                    "George", true
            )));

            final String LONG_TITLE =
                    "My question has a really long title, so sorry about that :(";
            final String LONG_QUESTION =
                    "My question has an even longer body, so " +
                    "really really, really really, really really, really really, really really" +
                    " sorry about that :(";
            allPosts.addPost(new Post(LONG_TITLE, new PostItem(LONG_QUESTION, "Troll", true)));
            allPosts.saveToJson();

        } catch (PostCollection.PostTitleDuplicateException e) {
            throw new RuntimeException(e);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView password = (TextView) findViewById(R.id.editTextTextPassword);

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(User.userHasPassword(users, username.getText().toString(), password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    openUI(User.getDisplayName(users, username.getText().toString()));
                }else
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // this is the open UI function jerry2
    public void openUI(String name){
        Intent intent = new Intent(this, UserInterface.class);
        intent.putExtra(DISPLAY_NAME, name);
        startActivity(intent);
    }

}